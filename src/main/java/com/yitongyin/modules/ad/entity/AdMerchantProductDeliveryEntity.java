package com.yitongyin.modules.ad.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.math.BigDecimal;
import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 商户产品运费价格表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-09-07 16:53:52
 */
@Data
@TableName("ad_merchant_product_delivery")
public class AdMerchantProductDeliveryEntity implements Serializable {
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
	 * 运费名称
	 */
	private String deliveryFeeName;
	/**
	 * 运费价格
	 */
	private BigDecimal deliveryFee;
	/**
	 * 运费天数
	 */
	private Integer deliveryDay;
	/**
	 * 是否开启
	 */
	private Integer showAble;

}
