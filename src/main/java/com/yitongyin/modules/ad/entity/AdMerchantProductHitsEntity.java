package com.yitongyin.modules.ad.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 商户产品点击量
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-09-05 17:44:15
 */
@Data
@TableName("ad_merchant_product_hits")
public class AdMerchantProductHitsEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 商户ID
	 */
	private Long merchantId;
	/**
	 * 商户产品ID
	 */
	private Long merchantProductId;
	/**
	 * 点击日期
	 */
	private Date hitDate;
	/**
	 * 点击量
	 */
	private Integer hitCount;
	@TableField(exist = false)
	private String date;
}
