package com.yitongyin.modules.ad.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yitongyin.modules.ad.dao.AdTagDao;
import com.yitongyin.modules.ad.entity.AdTagEntity;
import com.yitongyin.modules.ad.service.AdTagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("adTagService")
public class AdTagServiceImpl extends ServiceImpl<AdTagDao, AdTagEntity> implements AdTagService {

    @Autowired
    AdTagDao adTagDao;

    @Override
    public List<AdTagEntity> getClientByMerchantId(Long merchantId) {
        return adTagDao.getClientByMerId(merchantId);
    }

    @Override
    public List<AdTagEntity> getListByMerchantId(Long merchantId) {
        QueryWrapper<AdTagEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("tagId","name");
        queryWrapper.eq("merchant_id",merchantId);
        return this.list(queryWrapper);
    }

}