package com.gwd.controller;

import com.gwd.Util.WeChatUtil;
import com.gwd.entity.ResponseData;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import java.io.IOException;
/**
 * @Description: OnlinePrint
 * @Param:
 * @return:
 * @Author: ChenYu
 * @Date: 2019/5/4
 */
@RestController
@RequestMapping("/wechat")
public class WeChatController {

    // 用户名 注册
    @RequestMapping("/get/{code}")
    public String get(@PathVariable String code) throws IOException {
        return WeChatUtil.WeChatGetOpenId(code);
    }
}
