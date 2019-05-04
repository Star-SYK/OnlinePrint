package com.gwd.service.impl;

import java.io.Serializable;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gwd.Util.CookieUtil;
import com.gwd.entity.User;
import org.springframework.stereotype.Service;
import com.gwd.dao.UserDao;
import com.gwd.service.UserService;

/**
 * @Description: OnlinePrint
 * @Param:
 * @return:
 * @Author: ChenYu
 * @Date: 2019/5/4
 */
@Service("userService")  
public class UserServiceImpl implements UserService,Serializable{

    @Resource  
    private UserDao userDao;

    @Override
    public void addOrUpdate(HttpServletResponse response,User user) {
        Integer id = userDao.getUserIdByOpenId(user.getOpenId());
        if(id == null){
            userDao.insert(user);
        }else {
            user.setId(id);
            userDao.updateById(user);
        }
        CookieUtil.add(response,user.getToken());
    }

    @Override
    public void Update(User user) {
        userDao.updateById(user);
    }

    @Override
    public Integer getUserIdByToken(String token) {
        return userDao.getUserIdByToken(token);
    }


    @Override
    public Integer getUserId(String openId) {
        return userDao.getUserIdByOpenId(openId);
    }

    @Override
    public Integer hasLogin(HttpServletRequest request) {
        String token = CookieUtil.get(request);
        return userDao.getUserIdByToken(token);
    }

    @Override
    public String getOpenIdByUserId(Integer userId) {
        return userDao.getOpenIdByUserId(userId);
    }



}