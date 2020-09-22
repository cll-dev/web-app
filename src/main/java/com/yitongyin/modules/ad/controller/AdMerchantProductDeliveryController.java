package com.yitongyin.modules.ad.controller;

import com.yitongyin.common.utils.R;
import com.yitongyin.modules.ad.entity.AdMerchantProductDeliveryEntity;
import com.yitongyin.modules.ad.entity.AdMerchantProductDesignFeeEntity;
import com.yitongyin.modules.ad.entity.TMerchantEntity;
import com.yitongyin.modules.ad.service.AdMerchantProductDeliveryService;
import com.yitongyin.modules.ad.service.TMerchantService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * 商户产品运费价格表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-09-07 16:53:52
 */
@RestController
@RequestMapping("ad/deliveryfee")
public class AdMerchantProductDeliveryController extends AbstractController{
    @Autowired
    private AdMerchantProductDeliveryService adMerchantProductDeliveryService;
    @Autowired
    private TMerchantService tMerchantService;

    /**
     * 运费列表
     */
    @ApiOperation("运费列表")
    @GetMapping("/list")
    public R list(Long proId){
          TMerchantEntity tMerchantEntity = tMerchantService.getInfoByUserId(getUserId());
          return R.ok().put("list", adMerchantProductDeliveryService.getByProId(tMerchantEntity.getMerchantid(),proId));
    }


    /**
     * 信息
     */
    @ApiOperation("运费详情")
    @GetMapping("/info")
    public R info( Long id){
		AdMerchantProductDeliveryEntity adMerchantProductDeliveryFee = adMerchantProductDeliveryService.getById(id);
        return R.ok().put("info", adMerchantProductDeliveryFee);
    }

    /**
     * 保存
     */
    @ApiOperation("运费保存")
    @PostMapping("/save")
    public R save(@RequestBody List<AdMerchantProductDeliveryEntity> list){
        TMerchantEntity tMerchantEntity =tMerchantService.getInfoByUserId(getUserId());
        list.forEach( e -> {
           e.setMerchantId(tMerchantEntity.getMerchantid());
           if(e.getId()==null){
               adMerchantProductDeliveryService.save(e);
           }else{
               adMerchantProductDeliveryService.updateById(e);
           }
        } );
        return R.ok();
    }


    /**
     * 删除
     */
    @ApiOperation("删除运费")
    @GetMapping("/delete")
    public R delete(Long id){
		adMerchantProductDeliveryService.removeById(id);
        return R.ok();
    }
    /**
     * 开启/关闭
     */
    @ApiOperation("开启/关闭")
    @GetMapping("/updSta")
    public R delete(Long id,Integer status){
        TMerchantEntity tMerchantEntity = tMerchantService.getInfoByUserId(getUserId());
        adMerchantProductDeliveryService.updStatus(tMerchantEntity.getMerchantid(),id,status);
        return R.ok();
    }
    /**
     * 获取状态
     */
    @ApiOperation("获取状态")
    @GetMapping("/getSta")
    public R getSta(Long id){
        TMerchantEntity tMerchantEntity = tMerchantService.getInfoByUserId(getUserId());
        AdMerchantProductDeliveryEntity entity = adMerchantProductDeliveryService.getOneByProId(tMerchantEntity.getMerchantid(),id);
        if(entity==null){
           return R.ok().put("status",0);
        }
        return R.ok().put("status",entity.getShowAble());
    }

}
