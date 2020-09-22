package com.yitongyin.modules.mp.controller;

import com.yitongyin.common.utils.PageUtils;
import com.yitongyin.common.utils.R;
import com.yitongyin.modules.ad.entity.AdSceneMerchantEntity;
import com.yitongyin.modules.ad.service.AdSceneMerchantService;
import com.yitongyin.modules.ad.service.AdSceneProjectService;
import com.yitongyin.modules.mp.View.SceneMerchantSearch;
import com.yitongyin.modules.mp.View.SceneMerchantView;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * 场景商家表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-20 15:37:35
 */
@RestController
@RequestMapping("mp/sceneMerchant")
public class MpSceneMerchantController {
    @Autowired
    private AdSceneMerchantService adSceneMerchantService;

    /**
     * 整体方案列表
     */
    @ApiOperation("整体方案列表")
    @PostMapping("/list")
    public R list(@RequestParam Map<String, Object> params, @RequestBody(required = false) SceneMerchantSearch search){

        PageUtils page = adSceneMerchantService.queryPage(params,search);
        return R.ok().put("page", page);
    }
    /**
     * 整体方案详情
     */
    @ApiOperation("整体方案详情")
    @GetMapping("/info")
    public R info(@RequestParam Map<String, Object> params){
        SceneMerchantView view =  adSceneMerchantService.getInfoById(params);
        return R.ok().put("info",view);
    }




}
