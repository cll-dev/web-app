package com.yitongyin.modules.ad.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yitongyin.common.utils.PageUtils;
import com.yitongyin.common.utils.Query;
import com.yitongyin.modules.ad.dao.AdFitterServiceAreaDao;
import com.yitongyin.modules.ad.entity.AdFitterServiceAreaEntity;
import com.yitongyin.modules.ad.service.AdFitterServiceAreaService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("adFitterServiceAreaService")
public class AdFitterServiceAreaServiceImpl extends ServiceImpl<AdFitterServiceAreaDao, AdFitterServiceAreaEntity> implements AdFitterServiceAreaService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AdFitterServiceAreaEntity> page = this.page(
                new Query<AdFitterServiceAreaEntity>().getPage(params),
                new QueryWrapper<AdFitterServiceAreaEntity>()
        );

        return new PageUtils(page);
    }

}