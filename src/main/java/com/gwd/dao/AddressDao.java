package com.gwd.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.gwd.entity.Address;
import org.apache.ibatis.annotations.Param;
/**
 * @Description: OnlinePrint
 * @Param:
 * @return:
 * @Author: ChenYu
 * @Date: 2019/5/4
 */
public interface AddressDao extends BaseMapper<Address> {
    void updateAddressDefaultByUserId(@Param("userId")Integer userId,@Param("hasDefault")boolean hasDefault);
    void updateAddressDefaultById(@Param("id")Integer id,@Param("userId")Integer userId,@Param("hasDefault")boolean hasDefault);
}
