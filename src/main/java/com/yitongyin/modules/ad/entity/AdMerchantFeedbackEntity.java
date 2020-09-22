package com.yitongyin.modules.ad.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import lombok.Data;

/**
 * 商户反馈表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-09-02 10:22:06
 */
@Data
@TableName("ad_merchant_feedback")
public class AdMerchantFeedbackEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 商户ID
	 */
	private Long merchantId;
	/**
	 * 反馈内容
	 */
	private String content;
	/**
	 * 媒体资源id
	 */
	private String mediaResIds;
	/**
	 * 创建时间
	 */
	private Date createTime;

}
