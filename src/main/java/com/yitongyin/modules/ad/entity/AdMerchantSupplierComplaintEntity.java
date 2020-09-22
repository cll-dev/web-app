package com.yitongyin.modules.ad.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * 商户对应厂家的投诉表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-12 15:11:27
 */
@Data
@TableName("ad_merchant_supplier_complaint")
public class AdMerchantSupplierComplaintEntity implements Serializable {
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
	 * 厂商ID
	 */
	private Long supplierId;
	/**
	 * 内容
	 */
	private String content;
	/**
	 * 创建时间
	 */
	private Date complainTime;
	/**
	 * 多ossId,逗號分隔
	 */
	private String ossIds;
	@TableField(exist = false)
	private List<String> imgIds;

}
