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
 * @date 2019-07-16 13:31:11
 */
@Data
@TableName("bus_region_show")
public class BusRegionShowEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long showid;
	/**
	 * 创建日期
	 */
	private Date createdate;
	/**
	 * 更新日期
	 */
	private Date updatedate;
	/**
	 * 发布时间
	 */
	private Date publishdate;
	/**
	 * 发布状态
	 */
	private Integer publishable;
	/**
	 * 发布者
	 */
	private Long publishuserid;
	/**
	 * 下架时间
	 */
	private Date descenddate;
	/**
	 * 下架者
	 */
	private Long descenduserid;
	/**
	 * 封面
	 */
	private Long converlogresid;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 介绍
	 */
	private String descr;
	/**
	 * 链接地址
	 */
	private String href;
	/**
	 * 排序号
	 */
	private Integer ordernumber;
	/**
	 * 所在区域
	 */
	private String region;
	/**
	 * 显示端
	 */
	private Integer showarea;
	/**
	 * 所在组
	 */
	private String group;
	/**
	 * 复制来源id
	 */
	private Long copyfromid;

}
