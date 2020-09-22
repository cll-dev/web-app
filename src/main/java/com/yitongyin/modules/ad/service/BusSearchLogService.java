package com.yitongyin.modules.ad.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yitongyin.common.utils.PageUtils;
import com.yitongyin.modules.ad.entity.BusSearchLogEntity;

import java.util.List;
import java.util.Map;

/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-08-15 16:43:57
 */
public interface BusSearchLogService extends IService<BusSearchLogEntity> {
    /**
     * 分页获取搜索历史表
     * @param params
     * @return
     */
    PageUtils queryPage(Map<String, Object> params);

    /**
     * 修改或增加搜索词数据
     * @param entity
     * @param isMatch
     * @return
     */
    Boolean saveOrUpd(BusSearchLogEntity entity,Boolean isMatch);

    /**
     * 根据title获取搜索数据
     * @param title
     * @return
     */
    BusSearchLogEntity findByTitle(String title);

    /**
     * 获取推荐搜索列表
     * @return
     */
    List<BusSearchLogEntity> getListByIsSearchRecommend();

}

