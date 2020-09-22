package com.yitongyin.modules.mp.controller;

import com.yitongyin.common.utils.R;
import com.yitongyin.modules.ad.controller.AbstractController;
import com.yitongyin.modules.ad.entity.AdMerchantProductEntity;
import com.yitongyin.modules.ad.service.AdMerchantProductDesignFeeService;
import com.yitongyin.modules.ad.service.AdMerchantProductService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 商户产品设计价格表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-09-07 16:53:52
 */
@RestController
@RequestMapping("mp/designfee")
public class MpMerchantProductDesignFeeController extends AbstractController{
    @Autowired
    private AdMerchantProductDesignFeeService adMerchantProductDesignFeeService;
    @Autowired
    private AdMerchantProductService adMerchantProductService;

    /**
     * 列表
     */
    @ApiOperation("设计费列表")
    @GetMapping("/list")
    public R list(Long proId){
        AdMerchantProductEntity product= adMerchantProductService.getById(proId);
        if(product==null) return R.ok().put("list",null);
        return R.ok().put("list",adMerchantProductDesignFeeService.getAdListByProId(product.getMerchantId(),proId));
    }




}
