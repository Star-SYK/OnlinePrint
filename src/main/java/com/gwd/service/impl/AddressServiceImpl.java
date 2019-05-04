package com.gwd.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.gwd.dao.AddressDao;
import com.gwd.entity.Address;
import com.gwd.service.AddressService;
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
@Service("addressService")
public class AddressServiceImpl implements AddressService {

    @Resource
    private AddressDao addressDao;


    @Override
    public List<Address> getList(Integer userId) {
        return addressDao.selectList(new EntityWrapper<Address>().eq("user_id",userId));
    }

    @Override
    public void add(Address address) {
        addressDao.insert(address);
    }

    @Override
    public Address getDefaultAddress(Integer userId) {
        List<Address> addresses = addressDao.selectList(new EntityWrapper<Address>().eq("user_id",userId).eq("has_default",true)
                .orderBy("create_time",false));
        if(addresses.size()>0){
            return addresses.get(0);
        }
        return null;
    }

    @Override
    public void updateDefaultAddress(Integer userId, Integer addressId) {
        addressDao.updateAddressDefaultByUserId(userId,false);
        addressDao.updateAddressDefaultById(addressId,userId,true);
    }
}
