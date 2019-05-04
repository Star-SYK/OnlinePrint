package com.gwd.controller;

import com.gwd.Util.NetworkUtil;
import com.gwd.Util.WeChatPayUtil;
import com.gwd.dao.UserDao;
import com.gwd.entity.Address;
import com.gwd.entity.OrderInfo;
import com.gwd.entity.ResponseData;
import com.gwd.entity.WeChatPay;
import com.gwd.service.AddressService;
import com.gwd.service.OrderInfoService;
import com.gwd.service.UserService;
import com.gwd.service.WeChatPayService;
import org.dom4j.DocumentHelper;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.w3c.dom.Document;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

@RequestMapping(value = "/pay" , method = RequestMethod.POST)
@RestController
public class WeChatPayController {


    @Resource
    private UserService userService;

    @Resource
    private UserDao userDao;

    @Resource
    private OrderInfoService orderInfoService;

    @Resource
    private WeChatPayService weChatPayService;

    @Transactional
    @RequestMapping(value = "/order/{orderId}")
    public ResponseData order(@PathVariable Integer orderId, HttpServletRequest request) throws IOException {
        ResponseData  responseData  =  new ResponseData();
        Integer userId = userService.hasLogin(request);
        if(userId == null){
            responseData.setStatusOther("请先登录");
            return responseData;
        }
        if(weChatPayService.getByOrderId(orderId) != null){
            responseData.setStatusOther("该订单已经下过单请勿重复下单");
            return responseData;
        }


        Map<String,String> map = new TreeMap<>();
        map.put("openid",userService.getOpenIdByUserId(userId));
        String ip = NetworkUtil.getIpAddress(request);
        if(ip.equals("0:0:0:0:0:0:0:1")){
            map.put("spbill_create_ip","120.77.32.233");
        }else {
            map.put("spbill_create_ip",ip);
        }
        OrderInfo orderInfo = orderInfoService.getById(orderId);
        if(orderInfo == null || orderInfo.getUserId() != userId){
            responseData.setStatusOther("订单不存在");
            return responseData;
        }
        map.put("out_trade_no",orderId.toString());
        Integer price = (int)(orderInfo.getPrice()*100);
        map.put("total_fee",price.toString());
        String nonceStr = UUID.randomUUID().toString().replaceAll("-","").substring(0,20);
        map.put("nonce_str",nonceStr);

        String wxResponseXml = WeChatPayUtil.RequestWeChat(map);
        System.out.println(wxResponseXml);

        if(wxResponseXml.contains("SUCCESS")){
            Map<String,String> resultMap = new TreeMap<>();
            resultMap.put("appId",WeChatPayUtil.appid);
            resultMap.put("timeStamp",String.valueOf(new Date().getTime()));
            resultMap.put("nonceStr",WeChatPayUtil.regex(wxResponseXml,"nonce_str"));
            if(resultMap.get("nonceStr") == null){
                responseData.setStatusOther("商家订单号重复");
                return responseData;
            }
            resultMap.put("package","prepay_id="+WeChatPayUtil.regex(wxResponseXml,"prepay_id"));
            resultMap.put("signType","MD5");
            resultMap.put("paySign",WeChatPayUtil.createSign(resultMap));


            /* 保留请求数据*/
            WeChatPay weChatPay = new WeChatPay(orderId,map.toString(),wxResponseXml,resultMap.toString());
            weChatPayService.save(weChatPay);
            /*目前没必要返回*/
       /*     resultMap.put("return_code",WeChatPayUtil.regex(wxResponseXml,"return_code"));
            resultMap.put("return_msg",WeChatPayUtil.regex(wxResponseXml,"return_msg"));
            resultMap.put("appid",WeChatPayUtil.regex(wxResponseXml,"appid"));
            resultMap.put("mch_id",WeChatPayUtil.regex(wxResponseXml,"mch_id"));
            resultMap.put("sign",WeChatPayUtil.regex(wxResponseXml,"sign"));
            resultMap.put("result_code",WeChatPayUtil.regex(wxResponseXml,"result_code"));
            resultMap.put("trade_type",WeChatPayUtil.regex(wxResponseXml,"trade_type"));*/


            responseData.setData(resultMap);
        }else {
            responseData.setStatusOther("调用微信支付接口失败");
        }
        return responseData;
    }




