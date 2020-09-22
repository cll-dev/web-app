package com.yitongyin.modules.ad.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yitongyin.common.utils.PageUtils;
import com.yitongyin.common.utils.Query;
import com.yitongyin.modules.ad.dao.AdDesignerDao;
import com.yitongyin.modules.ad.entity.AdDesignerEntity;
import com.yitongyin.modules.ad.entity.AdOssEntity;
import com.yitongyin.modules.ad.service.AdDesignerService;
import com.yitongyin.modules.ad.service.AdOssService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("adDesignerService")
public class AdDesignerServiceImpl extends ServiceImpl<AdDesignerDao, AdDesignerEntity> implements AdDesignerService {

    @Autowired
    AdOssService adOssService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AdDesignerEntity> page = new Query<AdDesignerEntity>().getPage(params);
        long pageIndex = page.getCurrent();
        long pageSize = page.getSize();
        long start = (pageIndex-1)*pageSize;
        long end = pageIndex*pageSize;

        List<AdDesignerEntity> list = baseMapper.findPages(start,end);
        page.setRecords(list);
        page.setTotal(list.size());
        return new PageUtils(page);
    }

    @Override
    public AdDesignerEntity findById(Long id) {
        return baseMapper.findById(id);
    }

    @Override
    public AdDesignerEntity findByUserId(Long userId) {
        QueryWrapper<AdDesignerEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("userId",userId);
        AdDesignerEntity entity = this.getOne(queryWrapper);
        if(entity==null){
            return entity;
        }
        if(entity.getHeadImgId()!=null){
            AdOssEntity ossEntity = adOssService.getById(entity.getHeadImgId());
            if(ossEntity!=null){
                entity.setLogoUrl(ossEntity.getUrl());
            }
        }
        return entity;
    }

}