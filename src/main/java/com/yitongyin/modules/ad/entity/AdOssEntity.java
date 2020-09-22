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
 * @date 2019-07-08 17:16:15
 */
@Data
@TableName("ad_oss")
public class AdOssEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId
	private Long id;
	/**
	 * url
	 */
	private String url;
	/**
	 * 日期
	 */
	private Date createDate;
	/**
	 * 状态，1 正常 2  可删除
	 */
	private  Integer status;

}
