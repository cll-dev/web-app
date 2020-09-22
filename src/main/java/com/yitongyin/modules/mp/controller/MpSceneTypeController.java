package com.yitongyin.modules.mp.controller;

import com.yitongyin.common.utils.R;
import com.yitongyin.modules.ad.entity.AdSceneTypeEntity;
import com.yitongyin.modules.ad.service.AdSceneTypeService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


/**
 * 场景标签表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-20 15:37:35
 */
@RestController
@RequestMapping("mp/sceneType")
public class MpSceneTypeController {
    @Autowired
    private AdSceneTypeService adSceneTypeService;

    /**
     * 位置列表
     */
    @ApiOperation("位置下拉列表")
    @GetMapping("/position/list")
    public R positionList(){
        List<AdSceneTypeEntity> list = adSceneTypeService.getPositionSearchList();
        return R.ok().put("page", list);
    }
    /**
     * 空间列表
     */
    @ApiOperation("空间下拉列表")
    @GetMapping("/space/list")
    public R spaceList(){
        List<AdSceneTypeEntity> list = adSceneTypeService.getSpaceSearchList();
        return R.ok().put("page", list);
    }


}
