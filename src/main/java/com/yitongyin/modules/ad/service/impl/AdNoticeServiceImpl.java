package com.yitongyin.modules.ad.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yitongyin.common.utils.PageUtils;
import com.yitongyin.common.utils.Query;
import com.yitongyin.modules.ad.dao.AdNoticeDao;
import com.yitongyin.modules.ad.entity.AdNoticeEntity;
import com.yitongyin.modules.ad.service.AdNoticeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("adNoticeService")
public class AdNoticeServiceImpl extends ServiceImpl<AdNoticeDao, AdNoticeEntity> implements AdNoticeService {


    @Override
    public List<AdNoticeEntity> findNewByType(Integer type,Integer count) {

        return baseMapper.queryListByType(type,count);
    }

    @Override
    public PageUtils pageNewByType(Map<String, Object> params, Integer type) {
        QueryWrapper<AdNoticeEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("notice_type",4);
        IPage<AdNoticeEntity> page = this.page(
                new Query<AdNoticeEntity>().getPage(params),
                queryWrapper
        );

        return new PageUtils(page);
    }

}