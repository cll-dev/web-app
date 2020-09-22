package com.yitongyin.modules.ad.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yitongyin.common.utils.PageUtils;
import com.yitongyin.common.utils.Query;
import com.yitongyin.modules.ad.dao.BusMaterialIndustryDao;
import com.yitongyin.modules.ad.entity.BusMaterialIndustryEntity;
import com.yitongyin.modules.ad.service.BusMaterialIndustryService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("busMaterialIndustryService")
public class BusMaterialIndustryServiceImpl extends ServiceImpl<BusMaterialIndustryDao, BusMaterialIndustryEntity> implements BusMaterialIndustryService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<BusMaterialIndustryEntity> page = this.page(
                new Query<BusMaterialIndustryEntity>().getPage(params),
                new QueryWrapper<BusMaterialIndustryEntity>()
        );

        return new PageUtils(page);
    }

}