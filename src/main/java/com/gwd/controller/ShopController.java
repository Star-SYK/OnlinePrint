package com.gwd.controller;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gwd.Util.CookieUtil;
import com.gwd.Util.TokenUtil;
import com.gwd.dao.OrderInfoDao;
import com.gwd.dao.ShopDao;
import com.gwd.entity.File;
import com.gwd.entity.OrderInfo;
import com.gwd.entity.ResponseData;
import com.gwd.entity.Shop;
import com.gwd.service.FileService;
import com.gwd.service.OrderInfoService;
import com.gwd.service.ShopService;
import com.gwd.service.UserService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.DateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @Description: OnlinePrint
 * @Param:
 * @return:
 * @Author: ChenYu
 * @Date: 2019/5/4
 */
@RequestMapping(value = "/shop" , method = RequestMethod.POST)
@RestController
public class ShopController {

    @Resource
    private OrderInfoDao orderInfoDao;

    @Resource
    private FileService fileService;
    @Resource
    private ShopService shopService;
    @Resource
    private ShopDao shopDao;
    @Resource
    private UserService userService;

    @RequestMapping("/list/{collegeId}")
    public ResponseData getList(@PathVariable("collegeId")Integer collegeId, HttpServletRequest request) throws IOException {
        ResponseData  responseData  =  new ResponseData();
        if(userService.hasLogin(request) == null){
            responseData.setStatusOther("请先登录");
            return responseData;
        }
        List<Shop> list = shopService.getList(collegeId);
        for (Shop shop:list) {
            shop.setPassword(null);
            shop.setToken(null);
        }
        responseData.setData(list);
        return responseData;
    }

    @RequestMapping("/login")
    public ResponseData login(String phone, String password, HttpServletResponse response) throws IOException {
        ResponseData responseData = new ResponseData();
        if(shopService.login(phone,password,response)==null){
            responseData.setMsg("账号密码错误");
        }
        return responseData;
    }
    @RequestMapping("/update/info")
    public ResponseData UpdateInfo(HttpServletRequest request) throws IOException {
        Integer id = shopDao.getIdByToken(CookieUtil.get(request));
        System.out.println(shopDao.selectById(id));
        return null;
    }

    @RequestMapping("/list/order/{flag}/{haspay}/{today}")
    public ResponseData listOrder(@PathVariable("flag") boolean flag,@PathVariable("haspay") boolean haspay,@PathVariable("today")boolean today, HttpServletRequest request) throws IOException {
        ResponseData responseData = new ResponseData();
        Integer id = shopDao.getIdByToken(CookieUtil.get(request));
        if(id  == null){
            responseData.setNoLogin();
            return responseData;
        }
        Shop shop = shopDao.selectById(id);
        System.out.println(shop);
        List<OrderInfo> list = null;
        list = orderInfoDao.selectList(new EntityWrapper<OrderInfo>().eq("shop_address",shop.getAddress())
                    .eq("accomplish",flag).eq("haspay",haspay));
        responseData.setData(list);
        if(today){
            DateFormat df1 = DateFormat.getDateInstance(); //格式化后的时间格式：2016-2-19
            String str = df1.format(new Date());
            List<OrderInfo> newlist = list.stream().filter((x) -> {
                if(df1.format(x.getCreateTime()).contains(str)){
                    return true;
                }else {
                    return false;
                }
            }).collect(Collectors.toList());
            list = newlist;
        }
        Map<String,Object> map = new HashMap<>();
        map.put("size",list.size());
        double sum = 0;
        for (OrderInfo orderInfo:list) {
            sum += orderInfo.getPrice();
        }
        map.put("sum",String.format("%.2f", sum));
        List<Map<String,Object>> resultList = new ArrayList<>();
        for (OrderInfo o: list) {
            Map<String,Object> newMap= new HashMap<>();
            File file = fileService.get(o.getFileId());
            newMap.put("file",file);
            newMap.put("orderinfo",o);
            resultList.add(newMap);
        }
        map.put("list",resultList);
        responseData.setData(map);
        return responseData;
    }


    @RequestMapping("/order/accomplish/{orderId}")
    public ResponseData accomplish(@PathVariable("orderId") Integer orderId, HttpServletRequest request) throws IOException {
        ResponseData responseData = new ResponseData();
        Integer id = shopDao.getIdByToken(CookieUtil.get(request));
        if(id== null){
            responseData.setNoLogin();
            return  responseData;
        }
        OrderInfo orderInfo = orderInfoDao.selectById(orderId);
        Shop shop = shopDao.selectById(id);
        if(shop.getAddress().equals(orderInfo.getShopAddress())){
            if(orderInfo != null && orderInfo.isAccomplish()){
                responseData.setMsg("订单已完成");
                return  responseData;
            }
            orderInfoDao.updateHasAccomplish(orderId);
        }else {
            responseData.setMsg("该订单不是你的");
        }
        return responseData;
    }

    @RequestMapping(value = "/out",method = RequestMethod.POST)
    public void out(HttpServletRequest request,HttpServletResponse response){
        CookieUtil.delete(request,response);
    }

}
