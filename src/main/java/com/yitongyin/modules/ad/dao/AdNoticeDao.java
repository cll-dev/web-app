package com.yitongyin.modules.ad.dao;

import com.yitongyin.modules.ad.entity.AdNoticeEntity;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

/**
 * 公告表
 * 
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-08 12:59:42
 */
@Mapper
public interface AdNoticeDao extends BaseMapper<AdNoticeEntity> {

    List<AdNoticeEntity> queryList ();
    List<AdNoticeEntity> queryListByType (Integer type,Integer count);
	
}
