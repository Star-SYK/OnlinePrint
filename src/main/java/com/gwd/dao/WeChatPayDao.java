package com.gwd.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.gwd.entity.WeChatPay;
import org.apache.ibatis.annotations.Param;

public interface WeChatPayDao extends BaseMapper<WeChatPay> {
    WeChatPay getByOrderId(Integer orderId);
    void updatePayResult(@Param("orderId") Integer orderId,@Param("payResult") String payResult);
    void updatePayReturnCode(@Param("orderId") Integer orderId,@Param("returnCode") String returnCode);
}
