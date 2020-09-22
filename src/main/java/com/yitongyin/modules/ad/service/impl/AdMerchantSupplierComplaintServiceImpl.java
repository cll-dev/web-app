package com.yitongyin.modules.ad.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yitongyin.modules.ad.dao.AdMerchantSupplierComplaintDao;
import com.yitongyin.modules.ad.entity.AdMerchantSupplierComplaintEntity;
import com.yitongyin.modules.ad.service.AdMerchantSupplierComplaintService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;


@Service("adMerchantSupplierComplaintService")
public class AdMerchantSupplierComplaintServiceImpl extends ServiceImpl<AdMerchantSupplierComplaintDao, AdMerchantSupplierComplaintEntity> implements AdMerchantSupplierComplaintService {
    @Transactional(rollbackFor = Exception.class)
    @Override
    public void subimtByMerIdAndSupId(AdMerchantSupplierComplaintEntity entity) {
        entity.setComplainTime(new Date());
        this.save(entity);
    }


}