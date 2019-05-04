package com.gwd.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.gwd.entity.OrderInfo;

public interface OrderInfoDao extends BaseMapper<OrderInfo> {
    void updateHasPay(Integer id);
    void updateHasAccomplish(Integer id);
}
