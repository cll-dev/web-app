package com.yitongyin.modules.ad.controller;


import com.alibaba.fastjson.JSONArray;
import com.yitongyin.common.utils.PageUtils;
import com.yitongyin.common.utils.R;
import com.yitongyin.modules.ad.entity.AdMerchantProductEntity;
import com.yitongyin.modules.ad.entity.TMerchantEntity;
import com.yitongyin.modules.ad.form.ProListSelect;
import com.yitongyin.modules.ad.form.ProductStatus;
import com.yitongyin.modules.ad.form.ProductTag;
import com.yitongyin.modules.ad.service.*;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;


/**
 * 商户产品表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-11 11:30:20
 */
@RestController
@RequestMapping("ad/product")
public class AdMerchantProductController extends  AbstractController{
    @Autowired
    private AdMerchantProductService adMerchantProductService;
    @Autowired
    private TMerchantService tMerchantService;


    /**
     * 获取产品列表
     */
    @ApiOperation("获取产品列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public R list(@RequestParam Map<String, Object> params,Long typeId,String name,String type){
        TMerchantEntity merchantEntity=tMerchantService.getInfoByUserId(getUserId());
        if(StringUtils.isBlank(type))
            return R.error("type参数不能为空");
        if(type.equals("all")){
            PageUtils page = adMerchantProductService.queryPage(params,typeId,merchantEntity,name,null);
            return R.ok().put("page", page);
        }
        if(type.equals("isShow")){
            PageUtils page = adMerchantProductService.queryPage(params,typeId,merchantEntity,name,1);
            return R.ok().put("page", page);
        }
        return R.error("type参数错误");
    }


    /**
     * 发布/下架产品
     */
    @ApiOperation("发布/下架产品")
    @RequestMapping(value = "/show", method = RequestMethod.POST)
    public R show(@RequestBody ProductStatus entity){
        TMerchantEntity tMerchantEntity = tMerchantService.getInfoByUserId(getUserId());
        if(entity.getIds()==null||entity.getIds().size()==0)
         return R.error("参数ID错误");
        adMerchantProductService.updateStatuByIds(entity.getIds(),entity.getStatus(),tMerchantEntity.getMerchantid());
        return R.ok();
    }
    /**
     * 根据厂家产品修改对应商家的产品的状态
     */
    @ApiOperation("根据厂家产品修改对应商家的产品的状态")
    @RequestMapping(value = "/showBySup", method = RequestMethod.POST)
    public R showBySup(@RequestBody ProductStatus entity){
        TMerchantEntity tMerchantEntity = tMerchantService.getInfoByUserId(getUserId());
        if(entity.getBusIds()==null||entity.getBusIds().size()==0)
            return R.ok();
        adMerchantProductService.updMpShowByBusPro(entity.getBusIds(),entity.getStatus(),tMerchantEntity.getMerchantid());
        return R.ok();
    }

    /**
     * 产品详情管理
     */
    @ApiOperation("产品详情管理")
    @RequestMapping(value = "/update/productContent", method = RequestMethod.POST)
    @ResponseBody
    public R updateProductContent(AdMerchantProductEntity entity){
        adMerchantProductService.updateDescrip(entity);
        return  R.ok();
    }
    /**
     * 获取所有产品列表
     */
    @ApiOperation("获取所有产品列表")
    @RequestMapping(value = "/listAll", method = RequestMethod.GET)
    public R listAll(){
        TMerchantEntity merchantEntity=tMerchantService.getInfoByUserId(getUserId());
        List<AdMerchantProductEntity> list=adMerchantProductService.listAll(merchantEntity.getMerchantid());
        List<ProListSelect> selects =list.stream().map(entity -> {
             ProListSelect select = new ProListSelect();
             select.setId(entity.getId());
             select.setValue(entity.getProductId());
             select.setText(entity.getProductName());
             return  select;
        }).collect(Collectors.toList());
        return  R.ok().put("list",selects);
    }
    /**
     * 根据产品Id获取产品详情
     */
    @ApiOperation("根据产品Id获取产品详情")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public R getInfoById(Long id){
        TMerchantEntity tMerchantEntity = tMerchantService.getInfoByUserId(getUserId());
        AdMerchantProductEntity productEntity = adMerchantProductService.infoById(id,tMerchantEntity.getMerchantid());
        return R.ok().put("info",productEntity);
    }
    /**
     * 热门产品
     */
    @ApiOperation("根据时间获取前10条热门产品")
    @RequestMapping(value = "/hotList", method = RequestMethod.GET)
    public R getHotByTime(Integer type){
        TMerchantEntity tMerchantEntity = tMerchantService.getInfoByUserId(getUserId());
        List<AdMerchantProductEntity> list = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal=Calendar.getInstance();
        Date d = new Date();
        String startTime="";
        String endTime=sdf.format(d);
        if(type==null){
            return R.error("参数未传");
        }
        if(type.equals(1)){
            cal.add(Calendar.DATE,-1);
            Date time=cal.getTime();
            startTime =sdf.format(time);
            list=adMerchantProductService.getHotsByTime(tMerchantEntity.getMerchantid(),startTime,endTime);
        }else if(type.equals(2)){
            cal.add(Calendar.DATE,-6);
            Date time=cal.getTime();
            startTime =sdf.format(time);
            list=adMerchantProductService.getHotsByTime(tMerchantEntity.getMerchantid(),startTime,endTime);
        }else{
             list=adMerchantProductService.getHotsByTime(tMerchantEntity.getMerchantid(),null,null);
        }
        return R.ok().put("list",list);
    }
    /**
     * 根据产品Id保存标签
     */
    @ApiOperation("根据产品Id保存标签")
    @RequestMapping(value = "/tags/save", method = RequestMethod.POST)
    public R saveTags(@RequestBody List<ProductTag> tags, Long id){
        adMerchantProductService.saveTagsById(id,tags);
        return R.ok();
    }
    /**
     * 根据产品Id显示标签
     */
    @ApiOperation("根据产品Id显示标签")
    @RequestMapping(value = "/tags/list", method = RequestMethod.GET)
    public R saveTags(Long id){
        AdMerchantProductEntity entity = adMerchantProductService.getById(id);
        List<ProductTag> tagList= new ArrayList<>();
        String tags=entity.getTagJson();
        if(StringUtils.isNotBlank(tags)){
            tagList = JSONArray.parseArray(tags,ProductTag.class);
        }
        return R.ok().put("list",tagList);
    }


}
