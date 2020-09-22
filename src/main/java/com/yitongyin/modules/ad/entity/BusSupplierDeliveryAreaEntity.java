package com.yitongyin.modules.ad.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 厂商对应的配送范围
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-12 15:23:45
 */
@Data
@TableName("bus_supplier_delivery_area")
public class BusSupplierDeliveryAreaEntity implements Serializable {
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
	 * 省份ID
	 */
	private Integer provinceId;
	/**
	 * 城市ID
	 */
	private Integer cityId;
	/**
	 * 区ID
	 */
	private String districtIds;

}
