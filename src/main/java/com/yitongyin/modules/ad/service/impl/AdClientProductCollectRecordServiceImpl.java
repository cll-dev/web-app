package com.yitongyin.modules.ad.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yitongyin.common.utils.PageUtils;
import com.yitongyin.modules.ad.dao.AdClientProductCollectRecordDao;
import com.yitongyin.modules.ad.entity.AdClientProductCollectRecordEntity;
import com.yitongyin.modules.ad.entity.AdGroupProductRateEntity;
import com.yitongyin.modules.ad.entity.AdMerchantSpecGroupEntity;
import com.yitongyin.modules.ad.service.AdClientProductCollectRecordService;
import com.yitongyin.modules.ad.service.AdGroupProductRateService;
import com.yitongyin.modules.ad.service.AdMerchantSpecGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("adClientProductCollectRecordService")
public class AdClientProductCollectRecordServiceImpl extends ServiceImpl<AdClientProductCollectRecordDao, AdClientProductCollectRecordEntity> implements AdClientProductCollectRecordService {

    @Autowired
    AdClientProductCollectRecordDao adClientProductCollectRecordDao;
    @Autowired
    AdMerchantSpecGroupService adMerchantSpecGroupService;
    @Autowired
    private AdGroupProductRateService adGroupProductRateService;
    @Override
    public PageUtils queryPage(Map<String, Object> params, Long clientId, Long merchantId) {
        int page=Integer.valueOf(params.get("page").toString());
        int limit=Integer.valueOf(params.get("limit").toString());
        int start=(Integer.valueOf(params.get("page").toString())-1)*limit;
        List<AdClientProductCollectRecordEntity> list= adClientProductCollectRecordDao.getListByClientId(clientId,merchantId,start,limit);
        list.forEach( e -> {
            AdMerchantSpecGroupEntity groupEntity =adMerchantSpecGroupService.getLowestPriceByMerAndPro(merchantId,e.getProductId());
            if(groupEntity!=null){
                AdGroupProductRateEntity adGroupProductRateEntity = adGroupProductRateService.getRateByClientIdAndMerchantProcutId(clientId
                        ,e.getMerchantProductId(),merchantId);
                if(adGroupProductRateEntity!=null){
                    if(adGroupProductRateEntity.getIsShow()==1&&groupEntity.getPrice()!=null){
                        e.setLowPrice(groupEntity.getPrice().multiply(adGroupProductRateEntity.getPriceRate()));
                    }
                }else{
                    e.setLowPrice(groupEntity.getPrice());
                }
                e.setDay(groupEntity.getDeliveryDay());
            }
        } );
        Integer count= adClientProductCollectRecordDao.getCountByClientId(clientId,merchantId);
        return new PageUtils(list,count,limit,page);
    }

    @Override
    public boolean addOneByTime(AdClientProductCollectRecordEntity entity) {
        QueryWrapper<AdClientProductCollectRecordEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("merchant_product_id",entity.getMerchantProductId());
        queryWrapper.eq("client_id",entity.getClientId());
        AdClientProductCollectRecordEntity queryEntity=this.getOne(queryWrapper);
        if(queryEntity==null){
            return this.save(entity);
        }
        return true;
    }

    @Override
    public boolean delOne(AdClientProductCollectRecordEntity entity) {
        QueryWrapper<AdClientProductCollectRecordEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("merchant_product_id",entity.getMerchantProductId());
        queryWrapper.eq("client_id",entity.getClientId());
        return this.remove(queryWrapper);
    }

    @Override
    public boolean getStaOne(AdClientProductCollectRecordEntity entity) {
        QueryWrapper<AdClientProductCollectRecordEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("merchant_product_id",entity.getMerchantProductId());
        queryWrapper.eq("client_id",entity.getClientId());
        if(this.getOne(queryWrapper)!=null){
            return true;
        }
        return false;
    }

}