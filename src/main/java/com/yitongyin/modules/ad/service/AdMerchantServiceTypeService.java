package com.yitongyin.modules.ad.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yitongyin.common.utils.PageUtils;
import com.yitongyin.modules.ad.entity.AdMerchantServiceTypeEntity;
import com.yitongyin.modules.ad.entity.AdMerchantSpecValueEntity;
import com.yitongyin.modules.mp.View.PriceAndDays;
import com.yitongyin.modules.mp.View.ProductType;
import com.yitongyin.modules.mp.View.TypeAndPro;

import java.util.List;
import java.util.Map;


/**
 * 商户分类表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-10 09:58:57
 */
public interface AdMerchantServiceTypeService extends IService<AdMerchantServiceTypeEntity> {
    /**
     * 根据商户ID分页获取对应商户产品分类列表
     * @param params
     * @param userId
     * @return
     */
    PageUtils queryPage(Map<String, Object> params,long userId);

    /**
     * 根据parenttypeID分也获取商家产品子分类列表
     * @param params
     * @param userId
     * @return
     */
    PageUtils queryChildPage(Map<String, Object> params,long userId);

    /**
     * 获取分类下拉列表
     * @param userId
     * @return
     */
    List<AdMerchantServiceTypeEntity> getShowType(long userId);

    /**
     * 获取mpshow=1分类下拉列表
     * @param userId
     * @return
     */
    List<AdMerchantServiceTypeEntity> getMpShowType(long userId);

    /**
     * 修改商户对应分类mp_show状态及下面子类及产品
     * @param adMerchantServiceTypeEntity
     * @param merchantId
     */
    void updateStatusById(AdMerchantServiceTypeEntity adMerchantServiceTypeEntity,Long merchantId);

    /**
     * 获取商户下所有mp_show=1一级分类
     * @param merchantId
     * @return
     */
    List<AdMerchantServiceTypeEntity> getByMerchantId(Long merchantId);

    /**
     * 分页获取首页分类及其属热门产品
     * @param params
     * @param merchantId
     * @return
     */
    PageUtils getProByPopular(Map<String, Object> params,Long merchantId);

    /**
     * 根据一级分类分页获取其下所有产品(除了名片获取的是二级分类)
     * @param params
     * @param merchantId
     * @param serviceTypeId
     * @return
     */
    PageUtils getProByTypeId(Map<String, Object> params,Long merchantId,Long serviceTypeId);

    /**
     * 根据parentid获取对应商户的二级分类mp_show=1
     * @param parentId
     * @param merchantId
     * @return
     */
    List<ProductType> getChildList(Long parentId,Long merchantId);
    /**
     * 根据parentid获取对应商户的二级分类mp_show=1
     * @param serviceTypeId
     * @param merchantId
     * @return
     */
    AdMerchantServiceTypeEntity getByServiceAndMerId(Long merchantId,Long serviceTypeId);
    /**
     * 根据parentid获取对应商户的二级分类mp_show=1
     * @param id
     * @return
     */
    AdMerchantServiceTypeEntity getIsShowById(Long id);


}

