package com.gwd.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.gwd.entity.File;
import org.apache.ibatis.annotations.Param;

public interface FileDao extends BaseMapper<File> {
    File getByIdAndUserId(@Param("id")Integer id,@Param("userId")Integer userId);
}
