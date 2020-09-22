package com.yitongyin.modules.ad.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yitongyin.common.utils.PageUtils;
import com.yitongyin.modules.ad.entity.BusMaterialEntity;
import com.yitongyin.modules.ad.form.BusMaterialConditions;
import com.yitongyin.modules.ad.form.MaterialIM;

import java.util.List;
import java.util.Map;

/**
 * 素材表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-17 09:48:32
 */
public interface BusMaterialService extends IService<BusMaterialEntity> {
    /**
     * 分页获取素材
     * @param params
     * @param conditions
     * @return
     */
    PageUtils queryPage1(Map<String, Object> params, BusMaterialConditions conditions);
    /**
     * 分页获取素材
     * @param params
     * @param conditions
     * @return
     */
    PageUtils queryPage(Map<String, Object> params, BusMaterialConditions conditions);

    /**
     * 导入素材Excel
     * @param list
     */
    void importMaterialSync(List<MaterialIM> list);

    /**
     * 导入素材Excel行业Id
     * @param list
     */
    void importMaterialIndustrySync(List<MaterialIM> list);

    /**
     * 根据标题查询
     * @param title
     * @return
     */
    List<BusMaterialEntity> findByTitle(String title);

    /**
     * 导入所有的素材数据到ES
     * @return
     */
    int importAll();

    /**
     * 删除ES所有的素材数据
     */
    void deleteAll();
}

