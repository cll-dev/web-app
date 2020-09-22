package com.yitongyin.nosql.elasticsearch.document;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import java.io.Serializable;
import java.util.Date;

@Data
@Document(indexName = "yitongyin_product", type = "ad_merchant_product", shards = 1, replicas = 0)
public class EsProduct implements Serializable {

    @Id
    private Long id;

    /**
     * 总后台类型id
     */
    private Long serviceTypeId;
    /**
     * 总后台产品id
     */
    private Long productId;
    /**
     * 商户id
     */
    private Long merchantId;
    /**
     * 发布状态 0：否，1：是
     */
    private Integer publishAble;
    /**
     * 用户前台是否显示
     */
    private Integer mpShow;

    /**
     * 产品点击量
     */
    private Integer hits;

    /**
     * 创建时间
     */
    private Date createTime;
    /**
     * 更新时间
     */
    private Date updateTime;
    /**
     * 产品名称
     */
    private String productName;
    /**
     * 产品封面资源
     */
    private Long ossId;
    /**
     * 产品序号
     */
    private Integer orderNumber;
    /**
     * 关键词
     */
    private String tags;
    /**
     * 是否定义厂家规格配置
     */
    private Integer supplierSpecConf;
//    /**
//     * 规格值组合的价格
//     */
//    private BigDecimal price;
//    /**
//     * 到货天数
//     */
//    private Integer deliveryDay;
    /**
     * 关键词
     */
    private String ossUrl;
}
