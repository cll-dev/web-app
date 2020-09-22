package com.yitongyin.modules.ad.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yitongyin.common.utils.PageUtils;
import com.yitongyin.modules.ad.entity.AdMerchantSpecGroupEntity;
import com.yitongyin.modules.ad.entity.BusSupplierSpecGroupEntity;
import com.yitongyin.modules.ad.entity.BusSupplierSpecValueEntity;

import java.util.List;
import java.util.Map;

/**
 * 厂家对应产品的规格价格表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-30 10:40:45
 */
public interface BusSupplierSpecGroupService extends IService<BusSupplierSpecGroupEntity> {
    /**
     * 根据厂家id和后台产品id分页获取规格组
     * @param supplierId
     * @param productId
     * @return
     */
    List<BusSupplierSpecGroupEntity> queryBySupAndProId(Long supplierId,Long productId,List<BusSupplierSpecValueEntity> values,List<String>valueIds);

    /**
     * 根据groupId获取
     * @param groupId
     * @return
     */
    BusSupplierSpecGroupEntity getOneByGroupId(Long groupId);

    List<BusSupplierSpecGroupEntity> getListBySupplierIdsAndProId(List<Long> supplierIds,Long productId);



}

