package com.yitongyin.modules.ad.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 行业分类表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-10-13 17:05:06
 */
@Data
@TableName("bus_industry_type")
public class BusIndustryTypeEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long bitid;
	/**
	 * 行业名称
	 */
	private String name;
	/**
	 * 描述
	 */
	private String descr;
	/**
	 * 序号
	 */
	private Integer orderNumber;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;

}
