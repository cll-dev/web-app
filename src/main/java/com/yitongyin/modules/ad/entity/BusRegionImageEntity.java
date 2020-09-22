package com.yitongyin.modules.ad.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 广告位对应的图片库
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-08-26 14:43:01
 */
@Data
@TableName("bus_region_image")
public class BusRegionImageEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 所在区域
	 */
	private String region;
	/**
	 * 关键词
	 */
	private String keyWord;
	/**
	 * 图片资源id
	 */
	private Long ossId;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	/**
	 * 产品链接
	 */
	private String href;
	/**
	 * 分类Id
	 */
	private Long serviceTypeId;
	@TableField(exist = false)
	private String ossUrl;
	@TableField(exist = false)
	private String servicename;
}
