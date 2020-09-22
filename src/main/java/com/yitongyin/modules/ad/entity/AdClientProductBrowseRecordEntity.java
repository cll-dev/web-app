package com.yitongyin.modules.ad.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 客户产品浏览记录表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-07 14:44:03
 */
@Data
@TableName("ad_client_product_browse_record")
public class AdClientProductBrowseRecordEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 *
	 */
	@TableId
	private Long id;
	/**
	 * 客户id
	 */
	private Long clientId;
	/**
	 * 商户id
	 */
	private Long merchantId;
	/**
	 * 商户产品id
	 */
	private Long merchantProductId;
	/**
	 * 浏览日期，同一天只能记录一次
	 */
	private Date browseDate;
	@TableField(exist = false)
	private BigDecimal price;
	@TableField(exist = false)
	private Long productId;
	@TableField(exist = false)
	private String time;
	@TableField(exist = false)
	private String productName;
	@TableField(exist = false)
	private String url;

}
