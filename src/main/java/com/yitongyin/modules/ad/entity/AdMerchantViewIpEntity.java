package com.yitongyin.modules.ad.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 商户访客的ip表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-09-06 17:46:12
 */
@Data
@TableName("ad_merchant_view_ip")
public class AdMerchantViewIpEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 商户访问表Id
	 */
	private Long viewId;
	/**
	 * 访客的ip地址
	 */
	private String viewIpAddress;
	/**
	 * 来访时间
	 */
	private Date viewTime;

}
