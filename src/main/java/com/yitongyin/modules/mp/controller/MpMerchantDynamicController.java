package com.yitongyin.modules.mp.controller;

import com.yitongyin.common.utils.PageUtils;
import com.yitongyin.common.utils.R;
import com.yitongyin.common.utils.RegxUtil;
import com.yitongyin.modules.ad.controller.AbstractController;
import com.yitongyin.modules.ad.entity.AdMerchantDynamicEntity;
import com.yitongyin.modules.ad.entity.AdOssEntity;
import com.yitongyin.modules.ad.entity.TMerchantEntity;
import com.yitongyin.modules.ad.service.AdMerchantDynamicService;
import com.yitongyin.modules.ad.service.AdOssService;
import com.yitongyin.modules.ad.service.TMerchantService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * 商户动态表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-01 09:14:38
 */
@RestController
@RequestMapping("mp/dynamic")
public class MpMerchantDynamicController extends  AbstractController{
    @Autowired
    private AdMerchantDynamicService adMerchantDynamicService;
    @Autowired
    private TMerchantService tMerchantService;
    @Autowired
    private AdOssService adOssService;

    /**
     * 列表
     */
    @ApiOperation("列表")
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params,Long merchantId){
        PageUtils page = adMerchantDynamicService.queryPage(params,merchantId);
        return R.ok().put("page", page);
    }

    /**
     * 信息
     */
    @ApiOperation("信息")
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		AdMerchantDynamicEntity adMerchantDynamic = adMerchantDynamicService.getById(id);
        return R.ok().put("info", adMerchantDynamic);
    }



}
