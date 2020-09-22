package com.yitongyin.modules.ad.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.yitongyin.modules.ad.dao.BasParamDao;
import com.yitongyin.modules.ad.entity.BasParamEntity;
import com.yitongyin.modules.ad.service.BasParamService;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;



@Service("basParamService")
public class BasParamServiceImpl extends ServiceImpl<BasParamDao, BasParamEntity> implements BasParamService {
    @Override
    public BasParamEntity getNameByKey(String key) {
        QueryWrapper<BasParamEntity> query = new QueryWrapper<>();
        query.select("paramName");
        query.eq("paramKey",key);
        return this.getOne(query);
    }

//    @Override
//    public PageUtils queryPage(Map<String, Object> params) {
//        IPage<BasParamEntity> page = this.page(
//                new Query<BasParamEntity>().getPage(params),
//                new QueryWrapper<BasParamEntity>()
//        );
//
//        return new PageUtils(page);
//    }

}