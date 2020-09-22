package com.yitongyin.modules.ad.controller;

import com.yitongyin.common.utils.R;
import com.yitongyin.modules.ad.entity.AdClientTagEntity;
import com.yitongyin.modules.ad.entity.AdTagEntity;
import com.yitongyin.modules.ad.entity.TMerchantEntity;
import com.yitongyin.modules.ad.service.AdClientTagService;
import com.yitongyin.modules.ad.service.AdTagService;
import com.yitongyin.modules.ad.service.TMerchantService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;


/**
 * 标签表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-07 09:31:21
 */
@RestController
@RequestMapping("ad/tag")
public class AdTagController extends AbstractController {
    @Autowired
    private AdTagService adTagService;
    @Autowired
    TMerchantService tMerchantService;
    @Autowired
    AdClientTagService adClientTagService;

    /**
     * 列表
     */
    @GetMapping("/list")
    @ApiOperation("客户标签列表")
    public R list(){
        TMerchantEntity tMerchantEntity = tMerchantService.getInfoByUserId(getUserId());
        if(tMerchantEntity==null)return R.error("数据异常");
        List<AdTagEntity> list =adTagService.getClientByMerchantId(tMerchantEntity.getMerchantid());
        return R.ok().put("list", list);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{tagId}")
    @ApiOperation("客户标签详情")
    public R info(@PathVariable("tagId") Integer tagId){
        AdTagEntity adTag = adTagService.getById(tagId);

        return R.ok().put("info", adTag);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    @ApiOperation("添加标签")
    public R save(@RequestBody AdTagEntity adTag){
        TMerchantEntity tMerchantEntity = tMerchantService.getInfoByUserId(getUserId());
        if(tMerchantEntity==null)return R.error("数据异常");
        adTag.setMerchantId(tMerchantEntity.getMerchantid());
        adTagService.save(adTag);

        return R.ok();
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @ApiOperation("修改标签")
    public R update(@RequestBody AdTagEntity adTag){
        adTagService.updateById(adTag);
        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @ApiOperation("删除标签")
    public R delete(Integer tagId){
        if(adClientTagService.removeByTagId(tagId)){
            adTagService.removeById(tagId);
        }
        return R.ok();
    }
    /**
     * 添加用户
     */
    @PostMapping("/tag/add")
    @ApiOperation("商户标签添加用户")
    public R add(@RequestBody List<AdClientTagEntity> list){

        if(adClientTagService.addByClientTag(list)){
            return R.ok();
        }
        return R.error();
    }
    /**
     * 删除用户
     */
    @PostMapping("/tag/delete")
    @ApiOperation("商户标签删除用户")
    public R delete(@RequestBody List<Long> ids){
        adClientTagService.removeByIds(ids);
        return R.ok();
    }

    /**
     * 用户添加标签
     */
    @PostMapping("/tag/addTags")
    @ApiOperation("商户为用户添加标签")
    public R addTags(@RequestBody List<AdClientTagEntity> list,Long  clientId){
        TMerchantEntity tMerchantEntity = tMerchantService.getInfoByUserId(getUserId());
        if(tMerchantEntity==null)return R.error("数据异常");
        List<AdTagEntity> tagList=adTagService.getListByMerchantId(tMerchantEntity.getMerchantid());
        List<Integer> tagIds=tagList.stream().map(e->e.getTagid()).collect(Collectors.toList());
        if(adClientTagService.addTags(list,tagIds,clientId)){
            return R.ok();
        }
        return R.error();
    }
}
