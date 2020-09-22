package com.yitongyin.modules.mp.controller;

import com.alibaba.fastjson.JSONArray;
import com.yitongyin.common.utils.PageUtils;
import com.yitongyin.common.utils.R;
import com.yitongyin.modules.ad.entity.AdMerchantProductEntity;
import com.yitongyin.modules.ad.entity.AdMerchantSpecGroupEntity;
import com.yitongyin.modules.ad.entity.AdMerchantSpecValueEntity;
import com.yitongyin.modules.ad.entity.BusSupplierProductEntity;
import com.yitongyin.modules.ad.form.SpecKeyOrder;
import com.yitongyin.modules.ad.service.AdMerchantProductService;
import com.yitongyin.modules.ad.service.AdMerchantSpecGroupService;
import com.yitongyin.modules.ad.service.AdMerchantSpecValueService;
import com.yitongyin.modules.ad.service.BusSupplierProductService;
import com.yitongyin.modules.mp.View.SpecGroupVM;
import com.yitongyin.modules.mp.View.SpecKeys;
import com.yitongyin.modules.mp.View.SpecValues;
import com.yitongyin.modules.mp.View.SpecValuesAndSpecKeys;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;


/**
 * 
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-29 15:31:16
 */
@RestController
@RequestMapping("mp/spec")
public class MpMerchantSpecValueController {
    @Autowired
    private AdMerchantSpecValueService adMerchantSpecValueService;
    @Autowired
    private AdMerchantSpecGroupService adMerchantSpecGroupService;
    @Autowired
    private AdMerchantProductService adMerchantProductService;
    @Autowired
    private BusSupplierProductService busSupplierProductService;

    /**
     * 获取产品规格键值列表
     */
    @ApiOperation("获取产品规格键值列表")
    @RequestMapping(value = "/getList", method = RequestMethod.GET)
    public R getSpecKeyAndSpecValue(Long proId){
        AdMerchantProductEntity productEntity = adMerchantProductService.getById(proId);
        if(productEntity==null){
            return R.error("数据错误");
        }
        List<AdMerchantSpecGroupEntity> groupEntities =adMerchantSpecGroupService.getListByProAndMerchant(
                productEntity.getMerchantId(),productEntity.getProductId());
        List<Long> msgIds= groupEntities.stream().map(e->e.getMsgid()).collect(Collectors.toList());
        if(msgIds==null||msgIds.size()==0)
            return R.ok().put("data",null);
       // Map<SpecKeys, List<SpecValues>> data= adMerchantSpecValueService.ListByMsgId(msgIds);
        List<SpecGroupVM> specGroupVMList = adMerchantSpecValueService.getSpecGroupByMsgId(msgIds);
        BusSupplierProductEntity busSupplierProductEntity=busSupplierProductService.getOrderByProIdAndMerchantId(productEntity.getProductId(),
                productEntity.getMerchantId());
        String jsonOrder=busSupplierProductEntity==null?null:busSupplierProductEntity.getSpecKeyOrder();
        List<SpecKeyOrder> orders = new ArrayList<>();
        if(StringUtils.isNotBlank(jsonOrder)){
            orders= JSONArray.parseArray(jsonOrder,SpecKeyOrder.class);
        }
        for (SpecGroupVM valueEntity: specGroupVMList) {
            for (SpecKeyOrder order: orders) {
                if(valueEntity.getKey().equals(order.getSpecKey())){
                    valueEntity.setKeyOrder(order.getOrderNumber());
                    break;
                }
            }
        }
        List<SpecGroupVM> sortSpecGroupVMList = specGroupVMList.stream().sorted(Comparator.comparing(SpecGroupVM::getKeyOrder,Comparator.nullsFirst(Integer::compareTo))).collect(Collectors.toList());
        return R.ok().put("data", sortSpecGroupVMList);
    }


}
