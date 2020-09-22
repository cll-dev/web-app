package com.yitongyin.modules.ad.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yitongyin.modules.ad.entity.AdMerchantSpecGroupEntity;
import com.yitongyin.modules.mp.View.ClientCollectResult;
import com.yitongyin.modules.mp.View.MsgIdAndCount;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商户规格组合对应的价格表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-29 15:31:16
 */
@Mapper
public interface AdMerchantSpecGroupDao extends BaseMapper<AdMerchantSpecGroupEntity> {
    @Select("SELECT a.msgId,a.delivery_day,a.supplier_id,a.spec_note, a.price as salePrice,a.valueId,b.price FROM  " +
            "(SELECT ag.msgId,ag.price,ag.delivery_day,ag.spec_order,ag.supplier_id,ag.spec_note,GROUP_CONCAT(av.spec_value_id ORDER BY av.spec_value_id) as valueId FROM ad_merchant_spec_group ag LEFT JOIN ad_merchant_spec_value av on ag.msgId=av.msgId  WHERE ag.merchant_id=#{merchantId} and ag.product_id=#{productId} GROUP BY ag.msgId) a " +
            "LEFT JOIN (SELECT bg.price,GROUP_CONCAT(bv.spec_value_id ORDER BY bv.spec_value_id) as valueId FROM bus_supplier_spec_group bg LEFT JOIN bus_supplier_spec_value bv on bg.ssgId=bv.ssgId  WHERE bg.supplier_id=#{supplierId} and bg.product_id=#{productId} GROUP BY bg.ssgId) b " +
            "on a.valueId=b.valueId")
    List<AdMerchantSpecGroupEntity> getListAndSupPriceAndDaysByProId(Long merchantId,Long productId,Long supplierId);
    @Select("SELECT a.*,b.url FROM  " +
            "(SELECT ag.msgId,ag.price,ag.delivery_day,ag.spec_order,ag.supplier_id,ag.spec_note,GROUP_CONCAT(av.spec_value_id ORDER BY av.spec_value_id) as valueId FROM ad_merchant_spec_group ag LEFT JOIN ad_merchant_spec_value av on ag.msgId=av.msgId  WHERE ag.merchant_id=#{merchantId} and ag.product_id=#{productId} GROUP BY ag.msgId) a " +
            "LEFT JOIN (SELECT bg.price,o.url,GROUP_CONCAT(bv.spec_value_id ORDER BY bv.spec_value_id) as valueId FROM bus_supplier_spec_group bg LEFT JOIN bus_supplier_spec_value bv on bg.ssgId=bv.ssgId left join ad_oss o on bg.spec_image_id=o.id WHERE bg.supplier_id=#{supplierId} and bg.product_id=#{productId} GROUP BY bg.ssgId) b " +
            "on a.valueId=b.valueId")
    List<AdMerchantSpecGroupEntity> getMpListAndSupPriceAndDaysByProId(Long merchantId,Long productId,Long supplierId);
    @Select("SELECT a.delivery_day,a.price,b.url FROM  " +
            "(SELECT ag.msgId,ag.price,ag.delivery_day,ag.spec_order,ag.supplier_id,ag.spec_note,GROUP_CONCAT(av.spec_value_id ORDER BY av.spec_value_id) as valueId FROM ad_merchant_spec_group ag LEFT JOIN ad_merchant_spec_value av on ag.msgId=av.msgId  WHERE ag.merchant_id=#{merchantId} and ag.product_id=#{productId} GROUP BY ag.msgId) a " +
            "LEFT JOIN (SELECT bg.price,o.url,GROUP_CONCAT(bv.spec_value_id ORDER BY bv.spec_value_id) as valueId FROM bus_supplier_spec_group bg LEFT JOIN bus_supplier_spec_value bv on bg.ssgId=bv.ssgId left join ad_oss o on bg.spec_image_id=o.id WHERE bg.supplier_id=#{supplierId} and bg.product_id=#{productId} GROUP BY bg.ssgId) b " +
            "on a.valueId=b.valueId  WHERE a.valueId=#{valueIds}")
    List<ClientCollectResult> getMpClientSpecCollectAndDaysByProId(Long merchantId, Long productId, Long supplierId, String valueIds);
    @Select({
            "<script>",
            "select max(v.msgId) as msgId,count(v.msgId) as count from ad_merchant_spec_value v ",
            "left join ad_merchant_spec_group g on v.msgId=g.msgId",
            "where g.merchant_id=#{merchantId} and g.product_id = #{productId}",
            "and v.spec_value_id in",
            "<foreach collection='ids' item='id' open='(' separator=',' close=')'>",
            "#{id}",
            "</foreach>",
            "group by v.msgId",
            "</script>"
    })
    List<MsgIdAndCount> getCountByValueIdAndProId(Long merchantId, Long productId,List<Long> ids);

}
