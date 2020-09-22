package com.yitongyin.modules.ad.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yitongyin.common.utils.PageUtils;
import com.yitongyin.common.utils.Query;
import com.yitongyin.modules.ad.dao.BusSpecValueDao;
import com.yitongyin.modules.ad.entity.BusSpecValueEntity;
import com.yitongyin.modules.ad.service.BusSpecValueService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("busSpecValueService")
public class BusSpecValueServiceImpl extends ServiceImpl<BusSpecValueDao, BusSpecValueEntity> implements BusSpecValueService {

    @Override
    public PageUtils queryPage(Map<String, Object> params,String key) {
        QueryWrapper<BusSpecValueEntity> query = new QueryWrapper<>();
        query.select("id","val_name");
        query.eq("specKey",key);
        IPage<BusSpecValueEntity> page = this.page(
                new Query<BusSpecValueEntity>().getPage(params),
               query
        );

        return new PageUtils(page);
    }

    @Override
    public List<BusSpecValueEntity> getByKey(String key) {
        QueryWrapper<BusSpecValueEntity> query = new QueryWrapper<>();
        query.select("id","val_name");
        query.eq("specKey",key);
        return this.list(query);
    }

}