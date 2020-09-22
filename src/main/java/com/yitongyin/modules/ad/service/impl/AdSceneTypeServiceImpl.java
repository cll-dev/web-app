package com.yitongyin.modules.ad.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yitongyin.modules.ad.dao.AdSceneTypeDao;
import com.yitongyin.modules.ad.entity.AdSceneTypeEntity;
import com.yitongyin.modules.ad.service.AdSceneTypeService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("adSceneTypeService")
public class AdSceneTypeServiceImpl extends ServiceImpl<AdSceneTypeDao, AdSceneTypeEntity> implements AdSceneTypeService {


    @Override
    public List<AdSceneTypeEntity> getPositionSearchList() {
        QueryWrapper<AdSceneTypeEntity> parentQueryWrapper = new QueryWrapper<>();
        parentQueryWrapper.eq("name","位置").eq("parent_id",0);
        AdSceneTypeEntity positionParent=this.getOne(parentQueryWrapper);
        if(positionParent==null) return null;
        QueryWrapper<AdSceneTypeEntity> childQueryWrapper = new QueryWrapper<>();
        childQueryWrapper.eq("parent_id",positionParent.getStid());
        return this.list(childQueryWrapper);
    }

    @Override
    public List<AdSceneTypeEntity> getSpaceSearchList() {
        QueryWrapper<AdSceneTypeEntity> parentQueryWrapper = new QueryWrapper<>();
        parentQueryWrapper.eq("name","空间").eq("parent_id",0);
        AdSceneTypeEntity positionParent=this.getOne(parentQueryWrapper);
        if(positionParent==null) return null;
        QueryWrapper<AdSceneTypeEntity> childQueryWrapper = new QueryWrapper<>();
        childQueryWrapper.eq("parent_id",positionParent.getStid());
        return this.list(childQueryWrapper);
    }
}