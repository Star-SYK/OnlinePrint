package com.gwd.service.impl;

import com.gwd.dao.PropertyDao;
import com.gwd.entity.Property;
import com.gwd.service.PropertyService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

@Service("propertyService")
public class PropertyServiceImpl implements PropertyService {

    @Resource
    private PropertyDao propertyDao;

    @Override
    public Property get(Property property) {
        return propertyDao.get(property);
    }

    @Override
    public Property getById(Integer id) {
        return propertyDao.selectById(id);
    }
}