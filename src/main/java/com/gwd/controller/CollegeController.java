package com.gwd.controller;

import com.gwd.entity.ResponseData;
import com.gwd.service.CollegeService;
import com.gwd.service.UserService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;


@RequestMapping(value = "/college" , method = RequestMethod.POST)
@RestController
public class CollegeController {

    @Resource
    private CollegeService collegeService;

    @Resource
    private UserService userService;

    @RequestMapping("/get/list")
    public ResponseData get(HttpServletRequest request) throws IOException {
        ResponseData responseData = new ResponseData();
        if(userService.hasLogin(request) == null){
            responseData.setStatusOther("请先登录");
            return responseData;
        }
        responseData.setData(collegeService.getAll());
        return responseData;
    }

}
