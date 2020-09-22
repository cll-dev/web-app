package com.yitongyin.modules.ad.service;


import com.baomidou.mybatisplus.extension.service.IService;
import com.yitongyin.common.utils.PageUtils;
import com.yitongyin.modules.ad.entity.AdMerchantProductEntity;
import com.yitongyin.modules.ad.entity.TMerchantEntity;
import com.yitongyin.modules.ad.form.Product;
import com.yitongyin.modules.ad.form.ProductTag;
import com.yitongyin.modules.mp.View.PriceAndDays;
import com.yitongyin.modules.mp.View.ProInfoAndCase;
import com.yitongyin.modules.mp.View.ProductInfo;
import com.yitongyin.modules.mp.View.TypeAndPro;

import java.util.List;
import java.util.Map;

/**
 * 商户产品表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-11 11:30:20
 */
public interface AdMerchantProductService extends IService<AdMerchantProductEntity> {
    /**
     * 根据产品名称产品分类产品mp_show状态分页获取对应商户的产品列表
     * @param params
     * @param typeId
     * @param merchantEntity
     * @param name
     * @param status
     * @return
     */
    PageUtils queryPage(Map<String, Object> params, Long typeId, TMerchantEntity merchantEntity, String name, Integer status);

    /**
     * 根据商户ID获取所有产品列表,数据格式同上
     * @param merchantId
     * @return
     */
    List<Product> getByMerchantId(Long merchantId,List<Long> proIds,Integer start,Integer limit);

    /**
     * 根据后台service_type_id修改对应商户下产品的mp_show状态
     * @param entity
     */
    void updateStatusById(AdMerchantProductEntity entity);

    /**
     * 根据后台多个service_type_id修改对应商户下产品的mp_show状态
     * @param ids
     * @param merchantId
     * @param show
     */
    void updateStatusByIds(List<Object> ids,Long merchantId,Integer show);
    /**
     * 获取选择某产品的厂家数量
     * @param id
     * @return
     */
    Integer getSelectCount(long id);
    /**
     * 根据商户产品多个ID修改对应产品mp_show状态为status
     * @param ids
     * @param status
     * @param merchantId
     */
    void updateStatuByIds(List<Integer> ids,Integer status,Long merchantId);

    /**
     * 根据厂家多个后台产品ID修改对应商户的产品mo_show状态为status
     * @param busIds
     * @param status
     * @param merchantId
     * @return
     */
    boolean updMpShowByBusPro(List<Long> busIds,Integer status,Long merchantId);

    /**
     * 根据商户产品ID修改对应产品content
     * @param entity
     */
    void updateDescrip(AdMerchantProductEntity entity);

    /**
     * 根据商户ID和后台产品ID获取pubalb=1及mpshow=1的产品信息
     * @param merchantId
     * @param proId
     * @return
     */
    AdMerchantProductEntity mpGetOneByMerIdAndProId(long merchantId,long proId);

    /**
     * 根据后台service_type_id获取对应商户下的产品
     * @param typeId
     * @param merchantId
     * @return
     */
    List<AdMerchantProductEntity> getListByTypeId(long typeId,long merchantId);

    /**
     * 根据多个子分类或者单个后台分类获取对应商户的产品列表
     * @param typeIds
     * @param merchantId
     * @param serviceTypeId
     * @return
     */
    List<TypeAndPro.proInfo> getPopularListByMerchantIdAndType(List<Object> typeIds,long merchantId,Long serviceTypeId);

    /**
     * 根据多个子分类或者单个后台分类分页获取对应商户的产品列表
     * @param params
     * @param typeIds
     * @param merchantId
     * @param serviceTypeId
     * @return
     */
    PageUtils getPageListByMerchantIdAndType(Map<String, Object> params,List<Object> typeIds,long merchantId,Long serviceTypeId);

    /**
     * 根据搜索条件分页获取对应商户的产品列表
     * @param params
     * @param serviceTypeId
     * @param merchantId
     * @param priceAndDays
     * @return
     */
    PageUtils getMpListByConditions(Map<String, Object> params,Long serviceTypeId, long merchantId, PriceAndDays priceAndDays);

    /**
     * 根据商户产品ID获取产品详情
     * @param id
     * @return
     */
    ProInfoAndCase getInfoById(long id);

    /**
     * 根据商户ID获取所有pubalbe=1产品列表
     * @param merchantId
     * @return
     */
    List<AdMerchantProductEntity> listAll(Long merchantId);

    /**
     * 根据商户产品ID获取产品详情
     * @param id
     * @param merchantId
     * @return
     */
    AdMerchantProductEntity infoById(Long id,Long merchantId);

    /**
     * 根据实际获取商户对应的热门产品
     * @param merchantId
     * @param startTime
     * @param endTime
     * @return
     */
    List<AdMerchantProductEntity> getHotsByTime(Long merchantId,String startTime,String endTime);
    /**
     * 根据实际获取商户对应的热门产品
     * @param merchantId
     * @param supProId
     * @param productId
     * @return
     */
    boolean insertSupProId(Long merchantId,Long productId,Long supProId);

    boolean saveTagsById(Long proId,List<ProductTag> tags);

    /**
     * 获取所有产品
     * @param merchantId
     * @return
     */
    List<AdMerchantProductEntity> getAllShowList(Long merchantId);
    /**
     * 根据搜索条件分页获取对应商户的产品列表
     * @param params
     * @param serviceTypeId
     * @param merchantId
     * @param priceAndDays
     * @return
     */
    PageUtils getRateListByConditions(Map<String, Object> params,Long serviceTypeId, long merchantId, PriceAndDays priceAndDays,Long clientId);

    /**
     * 根据商户ID和产品ID获取对象
     * @param merchantId
     * @param productId
     * @return
     */
    AdMerchantProductEntity getOneByMerchantIdAndProductId(Long merchantId,Long productId);
    /**
     * 根据产品名称产品分类产品mp_show状态分页获取对应商户的产品列表
     * @param params
     * @param merchantEntity
     * @return
     */
    PageUtils queryRatePage(Map<String, Object> params, TMerchantEntity merchantEntity,Long groupId,String name,Long typeId);
    /**
     * 获取所有产品
     * @param merchantId
     * @return
     */
    List<AdMerchantProductEntity> mpGetAllShowListLimit(Long merchantId);
}

