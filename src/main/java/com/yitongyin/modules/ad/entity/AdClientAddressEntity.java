package com.yitongyin.modules.ad.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 客户收货地址表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-07 14:44:03
 */
@Data
@TableName("ad_client_address")
public class AdClientAddressEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 收货人名称
	 */
	private String name;
	/**
	 * 客户id
	 */
	private Long clientId;
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
	 * 详细地址
	 */
	private String address;
	/**
	 * 门牌号
	 */
	private String houseNumber;
	/**
	 * 联系号码
	 */
	private String phone;
	/**
	 * 是否默认地址 0：否，1：是
	 */
	private Integer isDefault;
	/**
	 * 创建时间
	 */
	private Date createTime;
	/**
	 * 更新时间
	 */
	private Date updateTime;

}
