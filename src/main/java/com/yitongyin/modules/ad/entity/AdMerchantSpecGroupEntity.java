package com.yitongyin.modules.ad.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * 商户规格组合对应的价格表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-29 15:31:16
 */
@Data
@TableName("ad_merchant_spec_group")
public class
AdMerchantSpecGroupEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long msgid;
	/**
	 * 厂商ID
	 */
	private Long merchantId;
	/**
	 * 总后台商品ID
	 */
	private Long productId;
	/**
	 * 总后台分类ID
	 */
	private Long serviceTypeId;
	/**
	 * 规格值组合的价格
	 */
	private BigDecimal price;
	/**
	 * 到货天数
	 */
	private Integer deliveryDay;
	/**
	 * 产品规格顺序号
	 */
	private Integer specOrder;
	/**
	 * 厂家规格字段
	 */
	private Long groupId;
	/**
	 * 厂家ID
	 */
	private Long supplierId;
	/**
	 * 规格备注
	 */
	private String specNote;
	@TableField(exist = false)
	private BigDecimal salePrice;
	@TableField(exist = false)
	private List<AdMerchantSpecValueEntity> specValues;
	@TableField(exist = false)
	private BigDecimal supPrice;
	@TableField(exist = false)
	private Integer supDeliveryDay;
	@TableField(exist = false)
	private List<Long> valueIds;
	@TableField(exist = false)
	private List<BusSupplierSpecValueEntity> headSpecValues;
	@TableField(exist = false)
	private List<BusSupplierSpecValueEntity> footSpecValues;
	@TableField(exist = false)
	private Integer orderNumber;
	@TableField(exist = false)
	private Integer isShow;
	@TableField(exist = false)
	private String valueId;
	@TableField(exist = false)
	private String url;
//	@TableField(exist = false)
//	private String specNote;

}
