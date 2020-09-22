package com.yitongyin.modules.ad.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yitongyin.common.utils.PageUtils;
import com.yitongyin.common.utils.Query;
import com.yitongyin.modules.ad.dao.AdMerchantDynamicDao;
import com.yitongyin.modules.ad.entity.AdMerchantDynamicEntity;
import com.yitongyin.modules.ad.service.AdMerchantDynamicService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("adMerchantDynamicService")
public class AdMerchantDynamicServiceImpl extends ServiceImpl<AdMerchantDynamicDao, AdMerchantDynamicEntity> implements AdMerchantDynamicService {

    @Override
    public PageUtils queryPage(Map<String, Object> params,Long merchantId) {
        QueryWrapper<AdMerchantDynamicEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("merchant_id",merchantId);
        queryWrapper.orderByDesc("create_time");
        IPage<AdMerchantDynamicEntity> page = this.page(
                new Query<AdMerchantDynamicEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }

    @Override
    public AdMerchantDynamicEntity getByTitle(String title,Long merchantId) {
        QueryWrapper<AdMerchantDynamicEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("title",title);
        queryWrapper.eq("merchant_id",merchantId);
        return this.getOne(queryWrapper);
    }

}