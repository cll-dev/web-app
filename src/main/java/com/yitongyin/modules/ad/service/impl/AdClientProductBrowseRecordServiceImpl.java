package com.yitongyin.modules.ad.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yitongyin.common.utils.PageUtils;
import com.yitongyin.modules.ad.dao.AdClientProductBrowseRecordDao;
import com.yitongyin.modules.ad.entity.AdClientProductBrowseRecordEntity;
import com.yitongyin.modules.ad.entity.AdGroupProductRateEntity;
import com.yitongyin.modules.ad.entity.AdMerchantSpecGroupEntity;
import com.yitongyin.modules.ad.service.AdClientProductBrowseRecordService;
import com.yitongyin.modules.ad.service.AdGroupProductRateService;
import com.yitongyin.modules.ad.service.AdMerchantSpecGroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("adClientProductBrowseRecordService")
public class AdClientProductBrowseRecordServiceImpl extends ServiceImpl<AdClientProductBrowseRecordDao, AdClientProductBrowseRecordEntity> implements AdClientProductBrowseRecordService {

    @Autowired
    AdClientProductBrowseRecordDao adClientProductBrowseRecordDao;
    @Autowired
    AdMerchantSpecGroupService adMerchantSpecGroupService;
    @Autowired
    private AdGroupProductRateService adGroupProductRateService;

    @Override
    public List<AdClientProductBrowseRecordEntity> getListByClientIdAndMerchantId(Long clientId, Long merchantId) {
        return adClientProductBrowseRecordDao.getListByClientIdAndMerchantId(clientId,merchantId);
    }

    @Override
    public List<AdClientProductBrowseRecordEntity> getListPriceByClientIdAndMerchantId(Long clientId, Long merchantId,String startTime,String endTime) {
        List<AdClientProductBrowseRecordEntity> list =adClientProductBrowseRecordDao.getTimeListByClientIdAndMerchantId(clientId,merchantId,
                startTime,endTime);
        list.forEach( e -> {
            AdMerchantSpecGroupEntity groupEntity =adMerchantSpecGroupService.getLowestPriceByMerAndPro(merchantId,e.getProductId());
            if(groupEntity!=null){
                AdGroupProductRateEntity adGroupProductRateEntity = adGroupProductRateService.getRateByClientIdAndMerchantProcutId(clientId
                        ,e.getMerchantProductId(),merchantId);
                if(adGroupProductRateEntity!=null&&groupEntity.getPrice()!=null){
                    if(adGroupProductRateEntity.getIsShow()==1){
                        e.setPrice(groupEntity.getPrice().multiply(adGroupProductRateEntity.getPriceRate()));
                    }
                }else{
                    e.setPrice(groupEntity.getPrice());
                }
            }
        } );
        return list;
    }

    @Override
    public boolean addOneByTime(AdClientProductBrowseRecordEntity entity) {
        QueryWrapper<AdClientProductBrowseRecordEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("merchant_product_id",entity.getMerchantProductId());
        queryWrapper.eq("client_id",entity.getClientId());
        queryWrapper.eq("merchant_id",entity.getMerchantId());
        AdClientProductBrowseRecordEntity queryEntity=this.getOne(queryWrapper);
        if(queryEntity==null){
            return this.save(entity);
        }else {
            queryEntity.setBrowseDate(entity.getBrowseDate());
            return this.updateById(queryEntity);
        }
    }

    @Override
    public AdClientProductBrowseRecordEntity getMaxTimeByClientId(Long clientId, Long merchantId) {
        QueryWrapper<AdClientProductBrowseRecordEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("client_id",clientId);
        queryWrapper.eq("merchant_id",merchantId);
        queryWrapper.orderByDesc("browse_date");
        return  this.getOne(queryWrapper);
    }

    @Override
    public PageUtils queryPage(Map<String, Object> params, Long clientId, Long merchantId) {
        int page=Integer.valueOf(params.get("page").toString());
        int limit=Integer.valueOf(params.get("limit").toString());
        int start=(Integer.valueOf(params.get("page").toString())-1)*limit;
        List<AdClientProductBrowseRecordEntity> list = adClientProductBrowseRecordDao.getPageListByClientIdAndMerchantId(clientId
        ,merchantId,start,limit);
        Integer count =  adClientProductBrowseRecordDao.getCountByClientIdAndMerchantId(clientId,merchantId);
        return new PageUtils(list,count,limit,page);
    }
}