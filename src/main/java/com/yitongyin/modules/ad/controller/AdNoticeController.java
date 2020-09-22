package com.yitongyin.modules.ad.controller;


import com.yitongyin.common.utils.PageUtils;
import com.yitongyin.common.utils.R;
import com.yitongyin.modules.ad.entity.AdNoticeEntity;
import com.yitongyin.modules.ad.entity.AdOssEntity;
import com.yitongyin.modules.ad.service.AdNoticeService;
import com.yitongyin.modules.ad.service.AdOssService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 公告表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-08 12:59:42
 */
@RestController
@RequestMapping("ad/notice")
public class AdNoticeController {


    @Autowired
    private AdNoticeService adNoticeService;
    @Autowired
    private AdOssService adOssService;

    @ApiOperation("根据分类获取前count条最新公告")
    @RequestMapping(value = "/listByType", method = RequestMethod.GET)
    @ResponseBody
    public R getListByType() {
        Map<String, Object> map = new HashMap<>();
        List<AdNoticeEntity> pics=adNoticeService.findNewByType(1,3);
        List<AdNoticeEntity> picUrls=pics.stream().map(pic->{
            AdNoticeEntity entity = new AdNoticeEntity();
            if(pic.getThumbnailResId()!=null){
                AdOssEntity oss= adOssService.findById(pic.getThumbnailResId());
                entity.setUrl(oss==null?null:oss.getUrl());
            }
            entity.setOutLink(pic.getOutLink());
            return entity;
        }).collect(Collectors.toList());

        List<AdNoticeEntity> opes=adNoticeService.findNewByType(2,10);
        List<AdNoticeEntity> devs=adNoticeService.findNewByType(3,10);
        map.put("pic",picUrls);
        map.put("opes",opes);
        map.put("devs",devs);
        return R.ok().put("list",map);
    }
    @ApiOperation("根据ID查看详情")
    @RequestMapping(value = "/info", method = RequestMethod.GET)
    public R getListByType(Long id) {
        AdNoticeEntity adNoticeEntity =adNoticeService.getById(id);
        return R.ok().put("info",adNoticeEntity);
    }
    @ApiOperation("根据分类分页获取营销推广最新公告")
    @RequestMapping(value = "/pageByType", method = RequestMethod.GET)
    @ResponseBody
    public R pageByType(@RequestParam Map<String, Object> params, Integer type) {
        PageUtils pageUtils = adNoticeService.pageNewByType(params,type);
        List<AdNoticeEntity> list =(List<AdNoticeEntity>) pageUtils.getList();
        for (AdNoticeEntity adNoticeEntity: list) {
            if(adNoticeEntity.getThumbnailResId()!=null){
                AdOssEntity adOssEntity = adOssService.findById(adNoticeEntity.getThumbnailResId());
                adNoticeEntity.setUrl(adOssEntity==null?null:adOssEntity.getUrl());
            }
        }
        return R.ok().put("page",pageUtils);
    }

}
