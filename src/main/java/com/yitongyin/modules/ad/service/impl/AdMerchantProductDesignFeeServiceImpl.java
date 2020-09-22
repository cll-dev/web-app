package com.yitongyin.modules.ad.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONException;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonParseException;
import com.yitongyin.common.exception.RRException;
import com.yitongyin.common.exception.RRExceptionHandler;
import com.yitongyin.common.utils.Query;
import com.yitongyin.common.utils.R;
import com.yitongyin.modules.ad.dao.AdMerchantProductDesignFeeDao;
import com.yitongyin.modules.ad.entity.AdMerchantProductDesignFeeEntity;
import com.yitongyin.modules.ad.entity.AdMerchantProductEntity;
import com.yitongyin.modules.ad.entity.BusProductEntity;
import com.yitongyin.modules.ad.service.AdMerchantProductDesignFeeService;
import com.yitongyin.modules.ad.service.AdMerchantProductService;
import com.yitongyin.modules.ad.service.BusProductService;
import com.yitongyin.modules.mp.View.DesignFee;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Service("adMerchantProductDesignFeeService")
public class AdMerchantProductDesignFeeServiceImpl extends ServiceImpl<AdMerchantProductDesignFeeDao, AdMerchantProductDesignFeeEntity> implements AdMerchantProductDesignFeeService {
    @Autowired
    private AdMerchantProductService adMerchantProductService;
    @Autowired
    private BusProductService busProductService;
    @Override
    public List<AdMerchantProductDesignFeeEntity> getListByProId(Long merchantId, Long proId) {
        QueryWrapper<AdMerchantProductDesignFeeEntity> query= new QueryWrapper<>();
        query.eq("merchant_id",merchantId).eq("merchant_product_id",proId);
        List<AdMerchantProductDesignFeeEntity> list =this.list(query);
        if(list!=null&&list.size()!=0){
            return list;
        }else{
            AdMerchantProductEntity productEntity = adMerchantProductService.getById(proId);
            if(productEntity.getProductId()!=null){
                BusProductEntity busProductEntity = busProductService.getSomePropertyById(productEntity.getProductId());
                String jsons=busProductEntity.getDesignfee();
                List<DesignFee> designFees = new ArrayList<>();
                if(StringUtils.isNotBlank(jsons)){
                   try {
                       designFees= JSONArray.parseArray(jsons, DesignFee.class);
                   }catch (JSONException e){
                       throw  new RRException("数据异常");
                   }
                    list=designFees.stream().map(e->{
                        AdMerchantProductDesignFeeEntity designFeeEntity = new AdMerchantProductDesignFeeEntity();
                        designFeeEntity.setDesignFee(e.getFeePrice());
                        designFeeEntity.setAutoInclude(e.getAutoInclude());
                        designFeeEntity.setFeeNote(e.getFeeNote());
                        designFeeEntity.setDesignFeeName(e.getFeeName());
                        designFeeEntity.setFeeDay(e.getFeeDay());
                        return designFeeEntity;
                    }).collect(Collectors.toList());
                }
            }
            return list;
        }
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean updStatus(Long merchantId, Long merProId,Integer status) {
        UpdateWrapper<AdMerchantProductDesignFeeEntity> query= new UpdateWrapper<>();
        query.eq("merchant_id",merchantId).eq("merchant_product_id",merProId);
        query.set("show_able",status);
        return this.update(query);
    }

    @Override
    public AdMerchantProductDesignFeeEntity getOneByProId(Long merchantId, Long proId) {
        QueryWrapper<AdMerchantProductDesignFeeEntity> query= new QueryWrapper<>();
        query.eq("merchant_id",merchantId).eq("merchant_product_id",proId);
        return this.getOne(query);
    }

    @Override
    public List<AdMerchantProductDesignFeeEntity> getAdListByProId(Long merchantId, Long proId) {
        QueryWrapper<AdMerchantProductDesignFeeEntity> query= new QueryWrapper<>();
        query.eq("merchant_id",merchantId).eq("merchant_product_id",proId);
        return this.list(query);
    }

    @Override
    public List<DesignFee> getServiceConfigByProId(Long proId) {
        AdMerchantProductEntity product =adMerchantProductService.getById(proId);
        if(product==null){
            throw  new RRException("数据异常");
        }
        BusProductEntity busProductEntity =busProductService.getSomePropertyById(product.getProductId());
        if(busProductEntity==null){
            return null;
        }
        String jsons=busProductEntity.getDesignfee();
        List<DesignFee> designFees = new ArrayList<>();
        if(StringUtils.isNotBlank(jsons)){
            try {
                designFees= JSONArray.parseArray(jsons, DesignFee.class);
            }catch (JSONException e){
                throw  new RRException("数据异常",e);
            }
        }
        return designFees;
    }
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeAll(Long merchantId, Long proId) {
        QueryWrapper<AdMerchantProductDesignFeeEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("merchant_id",merchantId).eq("merchant_product_id",proId);
       return this.remove(queryWrapper);
    }


}