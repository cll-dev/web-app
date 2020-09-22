package com.yitongyin.modules.ad.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 商户动态表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-01 09:14:38
 */
@Data
@TableName("ad_merchant_dynamic")
public class AdMerchantDynamicEntity implements Serializable {
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
	 * 标题
	 */
	private String title;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 阅读次数
	 */
	private Integer viewCounts;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 修改时间
	 */
	private Date updateTime;
//	@TableField(exist = false)
//	/**
//	 * 商户信息
//	 */
//	private TMerchantEntity tMerchantEntity;

}
