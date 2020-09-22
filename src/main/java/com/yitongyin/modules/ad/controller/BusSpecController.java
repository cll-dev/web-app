package com.yitongyin.modules.ad.controller;

import com.yitongyin.common.utils.R;
import com.yitongyin.modules.ad.entity.BusSpecEntity;
import com.yitongyin.modules.ad.form.AdSpecKey;
import com.yitongyin.modules.ad.form.AdSpecValue;
import com.yitongyin.modules.ad.service.BusSpecService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 规格表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-29 14:13:42
 */
@RestController
@RequestMapping("bus/busSpec")
public class BusSpecController {
    @Autowired
    private BusSpecService busSpecService;

    /**
     * 列表
     */
    @ApiOperation("总后台规格KeyList及key下所有value")
    @GetMapping("/listChild")
    @ResponseBody
    public R listChild(){
        List<BusSpecEntity> busSpecEntityList= busSpecService.getKeyValues();
        Map<String,List<BusSpecEntity>> maps=busSpecEntityList.stream()
                .collect(Collectors.groupingBy(BusSpecEntity::getSpeckey));
        List<AdSpecKey> keyList = new ArrayList<>();
        for (String key :maps.keySet()){
           AdSpecKey adSpecKey =new AdSpecKey();
           adSpecKey.setSpecKey(key);
           adSpecKey.setSpecName(maps.get(key).get(0).getSpecName());
           List<AdSpecValue> adSpecValues = maps.get(key).stream().map(entity -> {
               AdSpecValue adSpecValue =new AdSpecValue();
               adSpecValue.setValueId(entity.getId());
               adSpecValue.setValueName(entity.getValName());
               return  adSpecValue;
           }).collect(Collectors.toList());
           adSpecKey.setValues(adSpecValues);
           keyList.add(adSpecKey);
        }
        return R.ok().put("list", keyList);
    }




}
