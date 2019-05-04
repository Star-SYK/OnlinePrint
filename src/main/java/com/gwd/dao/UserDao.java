package com.gwd.dao;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.gwd.entity.User;
/**
 * @Description: OnlinePrint
 * @Param:
 * @return:
 * @Author: ChenYu
 * @Date: 2019/5/4
 */
public interface UserDao extends BaseMapper<User> {
    Integer getUserIdByOpenId(String openId);
    Integer getUserIdByToken(String token);
    String getOpenIdByUserId(Integer userId);
}
