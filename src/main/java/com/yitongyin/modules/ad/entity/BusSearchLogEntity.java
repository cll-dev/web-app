package com.yitongyin.modules.ad.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
 * @date 2019-08-15 16:43:57
 */
@Data
@TableName("bus_search_log")
public class BusSearchLogEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId(value = "logId",type = IdType.INPUT)
	private String logid;
	/**
	 * 搜索标题
	 */
	private String sarchtitle;
	/**
	 * 搜索次数
	 */
	private Long searchtimes;
	/**
	 * 匹配中的次数
	 */
	private Long matchedtimes;
	/**
	 * 第一次搜索日期
	 */
	private Date firstsearchdate;
	/**
	 * 最近搜索日期
	 */
	private Date lastsarchdate;
	/**
	 * 最后一次搜索时ip
	 */
	private String lastsearchip;
	/**
	 * 搜索推荐
	 */
	private Integer searchrecommend;

}
