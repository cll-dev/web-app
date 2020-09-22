package com.yitongyin.modules.ad.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 商户对应厂家的收藏表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-12 15:11:27
 */
@Data
@TableName("ad_merchant_supplier_collection")
public class AdMerchantSupplierCollectionEntity implements Serializable {
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
	 * 收藏状态 0：否，1：是
	 */
	private Integer collectStatus;
	/**
	 * 创建时间
	 */
	private Date collectTime;

}
