package com.yitongyin.modules.mp.controller;

import com.yitongyin.common.utils.PageUtils;
import com.yitongyin.common.utils.R;
import com.yitongyin.modules.ad.entity.AdOssEntity;
import com.yitongyin.modules.ad.entity.BusMaterialEntity;
import com.yitongyin.modules.ad.form.BusMaterialConditions;
import com.yitongyin.modules.ad.form.BusMaterialView;
import com.yitongyin.modules.ad.service.AdOssService;
import com.yitongyin.modules.ad.service.BusMaterialService;
import com.yitongyin.nosql.elasticsearch.document.EsMaterial;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 素材表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-17 09:48:32
 */
@RestController
@RequestMapping("mp/material")
public class MpMaterialController {
    @Autowired
    private BusMaterialService busMaterialService;
    @Autowired
    private AdOssService adOssService;

    /**
     * 列表
     */
    @ApiOperation("获取素材列表")
    @RequestMapping(value = "/list", method = RequestMethod.POST)
    public R list(@RequestParam Map<String, Object> params, @RequestBody BusMaterialConditions conditions) {
        PageUtils page = busMaterialService.queryPage1(params, conditions);
        List<EsMaterial> entities = (List<EsMaterial>) page.getList();
        List<BusMaterialView> views = entities.stream().map(busMaterialEntity -> {
            BusMaterialView view = new BusMaterialView();
            view.setId(busMaterialEntity.getId());
            view.setTitle(busMaterialEntity.getTitle());
            if(busMaterialEntity.getCoverResId()!=null){
                AdOssEntity ossEntity = adOssService.findById(busMaterialEntity.getCoverResId());
                view.setImgUrl(ossEntity==null?"":ossEntity.getUrl());
            }
            if(busMaterialEntity.getFileResId()!=null){
                AdOssEntity ossEntity1 = adOssService.findById(busMaterialEntity.getFileResId());
                view.setSourceUrl(ossEntity1==null?"":ossEntity1.getUrl());
            }
            view.setType(busMaterialEntity.getFileType());
            switch (busMaterialEntity.getFileType()){
                case 1: view.setTypeStr("PSD");
                    break;
                case 2: view.setTypeStr("AI");
                    break;
                case 3: view.setTypeStr("CDR");
                    break;
                case 4: view.setTypeStr("EPS");
                    break;
                case 5: view.setTypeStr("TIF");
                    break;
                case 6: view.setTypeStr("JPG");
                    break;
                case 7: view.setTypeStr("PDF");
                    break;
                default: view.setTypeStr("未知");
            }
            return view;
        }).collect(Collectors.toList());
        page.setList(views);
        return R.ok().put("page", page);
    }






}
