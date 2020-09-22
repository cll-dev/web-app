package com.yitongyin.modules.ad.controller;

import com.yitongyin.common.utils.R;
import com.yitongyin.modules.ad.entity.AdGroupProductRateEntity;
import com.yitongyin.modules.ad.service.AdGroupProductRateService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 客户组产品价格倍数表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-08 17:40:57
 */
@RestController
@RequestMapping("ad/grouproductrate")
public class AdGroupProductRateController {
    @Autowired
    private AdGroupProductRateService adGroupProductRateService;

    /**
     * 编辑
     */
    @PostMapping("/update")
    @ApiOperation("编辑产品价格倍数")
    public R update(@RequestBody List<AdGroupProductRateEntity> list){
        adGroupProductRateService.updRateByProId(list);
        return R.ok();
    }
}
