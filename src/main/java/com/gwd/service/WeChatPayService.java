package com.gwd.service;

import com.gwd.entity.WeChatPay;
import org.apache.ibatis.annotations.Param;
/**
 * @Description: OnlinePrint
 * @Param:
 * @return:
 * @Author: ChenYu
 * @Date: 2019/5/4
 */
public interface WeChatPayService {
    WeChatPay getById(Integer id);
    void save(WeChatPay weChatPay);
    WeChatPay getByOrderId(Integer orderId);
    void updatePayResult(Integer orderId,String payResult);
    void updatePayReturnCode(@Param("orderId") Integer orderId, @Param("ReturnCode") String returnCode);
}
