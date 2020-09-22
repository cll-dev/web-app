package com.yitongyin.modules.ad.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yitongyin.modules.ad.dao.AdAuthCodeDao;
import com.yitongyin.modules.ad.entity.AdAuthCodeEntity;
import com.yitongyin.modules.ad.entity.AdUserEntity;
import com.yitongyin.modules.ad.service.AdAuthCodeService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
public class AdAuthCodeServiceImpl extends ServiceImpl<AdAuthCodeDao, AdAuthCodeEntity> implements AdAuthCodeService {

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void insert(AdAuthCodeEntity authCode) {
        authCode.setSendDate(new Date());
        this.save(authCode);
    }

    @Override
    public AdAuthCodeEntity findLastByPhone(String phone,Integer type) {
        QueryWrapper<AdAuthCodeEntity> query = new QueryWrapper<>();
        query.eq("type",type);
        query.eq("mobile_phone",phone);
        query.orderByDesc("send_date");
        return this.getOne(query);
    }
}
