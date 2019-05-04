package com.gwd.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.gwd.entity.Shop;
import org.apache.ibatis.annotations.Param;

public interface ShopDao extends BaseMapper<Shop> {
    void updateTokenByPhone(@Param("phone") String phone,@Param("token") String token);
    Integer getIdByPhoneAndPassword(@Param("phone") String phone,@Param("password") String password);
    Integer getIdByToken(@Param("token") String token);
}
