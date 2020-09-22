package com.yitongyin.modules.ad.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yitongyin.modules.ad.dao.AdOssDao;
import com.yitongyin.modules.ad.entity.AdOssEntity;

import com.yitongyin.modules.ad.service.AdOssService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;


@Service("adOssService")
public class AdOssServiceImpl extends ServiceImpl<AdOssDao, AdOssEntity> implements AdOssService {

    @Override
    public AdOssEntity findById(long id) {
        return this.getById(id);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatusById(long id,Integer status) {
        AdOssEntity entity = new AdOssEntity();
        entity.setStatus(status);
        UpdateWrapper<AdOssEntity> update= new UpdateWrapper<>();
        update.eq("id",id);
        this.update(entity,update);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatusByIds(List<Long> ids,Integer status) {
        if(ids==null||ids.size()==0) return;
        UpdateWrapper<AdOssEntity> update= new UpdateWrapper<>();
        update.set("status",status);
        update.in("id",ids);
        this.update(update);
    }

    @Override
    public List<AdOssEntity> findByIds(List<Long> ids) {
        List<AdOssEntity> list = new ArrayList<>();
        QueryWrapper<AdOssEntity> query = new QueryWrapper<>();
        if(ids!=null&&ids.size()!=0){
            query.in("id",ids);
            list= this.list(query);
        }
        return list;

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateStatusByUrl(List<String> urls, Integer status) {
        if(urls==null||urls.size()==0) return;
        UpdateWrapper<AdOssEntity> update= new UpdateWrapper<>();
        update.set("status",status);
        update.in("url",urls);
        this.update(update);
    }

//    @Override
//    public PageUtils queryPage(Map<String, Object> params) {
//        IPage<AdOssEntity> page = this.page(
//                new Query<AdOssEntity>().getPage(params),
//                new QueryWrapper<AdOssEntity>()
//        );
//
//        return new PageUtils(page);
//    }

}