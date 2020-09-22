package com.yitongyin.modules.ad.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yitongyin.modules.ad.dao.TAreaDao;
import com.yitongyin.modules.ad.entity.TAreaEntity;

import com.yitongyin.modules.ad.service.TAreaService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("tAreaService")
public class TAreaServiceImpl extends ServiceImpl<TAreaDao, TAreaEntity> implements TAreaService {
    @Override
    public List<TAreaEntity> findProvinceList() {
        QueryWrapper<TAreaEntity> query = new QueryWrapper<>();
        query.select("id","name");
        query.eq("parentId",0);
        query.eq("status",1);
        return this.list(query);
    }

    @Override
    public List<TAreaEntity> findChildListByParentId(Integer parentId) {
        QueryWrapper<TAreaEntity> query = new QueryWrapper<>();
        query.select("id","name");
        query.eq("parentId",parentId);
        query.eq("status",1);
        return this.list(query);
    }

//    @Override
//    public PageUtils queryPage(Map<String, Object> params) {
//        IPage<TAreaEntity> page = this.page(
//                new Query<TAreaEntity>().getPage(params),
//                new QueryWrapper<TAreaEntity>()
//        );
//
//        return new PageUtils(page);
//    }

}