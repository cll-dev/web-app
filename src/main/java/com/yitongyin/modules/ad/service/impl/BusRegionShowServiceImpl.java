package com.yitongyin.modules.ad.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yitongyin.common.utils.PageUtils;
import com.yitongyin.common.utils.Query;
import com.yitongyin.modules.ad.dao.BusRegionShowDao;
import com.yitongyin.modules.ad.entity.BusRegionShowEntity;
import com.yitongyin.modules.ad.service.BusRegionShowService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("busRegionShowService")
public class BusRegionShowServiceImpl extends ServiceImpl<BusRegionShowDao, BusRegionShowEntity> implements BusRegionShowService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<BusRegionShowEntity> page = this.page(
                new Query<BusRegionShowEntity>().getPage(params),
                new QueryWrapper<BusRegionShowEntity>()
        );

        return new PageUtils(page);
    }

}