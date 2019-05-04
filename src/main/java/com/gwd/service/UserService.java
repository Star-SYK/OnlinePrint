package com.gwd.service;


import com.gwd.entity.User;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
/**
 * @Description: OnlinePrint
 * @Param:
 * @return:
 * @Author: ChenYu
 * @Date: 2019/5/4
 */
public interface UserService {
    void addOrUpdate(HttpServletResponse response,User user);
    void Update(User user);
    Integer getUserIdByToken(String token);
    Integer getUserId(String openId);
    Integer hasLogin(HttpServletRequest request);
    String getOpenIdByUserId(Integer userId);
}
