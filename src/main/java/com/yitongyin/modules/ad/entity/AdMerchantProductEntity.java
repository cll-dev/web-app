package com.yitongyin.modules.ad.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * 商户产品表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-11 11:30:20
 */
@Data
@TableName("ad_merchant_product")
public class AdMerchantProductEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
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
	 * 厂家产品ID
	 */
	private Long supplierProductId;
	/**
	 * 商户产品标签json字符串
	 */
	private String tagJson;
	/**
	 * 产品名称
	 */
	@TableField(exist = false)
	private String productName;
	@TableField(exist = false)
	private Long ossId;
	@TableField(exist = false)
	private String ossUrl;
	@TableField(exist = false)
	private String productDes;
	@TableField(exist = false)
	private String productContent;
	@TableField(exist = false)
	private Integer orderNo;
	@TableField(exist = false)
	private List<AdMerchantProductEntity> brothers;
	@TableField(exist = false)
	private Integer hitCount;
	@TableField(exist = false)
	private Integer supplierSpecConf;
	@TableField(exist = false)
	private BigDecimal lowPrice;
	@TableField(exist = false)
	private Integer day;
	@TableField(exist = false)
	private String outlink;


}
