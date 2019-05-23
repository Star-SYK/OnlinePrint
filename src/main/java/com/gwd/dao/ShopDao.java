package com.gwd.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.gwd.entity.Shop;
import org.apache.ibatis.annotations.Param;
/**
 * @Description: OnlinePrint
 * @Param:
 * @return:
 * @Author: ChenYu
 * @Date: 2019/5/4
 */
public interface ShopDao extends BaseMapper<Shop> {
    void updateTokenByPhone(@Param("phone") String phone,@Param("token") String token);
    Integer getIdByPhoneAndPassword(@Param("phone") String phone,@Param("password") String password);
    Integer getIdByToken(@Param("token") String token);
    String getShopPhoneByaddress(@Param("address") String address);
}
