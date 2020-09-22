package com.yitongyin.modules.ad.controller;

import com.alibaba.fastjson.JSON;
import com.yitongyin.common.utils.ClassUtil;
import com.yitongyin.common.utils.ListUtils;
import com.yitongyin.common.utils.R;
import com.yitongyin.modules.ad.entity.*;
import com.yitongyin.modules.ad.form.AdSpecGroup;
import com.yitongyin.modules.ad.form.SpecInCommon;
import com.yitongyin.modules.ad.form.SupSpecKeyValueMode;
import com.yitongyin.modules.ad.form.SupSpecMode;
import com.yitongyin.modules.ad.service.AdMerchantSpecGroupService;
import com.yitongyin.modules.ad.service.AdMerchantSpecValueService;
import com.yitongyin.modules.ad.service.BusSupplierService;
import com.yitongyin.modules.ad.service.TMerchantService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;


/**
 * 商户规格组合对应的价格表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-29 15:31:16
 */
@RestController
@RequestMapping("ad/specGroup")
public class AdMerchantSpecGroupController extends AbstractController{
    @Autowired
    private AdMerchantSpecGroupService adMerchantSpecGroupService;
    @Autowired
    private TMerchantService tMerchantService;
    @Autowired
    private BusSupplierService busSupplierService;
    @Autowired
    private AdMerchantSpecValueService adMerchantSpecValueService;

