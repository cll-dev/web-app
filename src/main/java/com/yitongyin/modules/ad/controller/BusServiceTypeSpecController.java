package com.yitongyin.modules.ad.controller;

import com.yitongyin.common.utils.R;
import com.yitongyin.modules.ad.entity.BusSpecEntity;
import com.yitongyin.modules.ad.service.BusServiceTypeSpecService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 产品分类规格字段表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-09-16 15:15:20
 */
@RestController
@RequestMapping("bus/servicetypespec")
public class BusServiceTypeSpecController {
    @Autowired
    private BusServiceTypeSpecService busServiceTypeSpecService;

    /**
     * 列表
     */
    @ApiOperation("根据产品分类获取该产品的keyList")
    @GetMapping("/listByServiceType")
    public R listChild(Long serviceTypeId){
         List<BusSpecEntity> keys=busServiceTypeSpecService.getKeysByServiceTypeId(serviceTypeId);
         return R.ok().put("keys",keys);
    }



}
