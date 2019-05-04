package com.gwd.service;


import com.gwd.entity.Property;

public interface PropertyService {
    Property get(Property property);
    Property getById(Integer id);
}
