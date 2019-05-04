package com.gwd.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.gwd.entity.Property;
import org.apache.ibatis.annotations.Param;
/**
 * @Description: OnlinePrint
 * @Param:
 * @return:
 * @Author: ChenYu
 * @Date: 2019/5/4
 */
public interface PropertyDao extends BaseMapper<Property> {
    Property get(@Param("property") Property property);
    Property getById(Integer id);
}
