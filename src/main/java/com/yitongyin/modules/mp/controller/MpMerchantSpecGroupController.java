package com.yitongyin.modules.mp.controller;

import com.yitongyin.common.utils.R;
import com.yitongyin.modules.ad.controller.AbstractController;
import com.yitongyin.modules.ad.entity.AdGroupProductRateEntity;
import com.yitongyin.modules.ad.entity.AdMerchantProductEntity;
import com.yitongyin.modules.ad.entity.AdMerchantSpecGroupEntity;
import com.yitongyin.modules.ad.entity.AdMerchantSpecValueEntity;
import com.yitongyin.modules.ad.form.AdSpecGroup;
import com.yitongyin.modules.ad.form.SpecGroup;
import com.yitongyin.modules.ad.service.AdGroupProductRateService;
import com.yitongyin.modules.ad.service.AdMerchantProductService;
import com.yitongyin.modules.ad.service.AdMerchantSpecGroupService;
import com.yitongyin.modules.ad.service.AdMerchantSpecValueService;
import com.yitongyin.modules.mp.View.KeyAndValueId;
import com.yitongyin.modules.mp.View.MsgIdAndCount;
import com.yitongyin.modules.mp.View.StatusId;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 商户规格组合对应的价格表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-29 15:31:16
 */
@RestController
@RequestMapping("mp/specGroup")
public class MpMerchantSpecGroupController extends AbstractController{
    @Autowired
    private AdMerchantSpecGroupService adMerchantSpecGroupService;
    @Autowired
    private AdMerchantProductService adMerchantProductService;
    @Autowired
    private AdMerchantSpecValueService adMerchantSpecValueService;
    @Autowired
    private AdGroupProductRateService adGroupProductRateService;


    /**
     * 获取规格对应价格和天数
     */
    @ApiOperation("获取规格对应价格和天数")
    @RequestMapping(value = "/info",method = RequestMethod.POST)
    public R info(Long proId,String specKey, @RequestBody  List<Long> ids){
        AdMerchantProductEntity product=adMerchantProductService.getById(proId);
        if(product==null){
            return  R.ok().put("info",null);
        }
       AdMerchantSpecGroupEntity groupEntity = adMerchantSpecGroupService.getMsgIdByValueIds(product.getMerchantId(),
               product.getProductId(),specKey,ids);

      return  R.ok().put("info",groupEntity);
    }
    /**
     * 获取产品规格键值列表
     */
    @ApiOperation("获取产品规格键值列表")
    @RequestMapping(value = "/getList", method = RequestMethod.GET)
    public R getSpecGroups(Long proId){

        AdMerchantProductEntity productEntity = adMerchantProductService.getById(proId);
        if(productEntity==null){
            return R.error("数据错误");
        }
        Long merchantId = productEntity.getMerchantId();
        Long merchantProId = productEntity.getId();
        List<AdMerchantSpecGroupEntity> list =adMerchantSpecGroupService.getMpListByProAndMerchant(
                productEntity.getMerchantId(),productEntity.getProductId());
        if(list==null){
            return R.ok().put("list",null);
        }
        List<SpecGroup> groups=list.stream().map(e->{
            SpecGroup specGroup = new SpecGroup();
            specGroup.setDeliveryDay(e.getDeliveryDay());
            AdGroupProductRateEntity adGroupProductRateEntity = adGroupProductRateService.getDefRateByClientIdAndMerchantProcutId(merchantProId,merchantId);
            if(adGroupProductRateEntity!=null){
                if(adGroupProductRateEntity.getIsShow()==1&&e.getPrice()!=null){
                   specGroup.setPrice(e.getPrice().multiply(adGroupProductRateEntity.getPriceRate()));
                }
                specGroup.setIsShowPrice(adGroupProductRateEntity.getIsShow());
            }else{
                specGroup.setPrice(e.getPrice());
            }
            specGroup.setSpecNote(e.getSpecNote());
            specGroup.setUrl(e.getUrl());
            List<AdMerchantSpecValueEntity> values = adMerchantSpecValueService.getListByMsgId(e.getMsgid());
            List<Long> valueIds= values.stream().map(c->c.getSpecValueId()).collect(Collectors.toList());
            specGroup.setSpecValueIds(valueIds);
            return specGroup;
        }).collect(Collectors.toList());
        return R.ok().put("list",groups);
    }




}
