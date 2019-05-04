package com.gwd.controller;
import com.gwd.entity.Property;
import com.gwd.entity.ResponseData;
import com.gwd.service.PropertyService;
import com.gwd.service.UserService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @Description: OnlinePrint
 * @Param:
 * @return:
 * @Author: ChenYu
 * @Date: 2019/5/4
 */
@RequestMapping(value = "/property" , method = RequestMethod.POST)
@RestController
public class PropertyController {

    @Resource
    private PropertyService propertyService;


    @Resource
    private UserService userService;

    @RequestMapping("/get")
    public ResponseData get(@RequestBody Property property, HttpServletRequest request) throws IOException {
        ResponseData responseData = new ResponseData();
        Integer userId = userService.hasLogin(request);
        if(userId == null){
            responseData.setStatusOther("请先登录");
            return responseData;
        }
        Property resultProperty = propertyService.get(property);
        responseData.setData(resultProperty);
//        System.out.println(property);
//        System.out.println(resultProperty);
        return responseData;
    }

}
