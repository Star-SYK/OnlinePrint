package com.gwd.service;

import com.gwd.entity.Address;

import java.util.List;
/**
 * @Description: OnlinePrint
 * @Param:
 * @return:
 * @Author: ChenYu
 * @Date: 2019/5/4
 */
public interface AddressService {
    List<Address> getList(Integer userId);
    void add(Address address);
    Address getDefaultAddress(Integer userId);
    void updateDefaultAddress(Integer userId,Integer addressId);
}
