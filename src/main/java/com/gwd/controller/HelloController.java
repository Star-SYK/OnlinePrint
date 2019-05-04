package com.gwd.controller;
import com.gwd.Util.CookieUtil;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RequestMapping("/hello")
@RestController
public class HelloController {

    @ApiOperation(value = "hello", notes = "测试一下下")
    @RequestMapping("/hello")
    public String hello() {
        return "hello,this is a SpringBoot demo";
    }

    @RequestMapping("/get/cookies")
    public Object get(HttpServletRequest request) {
        return request.getCookies();
    }

    @RequestMapping("/out")
    public void out(HttpServletRequest request, HttpServletResponse response) {
        CookieUtil.delete(request,response);
    }
}
