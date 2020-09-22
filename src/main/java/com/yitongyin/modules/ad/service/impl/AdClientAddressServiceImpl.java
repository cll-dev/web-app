package com.yitongyin.modules.ad.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yitongyin.modules.ad.dao.AdClientAddressDao;
import com.yitongyin.modules.ad.entity.AdClientAddressEntity;
import com.yitongyin.modules.ad.service.AdClientAddressService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("adClientAddressService")
public class AdClientAddressServiceImpl extends ServiceImpl<AdClientAddressDao, AdClientAddressEntity> implements AdClientAddressService {


    @Override
    public List<AdClientAddressEntity> getListByClientid(Long clientId) {
        QueryWrapper<AdClientAddressEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("client_id",clientId);
        return this.list(queryWrapper);
    }

    @Override
    public boolean updIsDefaultByClientId(Long clientId) {
        UpdateWrapper<AdClientAddressEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("is_default",0);
        updateWrapper.eq("is_default",1).eq("client_id",clientId);
        return this.update(updateWrapper);
    }
}