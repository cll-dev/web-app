package com.yitongyin.modules.ad.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yitongyin.common.utils.PageUtils;
import com.yitongyin.modules.ad.dao.AdClientSpecCollectRecordDao;
import com.yitongyin.modules.ad.entity.AdClientSpecCollectRecordEntity;
import com.yitongyin.modules.ad.service.AdClientSpecCollectRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("adClientSpecCollectRecordService")
public class AdClientSpecCollectRecordServiceImpl extends ServiceImpl<AdClientSpecCollectRecordDao, AdClientSpecCollectRecordEntity> implements AdClientSpecCollectRecordService {

    @Autowired
    AdClientSpecCollectRecordDao adClientSpecCollectRecordDao;
    @Override
    public PageUtils queryPage(Map<String, Object> params, Long clientId, Long merchantId) {
        int page=Integer.valueOf(params.get("page").toString());
        int limit=Integer.valueOf(params.get("limit").toString());
        int start=(Integer.valueOf(params.get("page").toString())-1)*limit;
        List<AdClientSpecCollectRecordEntity> list =adClientSpecCollectRecordDao.getListByClientId(clientId,merchantId,
                start,limit);
        Integer count=adClientSpecCollectRecordDao.getCountByClientId(clientId,merchantId);
        return new PageUtils(list,count,limit,page);
    }

    @Override
    public AdClientSpecCollectRecordEntity getByEntity(AdClientSpecCollectRecordEntity entity) {
        QueryWrapper<AdClientSpecCollectRecordEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("client_id",entity.getClientId());
        queryWrapper.eq("merchant_product_id",entity.getMerchantProductId());
        queryWrapper.eq("product_spec_value_json",entity.getProductSpecValueJson());
        return this.getOne(queryWrapper);
    }

}