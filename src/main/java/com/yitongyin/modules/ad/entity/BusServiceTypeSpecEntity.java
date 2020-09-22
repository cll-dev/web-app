package com.yitongyin.modules.ad.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 产品分类规格字段表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-09-16 15:15:20
 */
@Data
@TableName("bus_service_type_spec")
public class BusServiceTypeSpecEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 类型id
	 */
	private Long serviceTypeId;
	/**
	 * 规格key
	 */
	private String speckey;

}
