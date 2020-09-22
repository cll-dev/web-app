package com.yitongyin.modules.ad.controller;


import com.yitongyin.common.utils.*;
import com.yitongyin.modules.ad.entity.AdMerchantSupplierCollectionEntity;
import com.yitongyin.modules.ad.entity.AdOssEntity;
import com.yitongyin.modules.ad.entity.BusSupplierEntity;
import com.yitongyin.modules.ad.entity.TMerchantEntity;
import com.yitongyin.modules.ad.form.NewSuppllierInfo;
import com.yitongyin.modules.ad.form.SupplierConditions;
import com.yitongyin.modules.ad.form.SupplierInfo;
import com.yitongyin.modules.ad.service.*;
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
 * @date 2019-07-12 09:17:10
 */
@RestController
@RequestMapping("ad/supplier")
public class BusSupplierController extends  AbstractController{
    @Autowired
    private BusSupplierService busSupplierService;
    @Autowired
    private TMerchantService tMerchantService;
    @Autowired
    private AdMerchantSupplierCollectionService adMerchantSupplierCollectionService;

    @Autowired
    private BusSupplierProductService busSupplierProductService;

    @Autowired
    private AdOssService adOssService;



    /**
     * 获取关联厂家列表
     */
    @ApiOperation("根据分类获取关联厂家列表")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public R list(long typeId){
        List<BusSupplierEntity> list=busSupplierProductService.getIdAndNamesByType(typeId);
        return R.ok().put("list", list);
    }


