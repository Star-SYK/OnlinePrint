package com.gwd.controller;
import com.gwd.service.FileService;
import com.gwd.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
/**
 * @Description: OnlinePrint
 * @Param:
 * @return:
 * @Author: ChenYu
 * @Date: 2019/5/4
 */
@RequestMapping("/get")
@Controller
public class GetController {

    @Resource
    private UserService userService;

    @Resource
    private FileService fileService;

    @RequestMapping("/online/{md5}/{fileId}")
    public void Online(@PathVariable("md5")String md5,@PathVariable("fileId") Integer fileId,HttpServletResponse response) throws IOException {
        fileService.online(md5,fileId,response);
    }

}
