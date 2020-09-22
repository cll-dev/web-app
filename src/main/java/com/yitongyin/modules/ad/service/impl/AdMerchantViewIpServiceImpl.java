package com.yitongyin.modules.ad.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yitongyin.modules.ad.dao.AdMerchantViewIpDao;
import com.yitongyin.modules.ad.entity.AdMerchantViewIpEntity;
import com.yitongyin.modules.ad.service.AdMerchantViewIpService;
import org.springframework.stereotype.Service;

import java.util.List;


@Service("adMerchantViewIpService")
public class AdMerchantViewIpServiceImpl extends ServiceImpl<AdMerchantViewIpDao, AdMerchantViewIpEntity> implements AdMerchantViewIpService {


    @Override
    public AdMerchantViewIpEntity getByIpAndTime(Long viewId,String ip) {
        QueryWrapper<AdMerchantViewIpEntity> query =new QueryWrapper<>();
        query.eq("view_ip_address",ip);
        query.eq("view_id",viewId);
        return this.getOne(query);
    }

    @Override
    public AdMerchantViewIpEntity getByIpAndMerIdS(List<Object> viewIds, String ip) {
        if(viewIds==null||viewIds.size()==0)
            return null;
        QueryWrapper<AdMerchantViewIpEntity> query =new QueryWrapper<>();
        query.eq("view_ip_address",ip);
        query.in("view_id",viewIds);
        return this.getOne(query);
    }
}