package com.gwd.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.gwd.entity.Property;
import org.apache.ibatis.annotations.Param;

public interface PropertyDao extends BaseMapper<Property> {
    Property get(@Param("property") Property property);
    Property getById(Integer id);
}
