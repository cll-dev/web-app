package com.yitongyin.modules.ad.entity;

import com.baomidou.mybatisplus.annotation.TableField;
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
 * @date 2019-07-30 10:40:48
 */
@Data
@TableName("bus_supplier_spec_value")
public class BusSupplierSpecValueEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;

	/**
	 * 规格key
	 */
	private String speckey;
	/**
	 * 规格值Id
	 */
	private Long specValueId;
	/**
	 * 是否是底部纵列
	 */
	private Integer specOnFooter;
	@TableField(exist = false)
	private Integer inCommon;
	@TableField(exist = false)
	private String specKeyName;
	@TableField(exist = false)
	private String specValueName;
	@TableField(exist = false)
	private Integer orderNumber;
	@TableField(exist = false)
	private Integer keyOrderNumber;
	@TableField(exist = false)
	private String valDes;
	/**
	 * 规格组Id
	 */
	private Long ssgid;
	@TableField(exist = false)
	private Long msgid;

}
