package com.yitongyin.modules.mp.controller;

import com.yitongyin.common.utils.PageUtils;
import com.yitongyin.common.utils.R;
import com.yitongyin.modules.ad.entity.AdMerchantProductEntity;
import com.yitongyin.modules.ad.entity.AdSceneProjectEntity;
import com.yitongyin.modules.ad.service.AdMerchantProductService;
import com.yitongyin.modules.ad.service.AdSceneProjectService;
import com.yitongyin.modules.mp.View.ProductInfo;
import com.yitongyin.modules.mp.View.SceneProject;
import com.yitongyin.modules.mp.View.SceneProjectSearch;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;


/**
 * 场景主表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-20 15:37:35
 */
@RestController
@RequestMapping("mp/sceneProject")
public class MpSceneProjectController {

    @Autowired
    private AdSceneProjectService adSceneProjectService;
    @Autowired
    private AdMerchantProductService adMerchantProductService;

    /**
     * 列表
     */
    @ApiOperation("根据条件获取场景列表")
    @PostMapping("/list")
    public R list(@RequestParam Map<String, Object> params, @RequestBody(required = false) SceneProjectSearch search) {
        PageUtils page = adSceneProjectService.queryPageByConditions(params,search);
        return R.ok().put("page", page);
    }
    /**
     * 详情
     */
    @ApiOperation("场景详情")
    @GetMapping("/info")
    public R info(Long id,Long merchantId) {
        SceneProject info = adSceneProjectService.getInfoById(id,merchantId);
        return R.ok().put("info",info);
    }
    /**
     * 点赞
     */
    @ApiOperation("场景点赞")
    @GetMapping("/praise")
    public R praise(Long id) {
        AdSceneProjectEntity adSceneProject  = adSceneProjectService.getById(id);
        int random=(int)(Math.random()*10+1);
        adSceneProject.setThumbsUpNumber(adSceneProject.getThumbsUpNumber()+random);
        adSceneProjectService.updateById(adSceneProject);
        return R.ok();
    }
    /**
     * 场景产品查看明细
     */
    @ApiOperation("场景产品查看明细")
    @GetMapping("/product/list")
    public R getTypeProducts(@RequestParam Map<String, Object> params,Long productId,Long merchantId) {
        AdMerchantProductEntity productEntity = adMerchantProductService.mpGetOneByMerIdAndProId(merchantId,productId);
        if(productEntity==null){
            return R.error("数据错误");
        }
        PageUtils pageUtils = adSceneProjectService.getProListByType(params,productEntity.getServiceTypeId(),merchantId);
        return R.ok().put("page",pageUtils);
    }


}
