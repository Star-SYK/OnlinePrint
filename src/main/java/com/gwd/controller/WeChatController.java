package com.gwd.controller;

import com.gwd.Util.WeChatUtil;
import com.gwd.entity.ResponseData;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.io.IOException;

@RestController
@RequestMapping("/wechat")
public class WeChatController {

    // 用户名 注册
    @RequestMapping("/get/{code}")
    public String get(@PathVariable String code) throws IOException {
        return WeChatUtil.WeChatGetOpenId(code);
    }
}
