package com.gwd.controller;

import com.alibaba.fastjson.JSONObject;
import com.gwd.Util.WeChatUtil;
import com.gwd.entity.ResponseData;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.util.encoders.Base64;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.security.AlgorithmParameters;
import java.security.Security;
import java.util.Arrays;

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



    @RequestMapping("/get/user/phone")
    public Object getPhoneNumber(String encryptedData, String session_key, String iv) {
//        System.out.println(encryptedData);
//        System.out.println(session_key);
//        System.out.println(iv);
        // 被加密的数据
        byte[] dataByte = Base64.decode(encryptedData);
        // 加密秘钥
        byte[] keyByte = Base64.decode(session_key);
        // 偏移量
        byte[] ivByte = Base64.decode(iv);
        try {
            // 如果密钥不足16位，那么就补足.  这个if 中的内容很重要
            int base = 16;
            if (keyByte.length % base != 0) {
                int groups = keyByte.length / base + (keyByte.length % base != 0 ? 1 : 0);
                byte[] temp = new byte[groups * base];
                Arrays.fill(temp, (byte) 0);
                System.arraycopy(keyByte, 0, temp, 0, keyByte.length);
                keyByte = temp;
            }
            // 初始化
            Security.addProvider(new BouncyCastleProvider());
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            SecretKeySpec spec = new SecretKeySpec(keyByte, "AES");
            AlgorithmParameters parameters = AlgorithmParameters.getInstance("AES");
            parameters.init(new IvParameterSpec(ivByte));
            cipher.init(Cipher.DECRYPT_MODE, spec, parameters);// 初始化
            byte[] resultByte = cipher.doFinal(dataByte);
            if (null != resultByte && resultByte.length > 0) {
                String result = new String(resultByte, "UTF-8");
                System.out.println(JSONObject.parseObject(result).toString());
                return JSONObject.parseObject(result);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
