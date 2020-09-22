package com.yitongyin.modules.ad.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

import lombok.Data;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-12 17:51:07
 */
@Data
@TableName("bus_supplier_param_value")
public class BusSupplierParamValueEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 
	 */
	private Long supplierId;
	/**
	 * 
	 */
	private String paramKey;
	/**
	 * 
	 */
	private String paramValue;

}
