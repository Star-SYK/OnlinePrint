package com.gwd.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.gwd.entity.OrderInfo;
/**
 * @Description: OnlinePrint
 * @Param:
 * @return:
 * @Author: ChenYu
 * @Date: 2019/5/4
 */
public interface OrderInfoDao extends BaseMapper<OrderInfo> {
    void updateHasPay(Integer id);
    void updateHasAccomplish(Integer id);
    void updateHasReceipt(Integer id);
}
