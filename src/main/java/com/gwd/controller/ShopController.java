package com.gwd.controller;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gwd.Util.CookieUtil;
import com.gwd.Util.MyFileUtil;
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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
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
@RequestMapping(value = "/shop")
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
    public ResponseData getList(@PathVariable("collegeId") Integer collegeId, HttpServletRequest request) throws IOException {
        ResponseData responseData = new ResponseData();
        if (userService.hasLogin(request) == null) {
            responseData.setStatusOther("请先登录");
            return responseData;
        }
        List<Shop> list = shopService.getList(collegeId);
        for (Shop shop : list) {
            shop.setPassword(null);
            shop.setToken(null);
        }
        responseData.setData(list);
        return responseData;
    }


    @RequestMapping("/get/info")
    public ResponseData getInfo(HttpServletRequest request) throws IOException {
        System.setProperty("user.timezone", "GMT +08");
        ResponseData responseData = new ResponseData();
        Integer id = shopDao.getIdByToken(CookieUtil.get(request));
        Shop shop = shopDao.selectById(id);
        if (id == null) {
            responseData.setNoLogin();
            return responseData;
        }
        List<OrderInfo> list = orderInfoDao.selectList(new EntityWrapper<OrderInfo>().eq("shop_address", shop.getAddress()));
        SimpleDateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
        String str = df1.format(new Date());
        Integer todayNum = 0; //今日已接单数
        Integer accomplishNum = 0;  // 今日已完成单数
        double todayPrice = 0.0;  //今日已完成单数的价格

        Integer totalNum = 0; //总接单数
        double totalPrice = 0.0;  // 总价格

        for (OrderInfo o : list) {
            if (df1.format(o.getCreateTime()).split(" ")[0].equals(str)) {  // 今天的订单
                if (o.isHasPay()) {
                    if (o.isHasReceipt()) { //今日已接单数
                        todayNum++;
                    }
                    if (o.isAccomplish()) { //今日已完成单数
                        accomplishNum++;
                        todayPrice += o.getPrice();
                    }
                }
            }
            if (o.isAccomplish()) {
                totalNum++;
                totalPrice += o.getPrice();
            }
        }


        Map<String, Object> rs = new HashMap<>();

        rs.put("todayNum", todayNum);
        rs.put("totalNum", totalNum);
        rs.put("todayPrice", todayPrice);

        rs.put("totalPrice", totalPrice);
        rs.put("accomplishNum", accomplishNum);
        rs.put("date", str);

        responseData.setData(rs);
        return responseData;
    }

    @RequestMapping("/login")
    public ResponseData login(String phone, String password, HttpServletResponse response) throws IOException {
        ResponseData responseData = new ResponseData();
        if (shopService.login(phone, password, response) == null) {
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
    public ResponseData listOrder(@PathVariable("flag") boolean flag, @PathVariable("haspay") boolean haspay, @PathVariable("today") boolean today, HttpServletRequest request) throws IOException {
        ResponseData responseData = new ResponseData();
        Integer id = shopDao.getIdByToken(CookieUtil.get(request));
        if (id == null) {
            responseData.setNoLogin();
            return responseData;
        }
        Shop shop = shopDao.selectById(id);
        System.out.println(shop);
        List<OrderInfo> list = null;
        list = orderInfoDao.selectList(new EntityWrapper<OrderInfo>().eq("shop_address", shop.getAddress())
                .eq("accomplish", flag).eq("has_pay", haspay));
        responseData.setData(list);
        if (today) {
            DateFormat df1 = DateFormat.getDateInstance(); //格式化后的时间格式：2016-2-19
            String str = df1.format(new Date());
            List<OrderInfo> newlist = list.stream().filter((x) -> {
                if (df1.format(x.getCreateTime()).contains(str)) {
                    return true;
                } else {
                    return false;
                }
            }).collect(Collectors.toList());
            list = newlist;
        }
        Map<String, Object> map = new HashMap<>();
        map.put("size", list.size());
        double sum = 0;
        for (OrderInfo orderInfo : list) {
            sum += orderInfo.getPrice();
        }
        map.put("sum", String.format("%.2f", sum));
        List<Map<String, Object>> resultList = new ArrayList<>();
        for (OrderInfo o : list) {
            Map<String, Object> newMap = new HashMap<>();
            File file = fileService.get(o.getFileId());
            newMap.put("file", file);
            newMap.put("orderinfo", o);
            resultList.add(newMap);
        }
        map.put("list", resultList);
        responseData.setData(map);
        return responseData;
    }


    @RequestMapping("/list/order/receipt/{flag}/{accomplish}")
    public ResponseData listReceipt(@PathVariable("flag") boolean flag, @PathVariable("accomplish") boolean accomplish, HttpServletRequest request) throws IOException {
        ResponseData responseData = new ResponseData();
        Integer id = shopDao.getIdByToken(CookieUtil.get(request));
        if (id == null) {
            responseData.setNoLogin();
            return responseData;
        }
        Shop shop = shopDao.selectById(id);
        List<OrderInfo> list = null;
        list = orderInfoDao.selectList(new EntityWrapper<OrderInfo>().eq("shop_address", shop.getAddress())
                .eq("has_receipt", flag).eq("accomplish", accomplish).orderBy("priority", false).orderBy("id", false));
        Map<String, Object> map = new HashMap<>();
        List<Map<String, Object>> resultList = new ArrayList<>();
        for (OrderInfo o : list) {
            Map<String, Object> newMap = new HashMap<>();
            File file = fileService.get(o.getFileId());
            newMap.put("file", file);
            newMap.put("orderinfo", o);
            resultList.add(newMap);
        }
        map.put("list", resultList);
        responseData.setData(map);
        return responseData;
    }


    @RequestMapping("/order/accomplish/{orderId}")
    public ResponseData accomplish(@PathVariable("orderId") Integer orderId, HttpServletRequest request) throws IOException {
        ResponseData responseData = new ResponseData();
        Integer id = shopDao.getIdByToken(CookieUtil.get(request));
        if (id == null) {
            responseData.setNoLogin();
            return responseData;
        }
        OrderInfo orderInfo = orderInfoDao.selectById(orderId);
        Shop shop = shopDao.selectById(id);
        if (shop.getAddress().equals(orderInfo.getShopAddress())) {
            if (orderInfo != null && orderInfo.isAccomplish()) {
                responseData.setMsg("订单已完成");
                return responseData;
            }
            orderInfoDao.updateHasAccomplish(orderId);
        } else {
            responseData.setMsg("该订单不是你的");
        }
        return responseData;
    }


    @RequestMapping("/order/receipt/{orderId}")
    public ResponseData receipt(@PathVariable("orderId") Integer orderId, HttpServletRequest request) throws IOException {
        ResponseData responseData = new ResponseData();
        orderInfoDao.updateHasReceipt(orderId);
        return responseData;
    }

    private static SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");


    @RequestMapping("/download/{fileId}")
    public void download(@PathVariable(value = "fileId") Integer fileId, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ResponseData responseData = new ResponseData();
        File file = fileService.get(fileId);
        if (file == null) {
            responseData.setStatusOther("文件不存在");
        }
        String path = MyFileUtil.filePath + sdf1.format(file.getCreateTime()).split(" ")[0] + "//" + file.getMd5();
        if (file.getName().contains("pdf")) {
            path += ".pdf";
        }
        MyFileUtil.download(request, response, path, file.getName());
        // System.out.println(path);
    }


    @RequestMapping(value = "/out", method = RequestMethod.POST)
    public void out(HttpServletRequest request, HttpServletResponse response) {
        CookieUtil.delete(request, response);
    }

}
