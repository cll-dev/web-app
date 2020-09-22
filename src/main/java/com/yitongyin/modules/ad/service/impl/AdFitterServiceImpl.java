package com.yitongyin.modules.ad.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yitongyin.common.utils.PageUtils;
import com.yitongyin.common.utils.Query;
import com.yitongyin.modules.ad.dao.AdFitterDao;
import com.yitongyin.modules.ad.entity.AdFitterEntity;
import com.yitongyin.modules.ad.service.AdFitterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


@Service("adFitterService")
public class AdFitterServiceImpl extends ServiceImpl<AdFitterDao, AdFitterEntity> implements AdFitterService {
    @Autowired
    AdFitterDao adFitterDao;
    @Override
    public PageUtils queryPage(Map<String, Object> params, Integer county) {
        List<Long> ids=adFitterDao.getFiterIdsByCounty(county);
        int page=Integer.valueOf(params.get("page").toString());
        int limit=Integer.valueOf(params.get("limit").toString());
        if(ids==null||ids.size()==0){
            PageUtils nullPageUtils =new PageUtils(new ArrayList<>(),0,limit,page);
            return nullPageUtils;
        }
        QueryWrapper<AdFitterEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.in("id",ids);
        queryWrapper.orderByDesc("view_counts");
        IPage<AdFitterEntity> pageUtil = this.page(
                new Query<AdFitterEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(pageUtil);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        QueryWrapper<AdFitterEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("view_counts");
        IPage<AdFitterEntity> pageUtil = this.page(
                new Query<AdFitterEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(pageUtil);
    }

}