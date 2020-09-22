package com.yitongyin.modules.ad.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yitongyin.modules.ad.entity.AdMerchantServiceTypeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 商户分类表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-10 09:58:57
 */
@Mapper
public interface AdMerchantServiceTypeDao extends BaseMapper<AdMerchantServiceTypeEntity> {
    @Select("SELECT a.*,b.serviceName as typeName,b.orderNumber,b.childNumber FROM ad_merchant_service_type a LEFT JOIN t_service_type b on a.service_type_id=b.serviceTypeId WHERE a.merchant_id=#{merchantId} AND a.ad_show=1 ORDER BY b.orderNumber ASC")
    List<AdMerchantServiceTypeEntity> getListIsShowByMerchantId(Long merchantId);
    @Select("SELECT a.*,b.serviceName as typeName,b.orderNumber,b.childNumber,b.iconPath,b.descr FROM ad_merchant_service_type a LEFT JOIN t_service_type b on a.service_type_id=b.serviceTypeId WHERE a.merchant_id=#{merchantId} AND a.ad_show=1 and b.parentServiceTypeId=1 ORDER BY b.orderNumber ASC LIMIT #{start},#{limit}")
    List<AdMerchantServiceTypeEntity> getPageIsParent(Long merchantId,Integer start,Integer limit);
    @Select("SELECT COUNT(*) FROM ad_merchant_service_type a LEFT JOIN t_service_type b on a.service_type_id=b.serviceTypeId WHERE a.merchant_id=#{merchantId} AND a.ad_show=1 and b.parentServiceTypeId=1 ORDER BY b.orderNumber ASC")
    Integer getPageIsParentCount(Long merchantId);
    @Select("SELECT a.*,b.serviceName as typeName,b.orderNumber,b.childNumber,b.iconPath,b.descr FROM ad_merchant_service_type a LEFT JOIN t_service_type b on a.service_type_id=b.serviceTypeId WHERE a.merchant_id=#{merchantId} AND a.ad_show=1 and a.parent_type_id=#{parentTypeId} ORDER BY b.orderNumber ASC LIMIT #{start},#{limit}")
    List<AdMerchantServiceTypeEntity> getPageIsChild(Long merchantId,Long parentTypeId, Integer start,Integer limit);
    @Select("SELECT COUNT(*) FROM ad_merchant_service_type a LEFT JOIN t_service_type b on a.service_type_id=b.serviceTypeId WHERE a.merchant_id=#{merchantId} AND a.ad_show=1 and a.parent_type_id=#{parentTypeId} ORDER BY b.orderNumber ASC")
    Integer getPageIsChildCount(Long merchantId,Long parentTypeId);


    /*<!--————————————————————————MP------------------------------------------>*/
    //根据商户ID获取商户端所有分类:商户分类ID,后台分类名字，后台分类ICON
    @Select("SELECT a.typeId,a.service_type_id,b.serviceName as typeName,b.iconPath,b.childNumber FROM ad_merchant_service_type a LEFT JOIN t_service_type b on a.service_type_id=b.serviceTypeId WHERE a.merchant_id=#{merchantId} AND  a.ad_show=1 and a.mp_show=1 and b.parentServiceTypeId=1 ORDER BY b.orderNumber ASC ")
    List<AdMerchantServiceTypeEntity> getParentListByMerchantId(Long merchantId);
    //根据商户ID获取商户端所有分类:商户分类ID,后台分类名字，后台分类ICON(分页)
    @Select("SELECT a.typeId,a.service_type_id,b.serviceName as typeName,b.iconPath,b.childNumber FROM ad_merchant_service_type a LEFT JOIN t_service_type b on a.service_type_id=b.serviceTypeId WHERE a.merchant_id=#{merchantId} AND  a.ad_show=1 and a.mp_show=1 and b.parentServiceTypeId=1 ORDER BY b.orderNumber ASC limit #{start},#{limit}")
    List<AdMerchantServiceTypeEntity> getParentListByMerchantIdPage(Long merchantId,Integer start,Integer limit);
    //根据商户ID获取商户端所有分类:商户分类ID,后台分类名字，后台分类ICON(总数)
    @Select("SELECT count(*) FROM ad_merchant_service_type a LEFT JOIN t_service_type b on a.service_type_id=b.serviceTypeId WHERE a.merchant_id=#{merchantId} AND a.ad_show=1 and a.mp_show=1 and b.parentServiceTypeId=1")
    Integer getCountParentListByMerchantId(Long merchantId);
    //根据商户ID获取商户端所有分类:商户分类ID,后台分类名字，后台分类ICON
    @Select("SELECT a.*,b.serviceName as typeName,b.iconPath FROM ad_merchant_service_type a LEFT JOIN t_service_type b on a.service_type_id=b.serviceTypeId WHERE a.merchant_id=#{merchantId} AND  a.ad_show=1 and a.mp_show=1 and b.childNumber=1 ORDER BY b.orderNumber ASC ")
    List<AdMerchantServiceTypeEntity> getTypeNoChild(Long merchantId);
    //根据商户ID获取商户端所有分类:商户分类ID,后台分类名字，后台分类ICON
    @Select("SELECT a.typeId,a.service_type_id,b.serviceName as typeName,b.iconPath,b.descr,b.coverResId FROM ad_merchant_service_type a LEFT JOIN t_service_type b on a.service_type_id=b.serviceTypeId WHERE a.merchant_id=#{merchantId} AND a.ad_show=1 and a.mp_show=1 AND a.parent_type_id=#{parentTypeId} ORDER BY b.orderNumber ASC ")
    List<AdMerchantServiceTypeEntity> getIsChild(Long merchantId,Long parentTypeId);
    @Select("SELECT a.*,b.serviceName as typeName,b.orderNumber,b.childNumber FROM ad_merchant_service_type a LEFT JOIN t_service_type b on a.service_type_id=b.serviceTypeId WHERE a.merchant_id=#{merchantId} AND a.ad_show=1 and a.mp_show=1 ORDER BY b.orderNumber ASC")
    List<AdMerchantServiceTypeEntity> getListMpShowByMerchantId(Long merchantId);
    //根据商户ID获取商户端所有分类:商户分类ID,后台分类名字，后台分类ICON
    @Select("SELECT a.*,b.serviceName as typeName,b.orderNumber,b.coverResId FROM ad_merchant_service_type a LEFT JOIN t_service_type b on a.service_type_id=b.serviceTypeId WHERE a.merchant_id=#{merchantId} AND a.ad_show=1 and a.mp_show=1 AND a.parent_type_id=#{parentTypeId} ORDER BY b.orderNumber ASC limit #{start},#{limit}")
    List<AdMerchantServiceTypeEntity> getChildPageByTypeId(Long merchantId,Long parentTypeId,Integer start,Integer limit);
    //根据商户ID获取商户端所有分类:商户分类ID,后台分类名字，后台分类ICON
    @Select("SELECT COUNT(*) FROM ad_merchant_service_type a LEFT JOIN t_service_type b on a.service_type_id=b.serviceTypeId WHERE a.merchant_id=#{merchantId} AND a.ad_show=1 and a.mp_show=1 AND a.parent_type_id=#{parentTypeId}")
    Integer getCountChildPageByTypeId(Long merchantId,Long parentTypeId);

}
