package com.yitongyin.modules.ad.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * 客户组产品价格倍数表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-08 17:40:57
 */
@Data
@TableName("ad_group_product_rate")
public class AdGroupProductRateEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 客户组id
	 */
	private Integer groupId;
	/**
	 * 商户产品id
	 */
	private Long merchantProductId;
	/**
	 * 价格倍数
	 */
	private BigDecimal priceRate;
	@TableField(exist = false)
	private Integer isShow;

}
