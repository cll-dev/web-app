package com.yitongyin.modules.mp.controller;


import com.alibaba.fastjson.JSONArray;
import com.yitongyin.common.utils.PageUtils;
import com.yitongyin.common.utils.R;
import com.yitongyin.modules.ad.controller.AbstractController;
import com.yitongyin.modules.ad.entity.AdMerchantProductEntity;
import com.yitongyin.modules.ad.entity.AdMerchantProductHitsEntity;
import com.yitongyin.modules.ad.entity.AdMerchantServiceTypeEntity;
import com.yitongyin.modules.ad.form.ProductTag;
import com.yitongyin.modules.ad.service.AdMerchantProductHitsService;
import com.yitongyin.modules.ad.service.AdMerchantProductService;
import com.yitongyin.modules.ad.service.AdMerchantServiceTypeService;
import com.yitongyin.modules.mp.View.PriceAndDays;
import com.yitongyin.modules.mp.View.ProInfoAndCase;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 商户产品表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-11 11:30:20
 */
@RestController
@RequestMapping("mp/product")
public class MpMerchantProductController extends  AbstractController{
    @Autowired
    private AdMerchantProductService adMerchantProductService;
    @Autowired
    private AdMerchantServiceTypeService adMerchantServiceTypeService;
    @Autowired
    private AdMerchantProductHitsService adMerchantProductHitsService;



    /**
     * 获取产品列表
     */
    @ApiOperation("获取产品列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public R list(@RequestParam Map<String, Object> params, Long typeId){
        AdMerchantServiceTypeEntity typeEntity =adMerchantServiceTypeService.getById(typeId);
        if(typeEntity==null)
            return R.ok().put("page",null);
        PageUtils page=adMerchantProductService.getMpListByConditions(params,typeEntity.getServiceTypeId(),typeEntity.getMerchantId(),null);
        return R.ok().put("page", page);
    }

    /**
     * 搜索获取产品列表
     */
    @ApiOperation("搜索获取产品列表")
    @RequestMapping(value = "/listSearch", method = RequestMethod.POST)
    public R list(@RequestParam Map<String, Object> params,@RequestBody PriceAndDays priceAndDays){
        if(priceAndDays.getTypeId()==null){
            PageUtils page=adMerchantProductService.getMpListByConditions(params,null,priceAndDays.getMerchantId(),priceAndDays);
            return R.ok().put("page", page);
        }else{
            AdMerchantServiceTypeEntity typeEntity =adMerchantServiceTypeService.getById(priceAndDays.getTypeId());
            if(typeEntity==null){
                return R.ok().put("page",null);
            } else{
                PageUtils page=adMerchantProductService.getMpListByConditions(params,typeEntity.getServiceTypeId(),priceAndDays.getMerchantId(),priceAndDays);
                return R.ok().put("page", page);
            }
        }
    }
    /**
     * 产品详情
     */
    @ApiOperation("产品详情")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    @ResponseBody
    public R info(Long id)  {
        AdMerchantProductEntity productEntity =adMerchantProductService.getById(id);
        if(productEntity==null) return R.ok().put("info", null);
        AdMerchantServiceTypeEntity typeEntity = adMerchantServiceTypeService.getByServiceAndMerId(productEntity.getMerchantId(),productEntity.getServiceTypeId());
        List<ProductTag> tagList= new ArrayList<>();
        String tags=productEntity.getTagJson();
        if(StringUtils.isNotBlank(tags)){
            tagList = JSONArray.parseArray(tags,ProductTag.class);
        }
        ProInfoAndCase info= adMerchantProductService.getInfoById(id);
        info.setMerchantId(productEntity.getMerchantId());
        info.setTypeId(typeEntity==null?null:typeEntity.getTypeid());
        info.setTypeName(typeEntity==null?null:typeEntity.getTypeName());
        info.setTags(tagList);
        AdMerchantProductHitsEntity entity = new AdMerchantProductHitsEntity();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String startTime=sdf.format(new Date());
        entity.setHitDate(new Date());
        entity.setDate(startTime);
        entity.setMerchantId(productEntity.getMerchantId());
        entity.setMerchantProductId(id);
        if(adMerchantProductHitsService.saveOrUpdByToday(entity)){
            return R.ok().put("info", info);
        }else {
            return R.error();
        }
    }




}
