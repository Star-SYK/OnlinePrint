package com.gwd.service;

import com.gwd.entity.Address;

import java.util.List;

public interface AddressService {
    List<Address> getList(Integer userId);
    void add(Address address);
    Address getDefaultAddress(Integer userId);
    void updateDefaultAddress(Integer userId,Integer addressId);
}
