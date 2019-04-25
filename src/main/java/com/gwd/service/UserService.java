package com.gwd.service;


import com.gwd.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface UserService {
    void addOrUpdate(HttpServletResponse response,User user);
    void Update(User user);
    Integer getUserIdByToken(String token);
    Integer getUserId(String openId);
    Integer hasLogin(HttpServletRequest request);
    String getOpenIdByUserId(Integer userId);
}
