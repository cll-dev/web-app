package com.yitongyin.modules.ad.controller;



import com.yitongyin.common.utils.R;
import com.yitongyin.modules.ad.entity.AdMerchantSupplierCollectionEntity;
import com.yitongyin.modules.ad.entity.TMerchantEntity;
import com.yitongyin.modules.ad.service.AdMerchantSupplierCollectionService;
import com.yitongyin.modules.ad.service.TMerchantService;
import io.swagger.annotations.ApiOperation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;


/**
 * 商户对应厂家的收藏表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-12 15:11:27
 */
@RestController
@RequestMapping("ad/collect")
public class AdMerchantSupplierCollectionController extends  AbstractController{
    @Autowired
    private AdMerchantSupplierCollectionService adMerchantSupplierCollectionService;
    @Autowired
    private TMerchantService tMerchantService;

    /**
     * 收藏/取消收藏厂家
     */
    @ApiOperation("收藏/取消收藏厂家")
    @RequestMapping(value = "/save", method = RequestMethod.GET)
    @ResponseBody
    public R list(AdMerchantSupplierCollectionEntity entity){
        TMerchantEntity tMerchantEntity =tMerchantService.getInfoByUserId(getUserId());
        entity.setMerchantId(tMerchantEntity.getMerchantid());
        AdMerchantSupplierCollectionEntity collectionEntity =adMerchantSupplierCollectionService.getOneByMerchantIdAndSupplier
                (entity.getSupplierId(),tMerchantEntity.getMerchantid());
        if(collectionEntity!=null){
            adMerchantSupplierCollectionService.updateStatusByMerIdAndSupId(entity);
        }else{
            entity.setCollectTime(new Date());
            adMerchantSupplierCollectionService.save(entity);
        }
        return R.ok();
    }

}
