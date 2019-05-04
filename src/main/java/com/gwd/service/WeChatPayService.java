package com.gwd.service;

import com.gwd.entity.WeChatPay;
import org.apache.ibatis.annotations.Param;

public interface WeChatPayService {
    WeChatPay getById(Integer id);
    void save(WeChatPay weChatPay);
    WeChatPay getByOrderId(Integer orderId);
    void updatePayResult(Integer orderId,String payResult);
    void updatePayReturnCode(@Param("orderId") Integer orderId, @Param("ReturnCode") String returnCode);
}