    /**
     * 根据ID获取厂家信息
     */
    @ApiOperation("根据ID获取厂家信息")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public R info(Long id) {
        TMerchantEntity tMerchantEntity =tMerchantService.getInfoByUserId(getUserId());
        NewSuppllierInfo info =busSupplierService.getInfoById(id,tMerchantEntity.getMerchantid());
        return R.ok().put("info",info);
    }
    /**
     * 根据条件分页获取厂家信息
     */
    @ApiOperation("根据条件分页获取厂家信息")
    @RequestMapping(value = "/listByConditions", method = RequestMethod.POST)
    public R listByConditionss(@RequestParam Map<String, Object> params, SupplierConditions conditions){
        TMerchantEntity entity = tMerchantService.getInfoByUserId(getUserId());
        if(entity.getCounty()==null){
            return R.error("该商户未填写所在区");
        }
        List<BusSupplierEntity> supplierEntityList=new ArrayList<>();
        if(conditions.getIsMyCollect()!=null){
            List<Object> supIds=adMerchantSupplierCollectionService.getListByMerId(entity.getMerchantid());
            List<BusSupplierEntity> collectList= new ArrayList<>();
            for (Object obj: supIds) {
                BusSupplierEntity busSupplierEntity = new BusSupplierEntity();
                busSupplierEntity.setSupplierid(Long.valueOf(obj.toString()));
                collectList.add(busSupplierEntity);
            }
            supplierEntityList=collectList;
        }else{
            supplierEntityList =busSupplierService.queryByMerchantCounty(entity.getCounty());
        }
        if(supplierEntityList==null||supplierEntityList.size()==0){
            Integer limit =Integer.valueOf(params.get("limit").toString());
            Integer page1 =Integer.valueOf(params.get("page").toString());
            PageUtils pageUtils =new PageUtils(supplierEntityList,0,limit,page1);
            return R.ok().put("page",pageUtils);
        }
        if(conditions.getProductId()!=null){
            List<Object> objects= busSupplierProductService.getListByProId(conditions.getProductId());
            List<BusSupplierEntity> list =objects.stream().map(e->{
                BusSupplierEntity entity1 = new BusSupplierEntity();
                entity1.setSupplierid(Long.valueOf(e.toString()));
                return entity1;
            }).collect(Collectors.toList());
            if(list.isEmpty()){
                Integer limit =Integer.valueOf(params.get("limit").toString());
                Integer page1 =Integer.valueOf(params.get("page").toString());
                PageUtils pageUtils =new PageUtils(new ArrayList<>(),0,limit,page1);
                return R.ok().put("page",pageUtils);
            }else{
                supplierEntityList= supplierEntityList.stream()
                        .filter(t->list.stream()
                                .anyMatch(t2->t2.getSupplierid().equals(t.getSupplierid())))
                        .collect(Collectors.toList());
            }
        }
        if(supplierEntityList==null||supplierEntityList.size()==0){
            Integer limit =Integer.valueOf(params.get("limit").toString());
            Integer page1 =Integer.valueOf(params.get("page").toString());
            PageUtils pageUtils =new PageUtils(supplierEntityList,0,limit,page1);
            return R.ok().put("page",pageUtils);
        }
        List<Long> ids = supplierEntityList.stream().map(p -> p.getSupplierid()).collect(Collectors.toList());
        if(StringUtils.isNotBlank(conditions.getSupName())){
            List<Long> supIds= busSupplierProductService.getSupIdsByProNameAndSupName(conditions.getSupName());
            ids = ids.stream().filter(item -> supIds.contains(item)).collect(Collectors.toList());
        }
        if(ids==null||ids.size()==0){
            Integer limit =Integer.valueOf(params.get("limit").toString());
            Integer page1 =Integer.valueOf(params.get("page").toString());
            PageUtils pageUtils =new PageUtils(supplierEntityList,0,limit,page1);
            return R.ok().put("page",pageUtils);
        }
        String address = entity.getAddress() + entity.getHousenumber();
        String[] latlng = BaiDuMapHelper.getLatLng(address);
        conditions.setMLongitude(latlng[1]);
        conditions.setMLatitude(latlng[0]);
        PageUtils page = busSupplierService.queryPage(params,ids,conditions);
        List<SupplierInfo> infos= new ArrayList<>();
        List<BusSupplierEntity> supplierEntities=(List<BusSupplierEntity>)page.getList();
        for (BusSupplierEntity supplier:   supplierEntities  ) {
            SupplierInfo supplierInfo=new SupplierInfo();
            supplierInfo.setId(supplier.getSupplierid());
            supplierInfo.setPhone(supplier.getContactway());
            supplierInfo.setName(supplier.getSuppliername());
            supplierInfo.setAdr(supplier.getAddress()+supplier.getHousenumber());
            supplierInfo.setPayWay(supplier.getSettlementmodes());
            supplierInfo.setDistance(supplier.getDistance());
            supplierInfo.setDeliveryMethod(supplier.getDeliverymethod());
            supplierInfo.setWebPath(supplier.getWebpath());
            AdMerchantSupplierCollectionEntity collectionEntity =adMerchantSupplierCollectionService.getStatusByMerchantId
                    (supplier.getSupplierid(),entity.getMerchantid());
            supplierInfo.setStatus(collectionEntity==null?0:collectionEntity.getCollectStatus());
            supplierInfo.setCollectCount(supplier.getCollectAmount());
            if(StringUtils.isNotBlank(supplier.getRelevantquotationresids())){
                List<String> urls= new ArrayList<>();
                List<String> idss= UtilString.stringToList(supplier.getRelevantquotationresids());
                for (String oid:idss) {
                    AdOssEntity oss=adOssService.findById(Long.valueOf(oid));
                    urls.add(oss==null?null:oss.getUrl());
                }
                supplierInfo.setUrls(urls);
            }
            supplierInfo.setTypeName(busSupplierProductService.getTypeNamesBySupId(supplier.getSupplierid()));
            infos.add(supplierInfo);
        }

        page.setList(infos);
        return R.ok().put("page",page);
    }
    /**
     * 根据分类和厂家ID获取厂家产品
     */
    @ApiOperation("根据分类和厂家ID获取厂家产品")
    @RequestMapping(value = "/proListByType", method = RequestMethod.GET)
    public R info(@RequestParam Map<String, Object> params,Long id,Long typeId) {
        TMerchantEntity tMerchantEntity =tMerchantService.getInfoByUserId(getUserId());
        if(typeId==null||typeId==0){
            PageUtils page =busSupplierProductService.getAllProList(params,id,tMerchantEntity.getMerchantid());
            return R.ok().put("page",page);
        }else{
            PageUtils page =busSupplierProductService.getProListByType(params,id,tMerchantEntity.getMerchantid(),typeId);
            return R.ok().put("page",page);
        }

    }
    /**
     * 是否有权限
     */
    @ApiOperation("是否有权限")
    @RequestMapping(value = "/havPerm", method = RequestMethod.GET)
    @RequiresPermissions("ad:supplier:havPerm")
    public R info() {
        return R.ok();
    }

}
