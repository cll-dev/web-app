package com.yitongyin.modules.mp.controller;


import com.yitongyin.common.utils.R;
import com.yitongyin.modules.ad.controller.AbstractController;
import com.yitongyin.modules.ad.entity.TMerchantEntity;
import com.yitongyin.modules.ad.service.AdOssService;
import com.yitongyin.modules.ad.service.TMerchantService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-08 16:04:20
 */
@RestController
@RequestMapping("mp/merchant")
public class MpMerchantController extends  AbstractController{
    @Autowired
    private TMerchantService tMerchantService;
    @Autowired
    private AdOssService adOssService;

    /**
     * 获取商户信息
     */
    @ApiOperation("获取商户信息")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public R getInfo(Long id) {
        if(id == null|| id == 0) {
            return  R.error("无效的参数");
        }
        TMerchantEntity entity =tMerchantService.getInfoById(id);
        if(entity==null)
            return R.ok().put("info",new TMerchantEntity());
        if(entity.getLogoossid()!=null)
        entity.setLogoUrl(adOssService.findById(entity.getLogoossid()));
        if(entity.getSaleossid()!=null)
        entity.setSaleUrl(adOssService.findById(entity.getSaleossid()));
        if(entity.getDesignossid()!=null)
        entity.setDesignUrl(adOssService.findById(entity.getDesignossid()));
        if(entity.getShopphotoid()!=null){
        entity.setShopphotoUrl(adOssService.findById(entity.getShopphotoid()));
        }
        return R.ok().put("info",entity);
    }





}
