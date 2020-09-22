package com.yitongyin.modules.mp.controller;

import com.yitongyin.common.annotion.Login;
import com.yitongyin.common.annotion.LoginUser;
import com.yitongyin.common.utils.PageUtils;
import com.yitongyin.common.utils.R;
import com.yitongyin.modules.ad.entity.AdClientEntity;
import com.yitongyin.modules.ad.entity.AdClientProductCollectRecordEntity;
import com.yitongyin.modules.ad.service.AdClientProductCollectRecordService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;


/**
 * 客户产品收藏记录表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-07 14:44:03
 */
@RestController
@RequestMapping("smp/productcollectrecord")
public class MpClientProductCollectRecordController {
    @Autowired
    private AdClientProductCollectRecordService adClientProductCollectRecordService;

    /**
     * 列表
     */
    @Login
    @GetMapping("/list")
    @ApiOperation("产品收藏记录表")
    public R list(@RequestParam Map<String, Object> params, @LoginUser AdClientEntity user, Long merchantId){
        PageUtils page = adClientProductCollectRecordService.queryPage(params,user.getClientid(),merchantId);
        return R.ok().put("page", page);
    }
    /**
     * 产品收藏
     */
    @Login
    @PostMapping("/collect")
    @ApiOperation("产品收藏")
    public R collect(@RequestAttribute("clientId") Long clientId, Long merProductId) {
        AdClientProductCollectRecordEntity entity = new AdClientProductCollectRecordEntity();
        entity.setClientId(clientId);
        entity.setMerchantProductId(merProductId);
        if(adClientProductCollectRecordService.addOneByTime(entity)){
            return R.ok();
        }
        return R.error();
    }
    /**
     * 产品取消收藏
     */
    @Login
    @GetMapping("/collect/cancel")
    @ApiOperation("产品取消收藏")
    public R collectCancel(@RequestAttribute("clientId") Long clientId, Long merProductId) {
        AdClientProductCollectRecordEntity entity = new AdClientProductCollectRecordEntity();
        entity.setClientId(clientId);
        entity.setMerchantProductId(merProductId);
        adClientProductCollectRecordService.delOne(entity);
        return R.ok();
    }
    /**
     * 产品收藏状态
     */
    @Login
    @GetMapping("/collect/state")
    @ApiOperation("产品取消收藏")
    public R collectState(@RequestAttribute("clientId") Long clientId, Long merProductId) {
        AdClientProductCollectRecordEntity entity = new AdClientProductCollectRecordEntity();
        entity.setClientId(clientId);
        entity.setMerchantProductId(merProductId);
         if(adClientProductCollectRecordService.getStaOne((entity))){
             return R.ok().put("collect",1);
         }
         return R.ok().put("collect",0);
    }
    @Login
    @PostMapping("/collect/del")
    @ApiOperation("删除")
    public R del(@RequestBody List<Long> ids){
        adClientProductCollectRecordService.removeByIds(ids);
        return R.ok();
    }
}
