package com.yitongyin.modules.ad.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yitongyin.common.utils.RegxUtil;
import com.yitongyin.modules.ad.dao.TMerchantDao;
import com.yitongyin.modules.ad.entity.TMerchantEntity;
import com.yitongyin.modules.ad.form.MerchantReport;
import com.yitongyin.modules.ad.service.AdOssService;
import com.yitongyin.modules.ad.service.TMerchantService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;


@Service("tMerchantService")
public class TMerchantServiceImpl extends ServiceImpl<TMerchantDao, TMerchantEntity> implements TMerchantService {

    @Autowired
    AdOssService adOssService;
    @Autowired
    TMerchantDao tMerchantDao;

    @Override
    public TMerchantEntity getInfoById(long id) {
        QueryWrapper<TMerchantEntity> query = new QueryWrapper<>();
        query.eq("merchantId", id);
        return this.getOne(query);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void updateInfo(TMerchantEntity entity) {
        TMerchantEntity oldEntity=getInfoById(entity.getMerchantid());
        entity.setUpdatedate(new Date());
        UpdateWrapper<TMerchantEntity> update = new UpdateWrapper<>();
        update.set("logoOssId",entity.getLogoossid());
        update.set("address",entity.getAddress());
        update.set("province",entity.getProvince());
        update.set("city",entity.getCity());
        update.set("county",entity.getCounty());
        update.set("houseNumber",entity.getHousenumber());
        update.set("linkTel",entity.getLinktel());
        update.set("linkPhone",entity.getLinkphone());
        update.set("linkQQ",entity.getLinkqq());
        update.set("shopPhotoId",entity.getShopphotoid());
        update.set("saleOssId",entity.getSaleossid());
        update.set("designOssId",entity.getDesignossid());
        update.set("opening_time",entity.getOpeningTime());
        update.eq("merchantId", entity.getMerchantid());
        this.update(update);
        if(oldEntity.getLogoossid()!=null){
            adOssService.updateStatusById(oldEntity.getLogoossid(),2);
        }
        if (entity.getLogoossid() != null) {
            this.adOssService.updateStatusById(entity.getLogoossid(),1);
        }
        if(oldEntity.getDesignossid()!=null){
            adOssService.updateStatusById(oldEntity.getDesignossid(),2);
        }
        if (entity.getDesignossid() != null) {
            this.adOssService.updateStatusById(entity.getDesignossid(),1);
        }
        if(oldEntity.getSaleossid()!=null){
            adOssService.updateStatusById(oldEntity.getSaleossid(),2);
        }
        if (entity.getSaleossid() != null) {
            this.adOssService.updateStatusById(entity.getSaleossid(),1);
        }
        if(oldEntity.getShopphotoid()!=null){
            adOssService.updateStatusById(oldEntity.getShopphotoid(),2);
        }
        if (entity.getShopphotoid() != null) {
            this.adOssService.updateStatusById(entity.getShopphotoid(),1);
        }
        if(oldEntity.getDescr()!=null){
            List<String> oldUrls= RegxUtil.getUrl(oldEntity.getDescr());
            if(oldUrls!=null&&oldUrls.size()>0){
                adOssService.updateStatusByUrl(oldUrls,2);
            }
        }
        if(entity.getDescr()!=null){
            List<String> urls= RegxUtil.getUrl(entity.getDescr());
            if(urls!=null&&urls.size()>0){
                adOssService.updateStatusByUrl(urls,1);
            }
        }
    }

    @Override
    public  TMerchantEntity getInfoByUserId(long userId) {
        QueryWrapper<TMerchantEntity> query = new QueryWrapper<>();
        query.eq("user_id", userId);
        return this.getOne(query);
    }

    @Override
    public TMerchantEntity getInfoByEmail(String email) {
        QueryWrapper<TMerchantEntity> query = new QueryWrapper<>();
        query.eq("email", email);
        return this.getOne(query);
    }

    @Transactional
    @Override
    public Boolean updColor(String color,Long merchantId) {
        UpdateWrapper<TMerchantEntity> update = new UpdateWrapper<>();
        update.set("theme_color",color);
        update.eq("merchantId",merchantId);
        return this.update(update);
    }

    @Override
    public TMerchantEntity getOneByName(String name) {
        QueryWrapper<TMerchantEntity> query = new QueryWrapper<>();
        query.eq("merchantName", name);
        return this.getOne(query);
    }

    @Override
    public MerchantReport getReport(Long merchantId) {
        List<MerchantReport> reportList = tMerchantDao.getReport();
        List<MerchantReport> myReport=reportList.stream().filter(e->e.getMerchantId().equals(merchantId)).collect(Collectors.toList());
        if(myReport==null||myReport.size()==0){
            return null;
        }
        MerchantReport merchantReport =myReport.get(0);
        double avgSumViewCount=reportList.stream().mapToInt(MerchantReport::getSumViewCount).filter(x -> x !=0 ).average().getAsDouble();//访客平均值
        double avgSumHitCount=reportList.stream().mapToInt(MerchantReport::getSumHitCount).filter(x -> x !=0 ).average().getAsDouble();//产品浏览平均值
        double avgCountMpShow=reportList.stream().mapToInt(MerchantReport::getCountMpShow).filter(x -> x !=0 ).average().getAsDouble();//经营中产品平均值
        double avgCountSpc=reportList.stream().mapToInt(MerchantReport::getCountSpc).filter(x -> x !=0 ).average().getAsDouble();//已配置价格产品平均值
        double avgCountSupplier=reportList.stream().mapToInt(MerchantReport::getCountSupplier).filter(x -> x !=0 ).average().getAsDouble();//收藏厂家平均值
        double avgCountCase=reportList.stream().mapToInt(MerchantReport::getCountCase).filter(x -> x !=0 ).average().getAsDouble();//案例平均值
        double avgDesignerContactNumber=reportList.stream().mapToInt(MerchantReport::getDesignerContactNumber).filter(x -> x !=0 ).average().getAsDouble();//设计师联系次数平均值
        double avgMaterialSendNumber=reportList.stream().mapToInt(MerchantReport::getMaterialSendNumber).filter(x -> x !=0 ).average().getAsDouble();//素材下载次数平均值
        merchantReport.setAvgSumViewCount(avgSumViewCount);
        merchantReport.setAvgSumHitCount(avgSumHitCount);
        merchantReport.setAvgCountMpShow(avgCountMpShow);
        merchantReport.setAvgCountSpc(avgCountSpc);
        merchantReport.setAvgCountSupplier(avgCountSupplier);
        merchantReport.setAvgCountCase(avgCountCase);
        merchantReport.setAvgDesignerContactNumber(avgDesignerContactNumber);
        merchantReport.setAvgMaterialSendNumber(avgMaterialSendNumber);
        return merchantReport;
    }

    @Override
    public void updateDes(TMerchantEntity entity) {
        TMerchantEntity oldEntity=getInfoById(entity.getMerchantid());
        entity.setUpdatedate(new Date());
        this.updateById(entity);
        if(oldEntity.getDescr()!=null){
            List<String> oldUrls= RegxUtil.getUrl(oldEntity.getDescr());
            if(oldUrls!=null&&oldUrls.size()>0){
                adOssService.updateStatusByUrl(oldUrls,2);
            }
        }
        if(entity.getDescr()!=null){
            List<String> urls= RegxUtil.getUrl(entity.getDescr());
            if(urls!=null&&urls.size()>0){
                adOssService.updateStatusByUrl(urls,1);
            }
        }
    }
}
