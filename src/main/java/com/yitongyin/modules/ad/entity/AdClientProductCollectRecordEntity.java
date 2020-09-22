package com.yitongyin.modules.ad.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 客户产品收藏记录表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-07 14:44:03
 */
@Data
@TableName("ad_client_product_collect_record")
public class AdClientProductCollectRecordEntity implements Serializable {
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
	 * 商户产品id
	 */
	private Long merchantProductId;
	/**
	 * 收藏时间
	 */
	private Date collectTime;
	@TableField(exist = false)
    private String productname;
	@TableField(exist = false)
	private String url;
	@TableField(exist = false)
	private Long productId;
	@TableField(exist = false)
	private BigDecimal lowPrice;
	@TableField(exist = false)
	private Integer day;
	@TableField(exist = false)
	private String startTime;
	@TableField(exist = false)
	private String endTime;

}
