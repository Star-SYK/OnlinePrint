package com.gwd.service;

import com.gwd.entity.OrderInfo;

import java.util.List;

public interface OrderInfoService {
    void add(OrderInfo orderInfo);
    OrderInfo getById(Integer id);
    List<OrderInfo> list(Integer userId,Integer page);
    List<OrderInfo> listAccomplish(Integer userId,Integer page,boolean isAccomplish);
    void updateHasPay(Integer id);
}
