package com.gwd.service.impl;

import com.gwd.dao.WeChatPayDao;
import com.gwd.entity.WeChatPay;
import com.gwd.service.WeChatPayService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
/**
 * @Description: OnlinePrint
 * @Param:
 * @return:
 * @Author: ChenYu
 * @Date: 2019/5/4
 */
@Service("weChatPayService")
public class WeChatPayServiceImpl implements WeChatPayService {

    @Resource
    private WeChatPayDao weChatPayDao;

    @Override
    public WeChatPay getById(Integer id) {
        return weChatPayDao.selectById(id);
    }

    @Override
    public void save(WeChatPay weChatPay) {
        weChatPayDao.insert(weChatPay);
    }

    @Override
    public WeChatPay getByOrderId(Integer orderId) {
        return weChatPayDao.getByOrderId(orderId);
    }

    @Override
    public void updatePayResult(Integer orderId, String payResult) {
        weChatPayDao.updatePayResult(orderId,payResult);
    }

    @Override
    public void updatePayReturnCode(Integer orderId, String returnCode) {
        weChatPayDao.updatePayReturnCode(orderId,returnCode);
    }
}
