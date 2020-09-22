package com.yitongyin.modules.ad.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yitongyin.modules.ad.entity.BusProductEntity;
import com.yitongyin.modules.ad.entity.TMerchantEntity;

import java.util.List;

/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-10 11:14:58
 */
public interface BusProductService extends IService<BusProductEntity> {
    /**
     * 获取商户分类下的产品数量
     * @param type
     * @param merchantId
     * @return
     */
    Integer getCountByType(long type,Long merchantId);

    /**
     * 获取商户子分类下的产品数量
     * @param types
     * @param merchantId
     * @return
     */
    Integer getCountByChildTypes(List<Object> types,Long merchantId);

    /**
     * 根据后台产品ID获取后台产品部分信息
     * @param id
     * @return
     */
    BusProductEntity getSomePropertyById(Long id);
    /**
     * 根据后台产品ID获取后台产品部分信息
     * @param  serviceTypeId
     * @return
     */
    List<BusProductEntity> getNameByTypeId(Long serviceTypeId, TMerchantEntity merchantEntity,Integer state);
}

