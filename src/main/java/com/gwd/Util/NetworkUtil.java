package com.gwd.Util;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

public class NetworkUtil {

    public static String getIpAddress(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }
        return ip;
    }

    public static String GetType(HttpServletRequest request)
    {
        Enumeration typestr = request.getHeaderNames();
        String s1 = request.getHeader("user-agent");
        if(s1.contains("Android")) {
            return "Android移动客户端";
        } else if(s1.contains("iPhone")) {
            return "iPhone移动客户端";
        }  else if(s1.contains("iPad")) {
            return "iPad客户端";
        }  else {
            return "PC客户端";
        }
    }
}
