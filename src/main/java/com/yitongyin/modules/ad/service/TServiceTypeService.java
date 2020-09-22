package com.yitongyin.modules.ad.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yitongyin.modules.ad.entity.TMerchantEntity;
import com.yitongyin.modules.ad.entity.TServiceTypeEntity;

import java.util.List;

/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-08-13 09:23:04
 */
public interface TServiceTypeService extends IService<TServiceTypeEntity> {
    /**
     * 根据后台分类ID获取所有子分类
     * @param serviceTypeId
     * @return
     */
    List<Object> getChilds(Long serviceTypeId);

    /**
     * 按是否为父分类还是子分类获取所有分类
     * @return
     */
    List<TServiceTypeEntity> getAllListChild();
    /**
     * 按是否为父分类还是子分类获取所有分类及产品
     * @return
     */
    List<TServiceTypeEntity> getAllListAndProChild(TMerchantEntity merchantEntity,Integer state);
}

