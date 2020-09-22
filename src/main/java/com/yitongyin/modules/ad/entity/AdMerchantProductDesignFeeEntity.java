package com.yitongyin.modules.ad.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 商户产品设计价格表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-09-07 16:53:52
 */
@Data
@TableName("ad_merchant_product_design_fee")
public class AdMerchantProductDesignFeeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 商户id
	 */
	private Long merchantId;
	/**
	 * 商户产品id
	 */
	private Long merchantProductId;
	/**
	 * 设计费名称
	 */
	private String designFeeName;
	/**
	 * 设计费价格
	 */
	private BigDecimal designFee;
	/**
	 * 是否开启
	 */
	private Integer showAble;
	/**
	 * 是否自动融进参考价格里
	 */
	private Integer autoInclude;
	/**
	 * 备注
	 */
	private String feeNote;
	/**
	 * 天数
	 */
	private Integer feeDay;

 }
