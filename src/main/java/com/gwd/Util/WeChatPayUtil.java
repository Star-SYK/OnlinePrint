package com.gwd.Util;

import org.apache.commons.codec.digest.DigestUtils;

import java.io.IOException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
/**
 * @Description: OnlinePrint
 * @Param:
 * @return:
 * @Author: ChenYu
 * @Date: 2019/5/4
 */
public class WeChatPayUtil {

    public static final String url = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    public static final String appid = "wx828671bfb24c2981";
    public static final String mch_id = "1455573202";
    public static final String key = "8102b22a5e81e840176d9f381ec6f837";
    public static final String trade_type = "JSAPI";
    public static final String notify_url = "http://120.77.32.233/print/pay/inform";
    public static final String body = "期末考啦-在线打印";




     /*appId  nonceStr  package(wx211847224785484ae62dacf93289773240)*/

    public static String RequestWeChat(Map map) throws IOException {
        map.put("appid",appid);
        map.put("mch_id",mch_id);
        map.put("trade_type",trade_type);
        map.put("notify_url",notify_url);
        map.put("body",body);
        map.put("sign",createSign(map));
        String date = createXml(map);
        String responseXml = NetIOUtil.IoUtil(url,date);
        return responseXml;
    }



    public static String createXml(Map map){
        StringBuffer xml = new StringBuffer();
        xml.append("<xml>");
        xml.append("\r\n");
        Iterator<Map.Entry<String, String>> entries = map.entrySet().iterator();
        while (entries.hasNext()) {
            Map.Entry<String, String> entry = entries.next();
            xml.append("<"+entry.getKey()+">");
            xml.append(entry.getValue());
            xml.append("</"+entry.getKey()+">");
            xml.append("\r\n");
        }
        xml.append("</xml>");
       // System.out.println(xml.toString());
        return xml.toString();
    }


    public static String createSign(Map map){
        String mapStr = map.toString();
        String stringSignTemp = mapStr.substring(1,mapStr.length()-1).replaceAll(", ","&")+"&key="+key;
//        System.out.println(stringSignTemp);
        String sign = DigestUtils.md5Hex(stringSignTemp).toUpperCase();
        return sign;
    }


    public static String simpleRegex(String xml,String label) {
        String context = "";
        //正则表达式
        String regex = "<"+label+">(.*?)</"+label+">";
        Pattern pattern = Pattern.compile(regex);// 匹配的模式
        Matcher m = pattern.matcher(xml);
        //匹配的有多个
        List<String> list = new ArrayList<String>();
        while (m.find()) {
            context = m.group(1);
            return context;
        }
        return null;
    }



    public static String regex(String xml,String label) {
        String context = "";
        //正则表达式
        String regex = "<"+label+">(.*?)</"+label+">";
        Pattern pattern = Pattern.compile(regex);// 匹配的模式
        Matcher m = pattern.matcher(xml);
        //匹配的有多个
        List<String> list = new ArrayList<String>();
        while (m.find()) {
            context = m.group(1);
        }
       if(context!=null && !context.equals("")){
           String s = context;
           Pattern p = Pattern.compile(".*<!\\[CDATA\\[(.*)\\]\\]>.*");
           Matcher m2 = p.matcher(s);
           if(m2.matches()) {
             //  System.out.println(m2.group(1));
               return m2.group(1);
           }
       }
       return null;
    }



}
