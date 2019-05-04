package com.gwd.service;


import com.gwd.entity.Property;
/**
 * @Description: OnlinePrint
 * @Param:
 * @return:
 * @Author: ChenYu
 * @Date: 2019/5/4
 */
public interface PropertyService {
    Property get(Property property);
    Property getById(Integer id);
}
