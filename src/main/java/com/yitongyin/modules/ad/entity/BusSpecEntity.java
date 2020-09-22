package com.yitongyin.modules.ad.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import com.yitongyin.modules.ad.form.AdSpecValue;
import lombok.Data;

/**
 * 规格表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-29 14:13:42
 */
@Data
@TableName("bus_spec")
public class BusSpecEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private String speckey;
	/**
	 * 规格名称
	 */
	private String specName;
	/**
	 * 规格描述
	 */
	private String specDes;
	/**
	 * 创建日期
	 */
	private Date createTime;
	/**
	 * 更新日期
	 */
	private Date updateTime;

	@TableField(exist = false)
	private Long id;
	@TableField(exist = false)
	private String valName;
	@TableField(exist = false)
	private List<BusSpecValueEntity> valueList;

}
