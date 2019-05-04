package com.gwd.controller;
import com.gwd.entity.Address;
import com.gwd.entity.ResponseData;
import com.gwd.service.AddressService;
import com.gwd.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Date;
/**
 * @Description: OnlinePrint
 * @Param:
 * @return:
 * @Author: ChenYu
 * @Date: 2019/5/4
 */
@RequestMapping(value = "/address" , method = RequestMethod.POST)
@RestController
public class AddressController {

    @Resource
    private AddressService addressService;

    @Resource
    private UserService userService;


    @RequestMapping(value = "/list")
    public ResponseData list(HttpServletRequest request){
        ResponseData  responseData  =  new ResponseData();
        Integer userId = userService.hasLogin(request);
        if(userId == null){
            responseData.setStatusOther("请先登录");
            return responseData;
        }
        responseData.setData(addressService.getList(userId));
        return responseData;
    }


    @RequestMapping(value = "/add")
    public ResponseData add(String address, HttpServletRequest request){
        ResponseData  responseData  =  new ResponseData();
        Integer userId = userService.hasLogin(request);
        if(userId == null){
            responseData.setStatusOther("请先登录");
            return responseData;
        }
        Address newAddress = new Address(userId,address,false,new Date());
        addressService.add(newAddress);
        return responseData;
    }


    @RequestMapping(value = "/get/default")
    public ResponseData getDefault(HttpServletRequest request){
        ResponseData  responseData  =  new ResponseData();
        Integer userId = userService.hasLogin(request);
        if(userId == null){
            responseData.setStatusOther("请先登录");
            return responseData;
        }
        responseData.setData(addressService.getDefaultAddress(userId));
        return responseData;
    }

    @RequestMapping(value = "/update/default/{addressId}")
    public ResponseData getDefault(@PathVariable("addressId")Integer addressId, HttpServletRequest request){
        ResponseData  responseData  =  new ResponseData();
        Integer userId = userService.hasLogin(request);
        if(userId == null){
            responseData.setStatusOther("请先登录");
            return responseData;
        }
        addressService.updateDefaultAddress(userId,addressId);
        return responseData;
    }





}
