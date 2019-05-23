package com.gwd.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.gwd.dao.OrderInfoDao;
import com.gwd.entity.OrderInfo;
import com.gwd.service.OrderInfoService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
/**
 * @Description: OnlinePrint
 * @Param:
 * @return:
 * @Author: ChenYu
 * @Date: 2019/5/4
 */
@Service("orderInfoService")
public class OrderInfoServiceImpl implements OrderInfoService {

    private final int n = 10;

    @Resource
    private OrderInfoDao orderInfoDao;


    @Override
    public void add(OrderInfo orderInfo) {
        orderInfoDao.insert(orderInfo);
    }

    @Override
    public OrderInfo getById(Integer id) {
        return orderInfoDao.selectById(id);
    }


    @Override
    public List<OrderInfo> list(Integer userId, Integer page) {
        return orderInfoDao.selectPage(new Page<OrderInfo>(page,n),new EntityWrapper<OrderInfo>().eq("user_id",userId).orderBy("create_time",false));
    }

    @Override
    public List<OrderInfo> listAccomplish(Integer userId, Integer page,boolean isAccomplish) {
        return orderInfoDao.selectPage(new Page<OrderInfo>(page,n),new EntityWrapper<OrderInfo>().eq("user_id",userId).eq("accomplish",isAccomplish).orderBy("create_time",false));
    }

    @Override
    public void updateHasPay(Integer id) {
        orderInfoDao.updateHasPay(id);
    }

    @Override
    public void updateHasReceipt(Integer id) {
        orderInfoDao.updateHasAccomplish(id);
    }

}