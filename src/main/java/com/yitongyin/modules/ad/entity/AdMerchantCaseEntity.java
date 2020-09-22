package com.yitongyin.modules.ad.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 商户案例表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-16 11:34:13
 */
@Data
@TableName("ad_merchant_case")
public class AdMerchantCaseEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 所属商户id
	 */
	private Long merchantId;
	/**
	 * 所属商分类id
	 */
	private Long serviceTypeId;
	/**
	 * 所属商户产品id
	 */
	private Long ProductId;
	/**
	 * 评论内容
	 */
	private String content;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	/**
	 * 图片
	 */
	private String attachmentIds;
	/**
	 * 点击数量
	 */
	private Integer hits;
	/**
	 * 同步下來的後台案例ID
	 */
	private Long commentid;
	@TableField(exist = false)
	private String productName;

}
