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
 * @date 2019-07-09 14:09:25
 */
@Data
@TableName("t_area")
public class TAreaEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Integer id;
	/**
	 * 父级ID
	 */
	private Integer parentid;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 简称
	 */
	private String shortname;
	/**
	 * 经度
	 */
	private Float longitude;
	/**
	 * 纬度
	 */
	private Float latitude;
	/**
	 * 等级(1省/直辖市,2地级市,3区县,4镇/街道)
	 */
	private Integer level;
	/**
	 * 排序
	 */
	private Integer sort;
	/**
	 * 状态(0禁用/1启用)
	 */
	private Integer status;

}
