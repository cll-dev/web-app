package com.yitongyin.modules.ad.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yitongyin.common.utils.PageUtils;
import com.yitongyin.common.utils.Query;
import com.yitongyin.modules.ad.dao.AdMerchantSupplierApplyDao;
import com.yitongyin.modules.ad.entity.AdMerchantSupplierApplyEntity;
import com.yitongyin.modules.ad.service.AdMerchantSupplierApplyService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("adMerchantSupplierApplyService")
public class AdMerchantSupplierApplyServiceImpl extends ServiceImpl<AdMerchantSupplierApplyDao, AdMerchantSupplierApplyEntity> implements AdMerchantSupplierApplyService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AdMerchantSupplierApplyEntity> page = this.page(
                new Query<AdMerchantSupplierApplyEntity>().getPage(params),
                new QueryWrapper<AdMerchantSupplierApplyEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public AdMerchantSupplierApplyEntity findByName(String supplierName) {
        QueryWrapper<AdMerchantSupplierApplyEntity> query = new QueryWrapper<>();
        query.eq("supplier_name",supplierName);
        return baseMapper.selectOne(query);
    }

}