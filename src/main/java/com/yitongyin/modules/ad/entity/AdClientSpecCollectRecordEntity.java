package com.yitongyin.modules.ad.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * 客户产品方案收藏记录表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-07 14:44:04
 */
@Data
@TableName("ad_client_spec_collect_record")
public class AdClientSpecCollectRecordEntity implements Serializable {
	private static final long serialVersionUID = 1L;

	/**
	 * 
	 */
	@TableId
	private Long id;
	/**
	 * 客户id
	 */
	private Long clientId;
	/**
	 * 商户产品id
	 */
	private Long merchantProductId;
	/**
	 * 已选中的规格值id的json记录，{specValueName:specValueId}
	 */
	private String productSpecValueJson;
	/**
	 * 创建时间
	 */
	private Date createTime;
	@TableField(exist = false)
	private String proName;
	@TableField(exist = false)
	private String url;
	@TableField(exist = false)
	private BigDecimal price;
	@TableField(exist = false)
	private Integer days;
	@TableField(exist = false)
	private Integer isUse;
	@TableField(exist = false)
	private BigDecimal height;
	@TableField(exist = false)
	private BigDecimal width;
	@TableField(exist = false)
	private BigDecimal designPrice;
	@TableField(exist = false)
	private String designFeeName;
	@TableField(exist = false)
	private List<String> valueNames;
	@TableField(exist = false)
	private Integer feeDay;



}
