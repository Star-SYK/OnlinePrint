package com.gwd.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gwd.Util.TokenUtil;
import com.gwd.dao.UserDao;
import com.gwd.entity.ResponseData;
import com.gwd.entity.User;
import org.springframework.web.bind.annotation.*;
import com.gwd.service.UserService;
import java.io.IOException;
import java.util.Date;

@RequestMapping(value = "/user" , method = RequestMethod.POST)
@RestController
public class UserController {

    @Resource  
    private UserService userService;

    @Resource
    private UserDao userDao;

    @RequestMapping("/login")
    public ResponseData login(@RequestBody User user,HttpServletRequest request , HttpServletResponse response) throws IOException {
        ResponseData responseData = new ResponseData();
        if(user.getOpenId() == null){
             responseData.setStatusOther("用户openId不能为空");
             return responseData;
        }
        String token = TokenUtil.CreateToken(user.getOpenId());
        user.setToken(token);
        user.setCreateTime(new Date());
        try{
            userService.addOrUpdate(response,user);
        }catch (RuntimeException e){
            e.printStackTrace();
            responseData.setStatusError();
        }
        System.out.println(user);
        return responseData;
    }



    @RequestMapping("/update")
    public ResponseData update(@RequestBody User user,HttpServletRequest request , HttpServletResponse response) throws IOException {
        ResponseData responseData = new ResponseData();
        Integer userId = userService.hasLogin(request);
        if(userId == null){
            responseData.setStatusOther("请先登录");
            return responseData;
        }
        user.setId(userId);
        user.setOpenId(null);
        userService.Update(user);
        return responseData;
    }




    @RequestMapping("/get/{id}")
    public ResponseData get(@PathVariable("id")Integer id, HttpServletRequest request , HttpServletResponse response) throws IOException {
        ResponseData responseData = new ResponseData();
        responseData.setData(userDao.selectById(id));
        return responseData;
    }



}  