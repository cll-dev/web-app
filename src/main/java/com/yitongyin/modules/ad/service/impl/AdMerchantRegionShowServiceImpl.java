package com.yitongyin.modules.ad.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yitongyin.common.exception.RRException;
import com.yitongyin.common.utils.PageUtils;
import com.yitongyin.common.utils.Query;
import com.yitongyin.modules.ad.dao.AdMerchantRegionShowDao;
import com.yitongyin.modules.ad.dao.BusRegionShowDao;
import com.yitongyin.modules.ad.entity.AdMerchantProductEntity;
import com.yitongyin.modules.ad.entity.AdMerchantRegionShowEntity;
import com.yitongyin.modules.ad.entity.AdMerchantServiceTypeEntity;
import com.yitongyin.modules.ad.entity.BusRegionShowEntity;
import com.yitongyin.modules.ad.service.*;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("adMerchantRegionShowService")
public class AdMerchantRegionShowServiceImpl extends ServiceImpl<AdMerchantRegionShowDao, AdMerchantRegionShowEntity> implements AdMerchantRegionShowService {

    @Autowired
    BusRegionShowDao busRegionShowDao;
    @Autowired
    BusRegionShowService busRegionShowService;
    @Autowired
    AdOssService adOssService;
    @Autowired
    AdMerchantProductService adMerchantProductService;
    @Autowired
    AdMerchantServiceTypeService adMerchantServiceTypeService;

    @Override
    public PageUtils queryPage(Map<String, Object> params,long merchantId) {
        QueryWrapper<AdMerchantRegionShowEntity> query = new QueryWrapper<>();
        query.eq("region",params.get("region"));
        query.eq("merchant_id",merchantId);
        query.orderByDesc("order_number");
        IPage<AdMerchantRegionShowEntity> page = this.page(
                new Query<AdMerchantRegionShowEntity>().getPage(params),
                query
        );

        return new PageUtils(page);
    }

    @Override
    public Map<String, List<AdMerchantRegionShowEntity>> getByMerchantId(Long merchantId) {
        QueryWrapper<AdMerchantRegionShowEntity> query = new QueryWrapper<>();
        query.eq("merchant_id", merchantId);
        query.orderByDesc("order_number");
        List<AdMerchantRegionShowEntity> list =this.list(query);
       // List<AdMerchantRegionShowEntity> newList= new ArrayList<>();
        list.forEach( e -> {
            if(e.getCoverResId()!=null){
                e.setCoverUrl(adOssService.findById(e.getCoverResId())==null?null:adOssService.findById(e.getCoverResId()).getUrl());
            }
            if(e.getProductHref()!=null&&!e.getProductHref().equals("#")){
                String prefix=StringUtils.substring(e.getProductHref(),0,3);
                String a= StringUtils.substringAfterLast(e.getProductHref(),"=");
                if(prefix.equals("/mp")){
                    try{
                        if(StringUtils.isNotBlank(a)){
                            AdMerchantProductEntity productEntity =adMerchantProductService.mpGetOneByMerIdAndProId(merchantId,Long.valueOf(a));
                           if(productEntity!=null){
                               e.setProId(productEntity.getId());
                              // newList.add(e);
                           }
                        }
                    }catch (NumberFormatException c){
                        c.printStackTrace();
                        throw  new RRException("数据异常");
                    }
                }else{
                    if(StringUtils.isNotBlank(a)&&!prefix.equals("/ar")){
                        AdMerchantServiceTypeEntity entity =adMerchantServiceTypeService.getIsShowById(Long.valueOf(a));
                        if(entity!=null) {
//                            AdMerchantServiceTypeEntity curEntity = adMerchantServiceTypeService.getByServiceAndMerId(merchantId,entity.getServiceTypeId());
                            e.setTypeId(entity.getTypeid());
                            //newList.add(e);
                        }
                    }
                }
            }
        } );
        Map<String, List<AdMerchantRegionShowEntity>> listMap = list.stream().collect(Collectors.groupingBy(AdMerchantRegionShowEntity::getRegion));
        return listMap;
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean syncHeaderquarter(Long merchantId) {
        //删除已有数据
        QueryWrapper<AdMerchantRegionShowEntity> query = new QueryWrapper<>();
        query.eq("merchant_id", merchantId);
        this.remove(query);

        //查询总后台广告数据
        QueryWrapper<BusRegionShowEntity> regionShowQuery = new QueryWrapper<>();
        regionShowQuery.select
                ("showId","region","converLogResId"
                ,"title","href","publishDate","publishAble","orderNumber");
        regionShowQuery.eq("`group`", "system");
        regionShowQuery.eq("publishAble", 1);
        List<BusRegionShowEntity> regionShowList = busRegionShowService.list(regionShowQuery);
        List<AdMerchantRegionShowEntity> merchantRsList = new ArrayList<>();
        for (BusRegionShowEntity item : regionShowList) {
            AdMerchantRegionShowEntity entity = new AdMerchantRegionShowEntity();
            entity.setMerchantId(merchantId);
            entity.setShowId(item.getShowid());
            entity.setCreateDate(new Date());
            entity.setRegion(item.getRegion());
            entity.setCoverResId(item.getConverlogresid());
            entity.setTitle(item.getTitle());
            entity.setProductHref(item.getHref());
            entity.setPublishDate(item.getPublishdate());
            entity.setPublishAble(item.getPublishable());
            entity.setOrderNumber(item.getOrdernumber());
            entity.setHits(0);
            merchantRsList.add(entity);
        }
        return this.saveBatch(merchantRsList);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean updHref(AdMerchantRegionShowEntity entity) {
        UpdateWrapper<AdMerchantRegionShowEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.set("product_href",entity.getProductHref());
        updateWrapper.set("cover_res_id",entity.getCoverResId());
        updateWrapper.eq("id",entity.getId());
        return this.update(updateWrapper);
    }

}