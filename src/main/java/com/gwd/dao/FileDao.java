package com.gwd.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.gwd.entity.File;
import org.apache.ibatis.annotations.Param;
/**
 * @Description: OnlinePrint
 * @Param:
 * @return:
 * @Author: ChenYu
 * @Date: 2019/5/4
 */
public interface FileDao extends BaseMapper<File> {
    File getByIdAndUserId(@Param("id")Integer id,@Param("userId")Integer userId);

    File getLastFileByIdAndUserId(@Param("userId")Integer userId);

}
