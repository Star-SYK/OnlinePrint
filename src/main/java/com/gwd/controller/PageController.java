package com.gwd.controller;


import com.gwd.entity.ResponseData;
import com.gwd.service.FileService;
import com.gwd.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
/**
 * @Description: OnlinePrint
 * @Param:
 * @return:
 * @Author: ChenYu
 * @Date: 2019/5/4
 */
@Controller
public class PageController {
    private UserService userService;

    @Resource
    private FileService fileService;


    @RequestMapping("/upfile")
    public String upload(){
       // System.out.println("/sss");
        return "upfile";
    }

}
