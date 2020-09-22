package com.yitongyin.modules.ad.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yitongyin.modules.ad.entity.AdMerchantSpecValueEntity;
import com.yitongyin.modules.ad.entity.BusSupplierSpecValueEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-29 15:31:16
 */
@Mapper
public interface AdMerchantSpecValueDao extends BaseMapper<AdMerchantSpecValueEntity> {

    @Select("select v.specKey,v.spec_value_id,v.spec_on_footer,v.in_common,s1.spec_name as specKeyName,s2.val_des,s2.order_number,s2.val_name as specValueName\n" +
            "from ad_merchant_spec_value v\n" +
            "left join bus_spec_value s2 on v.spec_value_id = s2.id\n" +
            "left join bus_spec s1 on v.specKey = s1.specKey where v.msgId = #{msgId} AND v.spec_on_footer = #{isFoot}")
    List<BusSupplierSpecValueEntity> findByMsgId(Long msgId, Integer isFoot);
    @Select({"<script>" +
            "select v.specKey,v.spec_value_id,v.spec_on_footer,v.in_common,s1.spec_name as specKeyName,s2.val_des,s2.order_number,s2.val_name as specValueName\n" +
            "from ad_merchant_spec_value v\n" +
            "left join bus_spec_value s2 on v.spec_value_id = s2.id\n" +
            "left join bus_spec s1 on v.specKey = s1.specKey where v.msgId in" +
            "<foreach collection='msgIds' item='msgId' open='(' separator=',' close=')'>",
            "#{msgId}",
            "</foreach>",
            "</script>"})
    List<AdMerchantSpecValueEntity> findByMsgIds(@Param("msgIds")List<Long> msgIds);
    @Select({"<script>" +
            "select v.specKey,v.spec_value_id,v.spec_on_footer,s1.spec_name as specKeyName,s2.val_des,s2.order_number,s2.val_name as specValueName\n" +
            "from ad_merchant_spec_value v\n" +
            "left join bus_spec_value s2 on v.spec_value_id = s2.id\n" +
            "left join bus_spec s1 on v.specKey = s1.specKey where v.msgId in" +
            "<foreach collection='msgIds' item='msgId' open='(' separator=',' close=')'>",
            "#{msgId}",
            "</foreach>",
            "GROUP BY v.spec_value_id  order by s2.order_number ASC",
            "</script>"})
    List<BusSupplierSpecValueEntity> getGroupListByMsgIds(@Param("msgIds")List<Long> msgIds);
}
