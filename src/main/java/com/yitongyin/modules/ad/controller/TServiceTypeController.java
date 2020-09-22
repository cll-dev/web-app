package com.yitongyin.modules.ad.controller;

import com.yitongyin.common.utils.R;
import com.yitongyin.modules.ad.entity.TMerchantEntity;
import com.yitongyin.modules.ad.entity.TServiceTypeEntity;
import com.yitongyin.modules.ad.service.TMerchantService;
import com.yitongyin.modules.ad.service.TServiceTypeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("bus/proCat")
public class TServiceTypeController extends  AbstractController{
    @Autowired
    private TServiceTypeService tServiceTypeService;
    @Autowired
    private TMerchantService tMerchantService;

    @ApiOperation("获取厂家产品分类及分类下的产品")
    @RequestMapping(value = "/list/supplier", method = RequestMethod.GET)
    @ResponseBody
    public R getSupType() {
        TMerchantEntity tMerchantEntity = tMerchantService.getInfoByUserId(getUserId());
        if(tMerchantEntity.getCounty()==null){
            return R.error("该商户未填写所在区");
        }
        List<TServiceTypeEntity> tServiceTypeEntityList = tServiceTypeService.getAllListAndProChild(tMerchantEntity,1);
        return R.ok().put("list",tServiceTypeEntityList);
    }
    @ApiOperation("获取案例产品分类及分类下的产品")
    @RequestMapping(value = "/list/case", method = RequestMethod.GET)
    @ResponseBody
    public R getCaseType() {
        TMerchantEntity tMerchantEntity = tMerchantService.getInfoByUserId(getUserId());
        List<TServiceTypeEntity> tServiceTypeEntityList = tServiceTypeService.getAllListAndProChild(tMerchantEntity,2);
        return R.ok().put("list",tServiceTypeEntityList);
    }
    @ApiOperation("获取後台产品分类名称")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public R getTypeName() {
        List<TServiceTypeEntity> tServiceTypeEntityList = tServiceTypeService.getAllListChild();
        return R.ok().put("list",tServiceTypeEntityList);
    }
}
