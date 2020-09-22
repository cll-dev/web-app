package com.yitongyin.modules.ad.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yitongyin.modules.ad.entity.TMerchantEntity;
import com.yitongyin.modules.ad.form.MerchantReport;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-08 16:04:20
 */
@Mapper
public interface TMerchantDao extends BaseMapper<TMerchantEntity> {
    @Select("select m.merchantName,m.merchantId,u.create_time,u.last_login_time,IFNULL(a1.sumViewCount,0) as sumViewCount,IFNULL(a2.sumHitCount,0) as sumHitCount,\n" +
            "IFNULL(b.countMpShow,0) as countMpShow ,IFNULL(c.countSpc,0) as countSpc,IFNULL(d.countSupplier,0) as countSupplier,IFNULL(e.countCase,0) as countCase,\n" +
            "IFNULL(m.designer_contact_number,0) as designer_contact_number,IFNULL(m.material_send_number,0) as material_send_number\n" +
            "from t_merchant m left join ad_user u on m.user_id = u.userId\n" +
            "left join\n" +
            "(select merchant_id,sum(view_count) as sumViewCount from ad_merchant_view_count group by merchant_id) a1 on m.merchantId=a1.merchant_id\n" +
            "left join\n" +
            "(select merchant_id,sum(hit_count) as sumHitCount from ad_merchant_product_hits group by merchant_id) a2 on m.merchantId=a2.merchant_id\n" +
            "left join\n" +
            "-- 产品已打开的个数\n" +
            "(select merchant_id,count(product_id) as countMpShow from ad_merchant_product\n" +
            "where mp_show =1\n" +
            "group by merchant_id) b on b.merchant_id = m.merchantId\n" +
            "left join\n" +
            "-- 产品已配置的个数\n" +
            "(select merchant_id,count(distinct product_id) as countSpc from ad_merchant_spec_group\n" +
            "group by merchant_id) c on c.merchant_id = m.merchantId\n" +
            "left join\n" +
            "-- 收藏厂家个数\n" +
            "(select merchant_id,count(supplier_id) countSupplier from ad_merchant_supplier_collection\n" +
            "where collect_status=1\n" +
            "group by merchant_id) d on d.merchant_id = m.merchantId\n" +
            "left join\n" +
            "-- 案例个数\n" +
            "(select merchant_id,count(id) as countCase from ad_merchant_case\n" +
            "group by merchant_id) e on e.merchant_id = m.merchantId")
    List<MerchantReport> getReport();

	
}
