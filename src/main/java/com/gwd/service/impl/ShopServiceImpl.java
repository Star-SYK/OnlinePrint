package com.gwd.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.gwd.Util.CookieUtil;
import com.gwd.Util.TokenUtil;
import com.gwd.dao.ShopDao;
import com.gwd.entity.Shop;
import com.gwd.service.ShopService;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;
import java.util.List;


@Service("shopService")
public class ShopServiceImpl implements ShopService{

    @Resource
    private ShopDao shopDao;

    @Override
    public List<Shop> getList(Integer collegeId) {
        return shopDao.selectList(new EntityWrapper<Shop>().eq("college_id",collegeId).eq("open",true));
    }

    @Override
    public Integer login(String phone, String password, HttpServletResponse response) {
        Integer id = shopDao.getIdByPhoneAndPassword(phone,password);
        if(id != null){
            try {
                String token = TokenUtil.CreateToken(shopDao.selectById(id).getPhone());
                shopDao.updateTokenByPhone(phone,token);
                CookieUtil.add(response,token);
                return id;
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}