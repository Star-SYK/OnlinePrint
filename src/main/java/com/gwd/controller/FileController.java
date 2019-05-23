package com.gwd.controller;
import com.alibaba.fastjson.JSON;
import com.gwd.entity.File;
import com.gwd.entity.ResponseData;
import com.gwd.service.FileService;
import com.gwd.service.UserService;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
/**
 * @Description: OnlinePrint
 * @Param:
 * @return:
 * @Author: ChenYu
 * @Date: 2019/5/4
 */
@RequestMapping
//@RequestMapping(value = "/file" , method = RequestMethod.POST)
@RestController
public class FileController {
    @Resource
    private UserService userService;

    @Resource  
    private FileService fileService;

    @Transactional
    @RequestMapping("/upload")
    public Object upload(@RequestParam(value = "openId") String openId, @RequestParam(value = "uploadFile") MultipartFile uploadFile,HttpServletRequest request){
        ResponseData responseData = new ResponseData();
        Integer userId = userService.getUserId(openId);
        if(userId == null){
            responseData.setStatusOther("没有该用户");
        }else {
            try{
                  Integer id = fileService.add(userId,uploadFile);
                  responseData.setData(fileService.get(id));
                  HttpSession session = request.getSession();
                  session.setAttribute("fileId",id);
            }catch (Exception e){
                e.printStackTrace();
                responseData.setStatusOther("上传文件错误");
            }
        }
        return responseData;
    }

    @Transactional
    @RequestMapping("/get/current/file")
    public ResponseData get(HttpServletRequest request){
        ResponseData responseData = new ResponseData();
        Integer userId = userService.hasLogin(request);
        if(userId == null){
            responseData.setStatusOther("请先登录");
            return responseData;
        }
        HttpSession session = request.getSession();
        Object fileId = session.getAttribute("fileId");
        //System.out.println(Integer.valueOf(fileId.toString()));
        if(fileId != null){
            File file = fileService.get(Integer.valueOf(fileId.toString()));
            if(file != null && file.getUserId() == userId){
                //  System.out.println(file);
                responseData.setData(file);
            }
        }
        return responseData;
    }



    @Transactional
    @RequestMapping("/get/last/file")
    public ResponseData getLastFile(HttpServletRequest request){
        ResponseData responseData = new ResponseData();
        Integer userId = userService.hasLogin(request);
        if(userId == null){
            responseData.setStatusOther("请先登录");
            return responseData;
        }
        File file = fileService.getLastFileByIdAndUserId(userId);
        responseData.setData(file);
        return responseData;
    }


    @Transactional
    @RequestMapping("/get/{fileId}")
    public ResponseData get(@PathVariable(value = "fileId") Integer fileId, HttpServletRequest request){
        ResponseData responseData = new ResponseData();
        Integer userId = userService.hasLogin(request);
        if(userId == null){
            responseData.setStatusOther("请先登录");
            return responseData;
        }
        File file = fileService.get(fileId);
        if(file != null && file.getUserId() == userId){
          //  System.out.println(file);
            responseData.setData(file);
        }
        return responseData;
    }



}  