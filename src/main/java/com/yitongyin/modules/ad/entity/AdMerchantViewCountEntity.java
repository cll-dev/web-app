package com.yitongyin.modules.ad.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 商户访客数量表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-08 10:05:24
 */
@Data
@TableName("ad_merchant_view_count")
public class AdMerchantViewCountEntity implements Serializable {
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
	 * 访问日期
	 */
	private Date viewDate;
	/**
	 * 访问次数
	 */
	private Integer viewCount;
	@TableField(exist = false)
	private String ip;
	@TableField(exist = false)
	private String time;

}