    public ResponseData inform(@PathVariable Integer orderId, HttpServletRequest request) throws IOException {
        ResponseData  responseData  =  new ResponseData();
        return responseData;
    }


    // 微信通知地址
    @Transactional
    @RequestMapping(value = "/inform")
    public String getWeChatPayReturn(HttpServletRequest httpServletRequest){
        Integer orderId = null;
        try {
            InputStream inStream = httpServletRequest.getInputStream();
            int _buffer_size = 1024;
            if (inStream != null) {
                ByteArrayOutputStream outStream = new ByteArrayOutputStream();
                byte[] tempBytes = new byte[_buffer_size];
                int count = -1;
                while ((count = inStream.read(tempBytes, 0, _buffer_size)) != -1) {
                    outStream.write(tempBytes, 0, count);
                }
                outStream.flush();
                //将流转换成字符串
                String result = new String(outStream.toByteArray(), "UTF-8");
                Map<String,String> map = getMap(result); // 取出所有数据
                // 校验签名
                if(map.get("sign").equals(WeChatPayUtil.regex(result,"sign"))){
                     // 校验商户信息
                    boolean flag = (WeChatPayUtil.appid.equals(map.get("appid"))) && (WeChatPayUtil.mch_id.equals(map.get("mch_id")));
                    if(flag){
                        orderId = Integer.valueOf(map.get("out_trade_no"));  // 获取订单orderId
                        Integer price = Integer.valueOf(WeChatPayUtil.simpleRegex(result,"total_fee")); // 获取价格
                        OrderInfo orderInfo = orderInfoService.getById(orderId); // 获取订单信息
                        Integer sqlPrice = (int)(orderInfo.getPrice()*100);
                        if (sqlPrice == price && userService.getOpenIdByUserId(orderInfo.getUserId()).equals(map.get("openid"))){
                            if(weChatPayService.getByOrderId(orderId)!=null){
                                orderInfoService.updateHasPay(orderId);  // 更新支付状态
                                weChatPayService.updatePayResult(orderId,result); // 保存信息
                                weChatPayService.updatePayReturnCode(orderId,"SUCCESS");
                                //通知微信支付系统接收到信息
                                return "<xml><return_code><![CDATA[SUCCESS]]></return_code> <return_msg><![CDATA[OK]]></return_msg> </xml>";
                            }
                        }
                    }
                }
            }

        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
        //如果失败返回错误，微信会再次发送支付信息
        //weChatPayService.updatePayReturnCode(orderId,"FAIL");
        return "<xml><return_code><![CDATA[FAIL]]></return_code></xml>";
    }

    public static Map getMap(String xml){
        Map<String,String> map = new TreeMap<>();
        map.put("result_code",WeChatPayUtil.regex(xml,"result_code"));
        map.put("return_code",WeChatPayUtil.regex(xml,"return_code"));
         // 判读一下返回值是否正确
        if(map.get("result_code").equals("SUCCESS") && map.get("return_code").equals("SUCCESS")){
            map.put("appid",WeChatPayUtil.regex(xml,"appid"));
            map.put("bank_type",WeChatPayUtil.regex(xml,"bank_type"));
            map.put("cash_fee",WeChatPayUtil.regex(xml,"cash_fee"));
            map.put("fee_type",WeChatPayUtil.regex(xml,"fee_type"));
            map.put("is_subscribe",WeChatPayUtil.regex(xml,"is_subscribe"));
            map.put("mch_id",WeChatPayUtil.regex(xml,"mch_id"));
            map.put("nonce_str",WeChatPayUtil.regex(xml,"nonce_str"));
            map.put("openid",WeChatPayUtil.regex(xml,"openid"));
            map.put("out_trade_no",WeChatPayUtil.regex(xml,"out_trade_no"));
            map.put("time_end",WeChatPayUtil.regex(xml,"time_end"));
            map.put("total_fee",WeChatPayUtil.simpleRegex(xml,"total_fee"));
            map.put("trade_type",WeChatPayUtil.regex(xml,"trade_type"));
            map.put("transaction_id",WeChatPayUtil.regex(xml,"transaction_id"));
        }
        map.put("sign",WeChatPayUtil.createSign(map));
        return map;
    }


}
