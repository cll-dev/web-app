package com.yitongyin.modules.ad.controller;

import com.alibaba.fastjson.JSON;
import com.yitongyin.common.utils.ClassUtil;
import com.yitongyin.common.utils.ListUtils;
import com.yitongyin.common.utils.R;
import com.yitongyin.common.utils.UtilString;
import com.yitongyin.modules.ad.entity.BusSupplierEntity;
import com.yitongyin.modules.ad.entity.BusSupplierSpecGroupEntity;
import com.yitongyin.modules.ad.entity.BusSupplierSpecValueEntity;
import com.yitongyin.modules.ad.form.SupSpecKeyValueMode;
import com.yitongyin.modules.ad.form.SupSpecMode;
import com.yitongyin.modules.ad.service.BusSupplierService;
import com.yitongyin.modules.ad.service.BusSupplierSpecGroupService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.rmi.CORBA.Util;
import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;


/**
 * 厂家对应产品的规格价格表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-30 10:40:45
 */
@RestController
@RequestMapping("bus/supplierSpecGroup")
public class BusSupplierSpecGroupController {
    @Autowired
    private BusSupplierSpecGroupService busSupplierSpecGroupService;
    @Autowired
    private BusSupplierService busSupplierService;

    /**
     * 列表
     */
    @ApiOperation("获取产品对应厂家的规格")
    @GetMapping("/list")
    @ResponseBody
    public R list(Long supplierId,Long productId){
        if(supplierId==0l){
            BusSupplierEntity busSupplierEntity = busSupplierService.getByLevelId();
            if(busSupplierEntity!=null){
                supplierId=busSupplierEntity.getSupplierid();
            }
        }
        List<BusSupplierSpecValueEntity> valueList = new ArrayList<>();
        List<BusSupplierSpecGroupEntity> list = busSupplierSpecGroupService.queryBySupAndProId(supplierId,productId,valueList,null);
       if(list==null||list.size()==0) return R.error(501,"该产品暂未设置规格,请联系客户");
        List<BusSupplierSpecGroupEntity> sortList = list.stream().sorted(Comparator.comparing(BusSupplierSpecGroupEntity::getOrderNumber,Comparator.nullsFirst(Integer::compareTo))).collect(Collectors.toList());
        List<BusSupplierSpecValueEntity> unique = valueList.stream().distinct().collect(Collectors.toList());
        List<BusSupplierSpecValueEntity> sortUnique = unique.stream().sorted(Comparator.comparing(BusSupplierSpecValueEntity::getKeyOrderNumber,Comparator.nullsFirst(Integer::compareTo))).collect(Collectors.toList());
        Map<String , List<BusSupplierSpecValueEntity>> uniqueMap = sortUnique.stream().collect(Collectors.groupingBy(BusSupplierSpecValueEntity::getSpecKeyName));
        Map<List<BusSupplierSpecValueEntity> , List<BusSupplierSpecGroupEntity>> listMap = sortList.stream().collect(Collectors.groupingBy(BusSupplierSpecGroupEntity::getHeadSpecValues));

        List<Object> modeList = new ArrayList<>();
        Object obj2 =new Object();
        Integer attrLength=0;
        for (List<BusSupplierSpecValueEntity> key: listMap.keySet()) {
            HashMap addMap = new  HashMap();
            HashMap addValMap = new  HashMap();
            SupSpecMode mode =new SupSpecMode();
            mode.setHead(key);
            mode.setLines(listMap.get(key));
            for (int i = 0,length=key.size(); i < length; i++) {
                try{
                    addMap.put("order"+i, Class.forName("java.lang.Integer"));
                    addValMap.put("order"+i, key.get(i).getOrderNumber());
                }catch (Exception e){
                    e.printStackTrace();
                }
               // ReflectUtils.setValue(mode,"order"+i,mode.getHead().get(i).getKeyOrderNumber().toString());
            }
            attrLength=addMap.size();
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
        return R.ok().put("list", modeList).put("valueList",sortValueModes);
    }


}
