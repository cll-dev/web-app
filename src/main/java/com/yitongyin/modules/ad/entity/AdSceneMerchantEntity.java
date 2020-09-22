package com.yitongyin.modules.ad.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 场景商家表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-20 15:37:35
 */
@Data
@TableName("ad_scene_merchant")
public class AdSceneMerchantEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long smid;
	/**
	 * 名称
	 */
	private String name;
	/**
	 * 整案编号
	 */
	private String mainNo;
	/**
	 * 整案描述
	 */
	private String mainDesc;
	/**
	 * 场景：行业stId
	 */
	private Long bitid;
	/**
	 * 省份ID
	 */
	private Integer provinceId;
	/**
	 * 城市ID
	 */
	private Integer cityId;
	/**
	 * 区县ID
	 */
	private Integer countyId;
	/**
	 * 地址
	 */
	private String address;
	/**
	 * 门牌号码
	 */
	private String houseNumber;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;
	@TableField(exist = false)
	private String url;
	@TableField(exist = false)
	private Integer projectCount;

}
