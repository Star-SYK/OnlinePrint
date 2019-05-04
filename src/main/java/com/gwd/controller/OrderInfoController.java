package com.gwd.controller;
import com.gwd.Util.PriceUtil;
import com.gwd.entity.File;
import com.gwd.entity.OrderInfo;
import com.gwd.entity.Property;
import com.gwd.entity.ResponseData;
import com.gwd.service.FileService;
import com.gwd.service.OrderInfoService;
import com.gwd.service.PropertyService;
import com.gwd.service.UserService;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;


@RequestMapping(value = "/order" , method = RequestMethod.POST)
@RestController
public class OrderInfoController {


    @Resource
    private FileService fileService;

    @Resource
    private OrderInfoService orderInfoService;

    @Resource
    private PropertyService propertyService;

    @Resource
    private UserService userService;

    @RequestMapping("/add")
    public ResponseData add(@RequestBody OrderInfo orderInfo, HttpServletRequest request, HttpServletResponse response){
        ResponseData responseData = new ResponseData();
        Integer userId = userService.hasLogin(request);
        if(userId == null){
           responseData.setStatusOther("请先登录");
           return responseData;
        }
        File file = fileService.get(orderInfo.getFileId());
        if(orderInfo.getFileId()==null){
            responseData.setStatusOther("文件Id不能为空");
            return responseData;
        }
        if(file.getPage()!=orderInfo.getPageNum()){
            responseData.setStatusOther("订单文件页数错误");
            return responseData;
        }
        Property property = propertyService.getById(orderInfo.getPropertyId());
        System.out.println(orderInfo);
        if((int)(orderInfo.getPrice()*100)!= PriceUtil.CalculatePrice(property.getPrice(),orderInfo.getPageNum(),orderInfo.getNum())){
        //    System.out.println((int)(orderInfo.getPrice()*100));
       //     System.out.println(PriceUtil.CalculatePrice(property.getPrice(),orderInfo.getPageNum(),orderInfo.getNum()));
            responseData.setStatusOther("订单价格信息有误");
            return responseData;
        }
        orderInfo.setUserId(userId);
        orderInfo.setCreateTime(new Date());
        orderInfoService.add(orderInfo);
        responseData.setData(orderInfo.getId());
    //    System.out.println(orderInfo);
        return responseData;
    }


    @RequestMapping("/list/{page}")
    public ResponseData list( @PathVariable("page")Integer page, HttpServletRequest request){
        ResponseData responseData = new ResponseData();
        Integer userId = userService.hasLogin(request);
        if(userId == null){
            responseData.setStatusOther("请先登录");
            return responseData;
        }
        responseData.setData(orderInfoService.list(userId,page));
        return responseData;
    }


    @RequestMapping("/list/accomplish/{isAccomplish}/{page}")
    public ResponseData listAccomplish( @PathVariable Boolean isAccomplish,@PathVariable Integer page, HttpServletRequest request){
        ResponseData responseData = new ResponseData();
        Integer userId = userService.hasLogin(request);
        if(userId == null){
            responseData.setStatusOther("请先登录");
            return responseData;
        }
        responseData.setData(orderInfoService.listAccomplish(userId,page,isAccomplish));
        return responseData;
    }


}
