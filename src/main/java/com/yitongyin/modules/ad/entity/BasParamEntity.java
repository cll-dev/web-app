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
 * @date 2019-07-12 17:55:19
 */
@Data
@TableName("bas_param")
public class BasParamEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 参数key
	 */
	@TableId
	private String paramkey;
	/**
	 * 参数名称
	 */
	private String paramname;
	/**
	 * 参数说明
	 */
	private String paramexplain;
	/**
	 * 排序号
	 */
	private Integer ordernumber;
	/**
	 * 创建日期
	 */
	private Date crtdate;
	/**
	 * 更新日期
	 */
	private Date upddate;
	/**
	 * 是否有参数值
	 */
	private Boolean hasvalue;

}
