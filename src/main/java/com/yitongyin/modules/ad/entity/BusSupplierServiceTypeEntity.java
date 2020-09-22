package com.yitongyin.modules.ad.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-10 11:24:58
 */
@Data
@TableName("bus_supplier_service_type")
public class BusSupplierServiceTypeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long stid;
	/**
	 * 供应商id
	 */
	private Long supplierid;
	/**
	 * 产品目录id
	 */
	private Long servicetypeid;

}
