package com.yitongyin.modules.ad.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * 素材与行业对应关系表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-10-13 17:05:06
 */
@Data
@TableName("bus_material_industry")
public class BusMaterialIndustryEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 素材ID
	 */
	private Long materialId;
	/**
	 * 行业分类ID
	 */
	private Long bitid;

}
