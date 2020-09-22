package com.yitongyin.modules.ad.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yitongyin.modules.ad.dao.AdMerchantViewCountDao;
import com.yitongyin.modules.ad.entity.AdMerchantViewCountEntity;
import com.yitongyin.modules.ad.entity.AdMerchantViewIpEntity;
import com.yitongyin.modules.ad.service.AdMerchantViewCountService;
import com.yitongyin.modules.ad.service.AdMerchantViewIpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;


@Service("adMerchantViewCountService")
public class AdMerchantViewCountServiceImpl extends ServiceImpl<AdMerchantViewCountDao, AdMerchantViewCountEntity> implements AdMerchantViewCountService {

    @Autowired
    private AdMerchantViewIpService adMerchantViewIpService;

    @Override
    public AdMerchantViewCountEntity findLastCount(long merchantId) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
        QueryWrapper<AdMerchantViewCountEntity> query = new QueryWrapper<>();
        query.eq("merchant_id",merchantId);
        query.eq("view_date",df.format(new Date()));
        return this.getOne(query);
    }

    @Override
    public List<AdMerchantViewCountEntity> findWeekCount(long merchantId,String startTime,String endTime) {
        QueryWrapper<AdMerchantViewCountEntity> query = new QueryWrapper<>();
        query.between("view_date",startTime,endTime);
        query.eq("merchant_id",merchantId);
        query.orderByAsc("view_date");
        return this.list(query);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean saveOrUpdByIpAndToday(AdMerchantViewCountEntity entity) {
        QueryWrapper<AdMerchantViewCountEntity> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("merchant_id",entity.getMerchantId()).eq("view_date",entity.getTime());
        AdMerchantViewCountEntity countEntity =this.getOne(queryWrapper);
        int random=(int)(Math.random()*10+1);
        if(countEntity==null){
            entity.setViewCount(random);
            if(this.save(entity)){
                AdMerchantViewIpEntity ipEntity = new AdMerchantViewIpEntity();
                ipEntity.setViewId(entity.getId());
                ipEntity.setViewIpAddress(entity.getIp());
                ipEntity.setViewTime(entity.getViewDate());
                return adMerchantViewIpService.save(ipEntity);
            }else{
                return false;
            }
        } else {
            AdMerchantViewIpEntity ipEntity1 =adMerchantViewIpService.getByIpAndTime(countEntity.getId(),entity.getIp());
            if(ipEntity1==null){
                countEntity.setViewCount(countEntity.getViewCount()+random);
                if(this.updateById(countEntity)){
                    AdMerchantViewIpEntity ipEntity2 =new AdMerchantViewIpEntity();
                    ipEntity2.setViewTime(entity.getViewDate());
                    ipEntity2.setViewIpAddress(entity.getIp());
                    ipEntity2.setViewId(countEntity.getId());
                    return adMerchantViewIpService.save(ipEntity2);
                }else{
                    return  false;
                }
            }else{
                ipEntity1.setViewTime(new Date());
                return adMerchantViewIpService.updateById(ipEntity1);
            }
        }
    }

    @Override
    public List<Object> findByMerId(Long merchantId) {
        QueryWrapper<AdMerchantViewCountEntity> queryWrapper =new QueryWrapper<>();
        queryWrapper.eq("merchant_id",merchantId);
        return this.listObjs(queryWrapper);
    }
}