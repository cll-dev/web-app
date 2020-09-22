package com.yitongyin.modules.ad.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yitongyin.common.utils.PageUtils;
import com.yitongyin.common.utils.Query;
import com.yitongyin.common.utils.UtilString;
import com.yitongyin.modules.ad.dao.AdMerchantCaseDao;
import com.yitongyin.modules.ad.entity.AdMerchantCaseEntity;
import com.yitongyin.modules.ad.entity.AdOssEntity;
import com.yitongyin.modules.ad.entity.TServiceTypeEntity;
import com.yitongyin.modules.ad.form.CaseConditions;
import com.yitongyin.modules.ad.service.AdMerchantCaseService;
import com.yitongyin.modules.ad.service.AdOssService;
import com.yitongyin.modules.ad.service.TServiceTypeService;
import com.yitongyin.modules.mp.View.CaseInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("adMerchantCaseService")
public class AdMerchantCaseServiceImpl extends ServiceImpl<AdMerchantCaseDao, AdMerchantCaseEntity> implements AdMerchantCaseService {
    @Autowired
    private AdMerchantCaseDao adMerchantCaseDao;
    @Autowired
    private AdOssService adOssService;

    @Override
    public PageUtils queryPage(Map<String, Object> params, CaseConditions caseConditions,Long merchantId) {
        int page=Integer.valueOf(params.get("page").toString());
        int limit=Integer.valueOf(params.get("limit").toString());
        int start=(Integer.valueOf(params.get("page").toString())-1)*limit;
        List<AdMerchantCaseEntity> list =adMerchantCaseDao.getListByConditions(merchantId,
                 caseConditions.getProductId()
                ,caseConditions.getProName(),caseConditions.getIsMoreHist(),caseConditions.getIsNewDate(),start,limit);
        Integer count =adMerchantCaseDao.getCountConditions(merchantId,
                caseConditions.getProductId(), caseConditions.getProName());
        return new PageUtils(list,count,limit,page);
    }

    @Override
    public List<AdMerchantCaseEntity> getOssIdsByIds(List<Long> ids) {
        QueryWrapper<AdMerchantCaseEntity> query = new QueryWrapper<>();
        query.select("attachment_ids");
        query.in("id",ids);
        return this.list(query);
    }



    @Override
    public Integer countByProId(Long proId, Long merchantId) {
        QueryWrapper<AdMerchantCaseEntity> query = new QueryWrapper<>();
        query.eq("merchant_id",merchantId);
        query.eq("product_id",proId);
        return this.count(query);
    }

    @Override
    public PageUtils listByMerchantIdAndProId(Map<String, Object> params,Long proId, Long merchantId) {
        QueryWrapper<AdMerchantCaseEntity> query = new QueryWrapper<>();
        query.eq("merchant_id", merchantId);
        query.eq("product_id", proId);
        query.orderByDesc("create_time");
        IPage<AdMerchantCaseEntity> page = this.page(
                new Query<AdMerchantCaseEntity>().getPage(params),
                query
        );
        return new PageUtils(page);
    }

    @Override
    public CaseInfo getOneByMerchantIdAndProId(Long proId, Long merchantId,String proName) {
        QueryWrapper<AdMerchantCaseEntity> query = new QueryWrapper<>();
        query.eq("merchant_id",merchantId);
        query.eq("product_id",proId);
        query.orderByDesc("hits");
        AdMerchantCaseEntity entity=this.getOne(query);
        CaseInfo caseInfo=new CaseInfo();
        if(entity!=null){
            caseInfo.setContent(entity.getContent());
            caseInfo.setId(entity.getId());
            caseInfo.setTime(entity.getCreateTime());
            caseInfo.setProName(proName);
            if(entity.getAttachmentIds()!=null){
                List<String> ids= UtilString.stringToList(entity.getAttachmentIds());
                List<Long> longList=ids.stream().map(e->Long.valueOf(e)).collect(Collectors.toList());
                List<AdOssEntity> ossEntities =adOssService.findByIds(longList);
                List<String> urls=ossEntities.stream().map(e->e.getUrl()).collect(Collectors.toList());
                caseInfo.setPicUrls(urls);
            }
        }
        return caseInfo;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean synList(List<AdMerchantCaseEntity> list,Long merchantId) {
        QueryWrapper<AdMerchantCaseEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("merchant_id",merchantId);
        queryWrapper.isNotNull("commentId");
        List<AdMerchantCaseEntity> merchantList= this.list(queryWrapper);
        List<AdMerchantCaseEntity> filterList = list.stream()
                .filter(item -> !merchantList.stream()
                        .map(e -> e.getCommentid())
                        .collect(Collectors.toList())
                        .contains(item.getCommentid()))
                .collect(Collectors.toList());
        if(this.saveBatch(filterList)){
            return true;
        }
        return false;
    }

}