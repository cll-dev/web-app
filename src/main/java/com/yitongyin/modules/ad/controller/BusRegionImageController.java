package com.yitongyin.modules.ad.controller;

import com.yitongyin.common.utils.PageUtils;
import com.yitongyin.common.utils.R;
import com.yitongyin.modules.ad.entity.AdOssEntity;
import com.yitongyin.modules.ad.entity.BusRegionImageEntity;
import com.yitongyin.modules.ad.service.AdOssService;
import com.yitongyin.modules.ad.service.BusRegionImageService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;



/**
 * 广告位对应的图片库
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-08-26 14:43:01
 */
@RestController
@RequestMapping("Bus/busregionimage")
public class BusRegionImageController {
    @Autowired
    private BusRegionImageService busRegionImageService;

    /**
     * 列表
     */
    @ApiOperation("根据条件获取广告图片库")
    @PostMapping("/listByRegion")
    public R list(@RequestParam Map<String, Object> params){
        PageUtils page = busRegionImageService.queryPage(params);
        return R.ok().put("page", page);
    }



}
