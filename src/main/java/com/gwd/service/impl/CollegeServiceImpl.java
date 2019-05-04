package com.gwd.service.impl;

import com.gwd.dao.CollegeDao;
import com.gwd.entity.College;
import com.gwd.service.CollegeService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

@Service("collegeService")
public class CollegeServiceImpl implements CollegeService {

    @Resource
    private CollegeDao collegeDao;


    @Override
    public List<College> getAll() {
        return collegeDao.selectList(null);
    }
}