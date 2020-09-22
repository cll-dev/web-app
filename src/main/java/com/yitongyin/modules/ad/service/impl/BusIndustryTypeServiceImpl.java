package com.yitongyin.modules.ad.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yitongyin.common.utils.PageUtils;
import com.yitongyin.common.utils.Query;
import com.yitongyin.modules.ad.dao.BusIndustryTypeDao;
import com.yitongyin.modules.ad.entity.BusIndustryTypeEntity;
import com.yitongyin.modules.ad.service.BusIndustryTypeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("busIndustryTypeService")
public class BusIndustryTypeServiceImpl extends ServiceImpl<BusIndustryTypeDao, BusIndustryTypeEntity> implements BusIndustryTypeService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<BusIndustryTypeEntity> page = this.page(
                new Query<BusIndustryTypeEntity>().getPage(params),
                new QueryWrapper<BusIndustryTypeEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<BusIndustryTypeEntity> getListOrder() {
        QueryWrapper<BusIndustryTypeEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByAsc("order_number");
        return this.list(queryWrapper);
    }

}