    @ApiOperation("根据产品id获取规格组和规格值")
    @GetMapping("/getByProductId")
    public R getByProductId(Long productId){
        TMerchantEntity merchantEntity=tMerchantService.getInfoByUserId(getUserId());
        AdMerchantSpecGroupEntity groupEntity = adMerchantSpecGroupService.getByMerAndPro(merchantEntity.getMerchantid(),productId);
        if(groupEntity==null){
            return R.ok().put("list",null).put("valueList",null);
        }
        BusSupplierEntity busSupplierEntity = busSupplierService.getById(groupEntity.getSupplierId());
        Long supplierId=null;
        if(busSupplierEntity==null){
            BusSupplierEntity supplierEntity1=busSupplierService.getByLevelId();
            if(supplierEntity1!=null){
                supplierId=supplierEntity1.getSupplierid();
            }else{
                return R.error("暂无模板厂家数据");
            }
        }else{
            supplierId=busSupplierEntity.getSupplierid();
        }
        List<BusSupplierSpecValueEntity> valueList = new ArrayList<>();
        List<AdMerchantSpecGroupEntity> headValueList = new ArrayList<>();
        List<AdMerchantSpecGroupEntity> groupList = adMerchantSpecGroupService.findSpecGroupByProductId(merchantEntity.getMerchantid(),productId,supplierId,valueList,headValueList);
        if(groupList==null || groupList.size()==0){
            return R.ok().put("list",null).put("valueList",null);
        }
        List<AdMerchantSpecGroupEntity> sortList = groupList.stream().sorted(Comparator.comparing(AdMerchantSpecGroupEntity::getOrderNumber,Comparator.nullsFirst(Integer::compareTo))).collect(Collectors.toList());
        Map<List<BusSupplierSpecValueEntity> , List<AdMerchantSpecGroupEntity>> listMap = sortList.stream().collect(Collectors.groupingBy(AdMerchantSpecGroupEntity::getHeadSpecValues));
        List<Object> modeList = new ArrayList<>();
        Object obj2 =new Object();
        List<BusSupplierSpecValueEntity> unique = valueList.stream().distinct().collect(Collectors.toList());
        List<BusSupplierSpecValueEntity> sortUnique = unique.stream().sorted(Comparator.comparing(BusSupplierSpecValueEntity::getKeyOrderNumber,Comparator.nullsFirst(Integer::compareTo))).collect(Collectors.toList());
        Map<String , List<BusSupplierSpecValueEntity>> uniqueMap = sortUnique.stream().collect(Collectors.groupingBy(BusSupplierSpecValueEntity::getSpecKeyName));

        Integer attrLength=uniqueMap.size();
        for (List<BusSupplierSpecValueEntity> key: listMap.keySet()) {
            SupSpecMode mode =new SupSpecMode();
            HashMap addMap = new  HashMap();
            HashMap addValMap = new  HashMap();
            mode.setHead(key);
            mode.setLines(listMap.get(key));
            List<Long> keyIds=key.stream().map(e->e.getSpecValueId()).collect(Collectors.toList());
            for (AdMerchantSpecGroupEntity groupEntity1: headValueList) {
                List<Long> groupIds=groupEntity1.getHeadSpecValues().stream().map(c->c.getSpecValueId()).collect(Collectors.toList());
                if(groupIds.containsAll(keyIds)){
                    mode.setInCommon(groupEntity1.getHeadSpecValues().get(0).getInCommon());
                }
            }
//            key.forEach(e->{
//                e.setInCommon(null);
//            });
            for (int i = 0; i < attrLength; i++) {
                try{
                    if(i>=key.size()){
                        addMap.put("order"+i, Class.forName("java.lang.Integer"));
                        addValMap.put("order"+i, 0);
                    }else{
                        addMap.put("order"+i, Class.forName("java.lang.Integer"));
                        addValMap.put("order"+i, key.get(i).getOrderNumber());
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
            try {
                obj2= new ClassUtil().dynamicClass(mode,addMap,addValMap);
                System.out.println(JSON.toJSONString(obj2));
            }catch (Exception e){
                e.printStackTrace();
            }
            modeList.add(obj2);
        }
        List<SupSpecKeyValueMode> valueModes = new ArrayList<>();
        for (String key: uniqueMap.keySet()) {
            List<BusSupplierSpecValueEntity> sortValue = uniqueMap.get(key).stream().sorted(Comparator.comparing(BusSupplierSpecValueEntity::getOrderNumber,Comparator.nullsFirst(Integer::compareTo))).collect(Collectors.toList());
            SupSpecKeyValueMode valueMode = new SupSpecKeyValueMode();
            valueMode.setKeyName(key);
            valueMode.setList(sortValue);
            valueMode.setKeyOrder(sortValue.get(0).getKeyOrderNumber());
            sortValue.forEach(e->{
                e.setKeyOrderNumber(null);
            });
            valueModes.add(valueMode);
        }
        Field[] field = obj2.getClass().getDeclaredFields();
        String[] attrNames =new String[attrLength];
        Integer a=0;
        for (int i = 0,length=field.length; i < length; i++) {
            if(field[i].getName().contains("order")){
                attrNames[a]=field[i].getName();
                a++;
            }
        }
        Arrays.sort(attrNames);
//        for (int i = 0; i < attrLength; i++) {
//            attrNames[i]= attrNames[i].replace()
//        }
        ListUtils.sort(modeList, true,attrNames);
        List<SupSpecKeyValueMode> sortValueModes = valueModes.stream().sorted(Comparator.comparing(SupSpecKeyValueMode::getKeyOrder,Comparator.nullsFirst(Integer::compareTo))).collect(Collectors.toList());
        BusSupplierEntity supplier = busSupplierService.getById(supplierId);
        return R.ok().put("list", modeList).put("valueList",sortValueModes).put("sup",supplier);
    }
    @ApiOperation("根据产品id判断该商户产品下是否有配置的规格")
    @GetMapping("/ableByProId")
    public R ableByProId(Long productId){
        TMerchantEntity merchantEntity=tMerchantService.getInfoByUserId(getUserId());
        Boolean hvData=adMerchantSpecGroupService.getByProductIdAndMerchantId(merchantEntity.getMerchantid(),productId);
        return R.ok().put("hvData",hvData);
    }

    @ApiOperation("商户修改或添加规格")
    @PostMapping("/updOrSave")
    @ResponseBody
    public R updOrSave(@RequestBody List<AdSpecGroup> specGroup,Long productId){

        TMerchantEntity merchantEntity=tMerchantService.getInfoByUserId(getUserId());
        adMerchantSpecGroupService.saveOrUpdBatch(specGroup,merchantEntity.getMerchantid(),productId);
        return R.ok();

    }
    @ApiOperation("商户开关常用规格")
    @PostMapping("/isInCommon")
    @ResponseBody
    public R updOrSave(@RequestBody SpecInCommon atr){
        TMerchantEntity merchantEntity = tMerchantService.getInfoByUserId(getUserId());
        List<AdMerchantSpecGroupEntity> list= adMerchantSpecGroupService.getListByProAndMerchant(merchantEntity.getMerchantid(),atr.getProductId());
        if(list==null||list.size()==0){
            return R.error("数据错误");
        }
        List<Long> curMsgIds=list.stream().map(e->e.getMsgid()).collect(Collectors.toList());
        adMerchantSpecValueService.updInCommon(atr,curMsgIds);
        return R.ok();
    }

}
