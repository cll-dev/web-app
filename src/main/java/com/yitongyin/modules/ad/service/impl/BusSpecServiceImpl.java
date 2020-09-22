package com.yitongyin.modules.ad.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yitongyin.common.utils.PageUtils;
import com.yitongyin.common.utils.Query;
import com.yitongyin.modules.ad.dao.BusSpecDao;
import com.yitongyin.modules.ad.entity.BusSpecEntity;
import com.yitongyin.modules.ad.service.BusSpecService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("busSpecService")
public class BusSpecServiceImpl extends ServiceImpl<BusSpecDao, BusSpecEntity> implements BusSpecService {
    @Autowired
    private BusSpecDao busSpecDao;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<BusSpecEntity> page = this.page(
                new Query<BusSpecEntity>().getPage(params),
                new QueryWrapper<BusSpecEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public List<BusSpecEntity> getKeyValues() {

        return busSpecDao.getKeyValues();
    }

}