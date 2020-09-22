package com.yitongyin.modules.ad.controller;

import com.yitongyin.common.utils.R;
import com.yitongyin.modules.ad.entity.BusIndustryTypeEntity;
import com.yitongyin.modules.ad.form.BusMaterialConditions;
import com.yitongyin.modules.ad.service.BusIndustryTypeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * 行业分类表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-10-13 17:05:06
 */
@RestController
@RequestMapping("bus/busIndustryType")
public class BusIndustryTypeController {
    @Autowired
    private BusIndustryTypeService busIndustryTypeService;

    /**
     * 列表
     */
    @ApiOperation("获取素材列表")
    @GetMapping(value = "/list")
    public R list() {
        List<BusIndustryTypeEntity> list =busIndustryTypeService.getListOrder();
        return R.ok().put("list",list);
    }

}
