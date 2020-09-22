package com.yitongyin.modules.ad.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 厂商对应产品的关系表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-11 16:07:31
 */
@Data
@TableName("bus_supplier_product")
public class BusSupplierProductEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 厂商ID
	 */
	private Long supplierId;
	/**
	 * 总后台商品ID
	 */
	private Long productId;
	/**
	 * 总后台分类ID
	 */
	private Long serviceTypeId;
	/**
	 * json规格key顺序
	 */
	private String specKeyOrder;
	/**
	 * 备注
	 */
	private String productNote;

}
