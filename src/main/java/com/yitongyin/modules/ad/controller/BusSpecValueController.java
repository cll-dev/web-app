package com.yitongyin.modules.ad.controller;

import com.yitongyin.common.utils.R;
import com.yitongyin.modules.ad.entity.BusSpecValueEntity;
import com.yitongyin.modules.ad.service.BusSpecValueService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;



/**
 * 规格值表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-29 14:13:42
 */
@RestController
@RequestMapping("bus/busSpecValue")
public class BusSpecValueController {
    @Autowired
    private BusSpecValueService busSpecValueService;

    /**
     * 根据规格Key获取规格值列表
     */
    @ApiOperation("根据规格Key获取规格值列表")
    @GetMapping("/listByKey")
    public R list(String key){
        List<BusSpecValueEntity> list= busSpecValueService.getByKey(key);

        return R.ok().put("list", list);
    }
}
