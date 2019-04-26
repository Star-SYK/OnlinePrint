package com.gwd.Util;


import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class TokenUtil {

    //公共密钥客户端不会知道
    public static String SECRET="print";

    public static  String  CreateToken(String openId) throws UnsupportedEncodingException {
        //签名发布时间
        Date iatDate=new Date();
        // System.out.println(iatDate);//英文时间
        //设置签名过期时间  一周
        Calendar nowTime=Calendar.getInstance();
        nowTime.add(Calendar.DAY_OF_WEEK,7);
        Date expiresDate=nowTime.getTime();
        //System.out.println(expiresDate);
        Map<String,Object> map=new HashMap<String, Object>();
        map.put("alg","HS256");//设置算法 为HS256
        map.put("typ","JWT");//设置类型为JWT
        String token=JWT.create().withHeader(map)
                .withClaim("openId",openId)
                .withIssuedAt(iatDate)//设置签发时间
                .withExpiresAt(expiresDate)//设置过去时间 过期时间大于签发时间
                .sign(Algorithm.HMAC256(SECRET));//用公共密钥加密
        //System.out.println(token);
        return token;
    }


    public static Map<String,Claim> VerifyToken(String token) throws UnsupportedEncodingException {
        // 用公共密钥解密验证
        JWTVerifier verifier = JWT.require(Algorithm.HMAC256(SECRET)).build();
        DecodedJWT jwt;
        try{
            jwt = verifier.verify(token);
        }catch (Exception e)
        {
            throw new RuntimeException("登录凭证已过去，请重新登录");
        }
        return jwt.getClaims();
    }

    public void TestToken(String token) throws UnsupportedEncodingException {
        System.out.println("Token:"+token);
        Map<String,Claim> claims=VerifyToken(token);
        System.out.println(claims.get("name").asString());
        System.out.println(claims.get("age").asString());
        System.out.println(claims.get("username").asString());
        System.out.println(claims.get("org")==null?null:claims.get("org").asString());

        //测试过期token
//        String GuoQiToken="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJhdXRoMCJ9.izVguZPRsBQ5Rqw6dhMvcIwy8_9lQnrO3vpxGwPCuzs";
//
//        Map<String,Claim> claims2=verifyToken(GuoQiToken);
    }
}
