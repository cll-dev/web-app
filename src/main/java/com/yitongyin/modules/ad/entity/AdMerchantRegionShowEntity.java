package com.yitongyin.modules.ad.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import lombok.Data;

/**
 * 商户广告素材表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-16 10:26:24
 */
@Data
@TableName("ad_merchant_region_show")
public class AdMerchantRegionShowEntity implements Serializable {
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
	 * 广告ID
	 */
	private Long showId;
	/**
	 * 创建日期
	 */
	private Date createDate;
	/**
	 * 更新日期
	 */
	private Date updateDate;
	/**
	 * 位置
	 */
	private String region;
	/**
	 * 配图
	 */
	private Long coverResId;
	/**
	 * 标题
	 */
	private String title;
	/**
	 * 产品链接
	 */
	private String productHref;
	/**
	 * 发布日期
	 */
	private Date publishDate;
	/**
	 * 发布状态
	 */
	private Integer publishAble;
	/**
	 * 排序号
	 */
	private Integer orderNumber;
	/**
	 * 点击量
	 */
	private Integer hits;
    /**
     * 封面图片Url
     */
	@TableField(exist = false)
    private String coverUrl;
    /**
     * 产品id
     */
    @TableField(exist = false)
    private Long proId;
	@TableField(exist = false)
	private List<AdMerchantRegionShowEntity> list;
	@TableField(exist = false)
	private Long typeId;
	@TableField(exist = false)
	private Long dynamicId;


}
