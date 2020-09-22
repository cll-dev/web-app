package com.yitongyin.modules.ad.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yitongyin.modules.ad.dao.AdMerchantSupplierCollectionDao;
import com.yitongyin.modules.ad.entity.AdMerchantSupplierCollectionEntity;
import com.yitongyin.modules.ad.service.AdMerchantSupplierCollectionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;


@Service("adMerchantSupplierCollectionService")
public class AdMerchantSupplierCollectionServiceImpl extends ServiceImpl<AdMerchantSupplierCollectionDao, AdMerchantSupplierCollectionEntity> implements AdMerchantSupplierCollectionService {
    @Override
    public Integer getCountBySupId(long supplierId) {
        QueryWrapper<AdMerchantSupplierCollectionEntity> query = new QueryWrapper<>();
        query.eq("supplier_id",supplierId);
        query.eq("collect_status",1);
        return this.count(query);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public void updateStatusByMerIdAndSupId(AdMerchantSupplierCollectionEntity entity) {
        entity.setCollectTime(new Date());
        UpdateWrapper<AdMerchantSupplierCollectionEntity> update = new UpdateWrapper<>();
        update.eq("merchant_id", entity.getMerchantId());
        update.eq("supplier_id", entity.getSupplierId());
        this.update(entity,update);
    }

    @Override
    public List<Object> getListByMerId(long merId) {
        QueryWrapper<AdMerchantSupplierCollectionEntity> query = new QueryWrapper<>();
        query.select("supplier_id");
        query.eq("merchant_id",merId);
        query.eq("collect_status",1);
        return this.listObjs(query);
    }

    @Override
    public AdMerchantSupplierCollectionEntity getStatusByMerchantId(Long supplierId, Long merchantId) {
        QueryWrapper<AdMerchantSupplierCollectionEntity> query = new QueryWrapper<>();
        query.select("collect_status");
        query.eq("supplier_id",supplierId);
        query.eq("merchant_id",merchantId);
        return this.getOne(query);
    }

    @Override
    public AdMerchantSupplierCollectionEntity getOneByMerchantIdAndSupplier(Long supplierId, Long merchantId) {
        QueryWrapper<AdMerchantSupplierCollectionEntity> query = new QueryWrapper<>();
        query.eq("supplier_id",supplierId);
        query.eq("merchant_id",merchantId);
        return this.getOne(query);
    }

//    @Override
//    public PageUtils queryPage(Map<String, Object> params) {
//        IPage<AdMerchantSupplierCollectionEntity> page = this.page(
//                new Query<AdMerchantSupplierCollectionEntity>().getPage(params),
//                new QueryWrapper<AdMerchantSupplierCollectionEntity>()
//        );
//
//        return new PageUtils(page);
//    }

}