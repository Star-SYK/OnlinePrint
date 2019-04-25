package com.gwd.dao;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.gwd.entity.User;
 
public interface UserDao extends BaseMapper<User> {
    Integer getUserIdByOpenId(String openId);
    Integer getUserIdByToken(String token);
    String getOpenIdByUserId(Integer userId);
}
