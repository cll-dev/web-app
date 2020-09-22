package com.yitongyin.modules.mp.controller;

import com.yitongyin.common.utils.R;
import com.yitongyin.modules.ad.entity.AdMerchantRegionShowEntity;
import com.yitongyin.modules.ad.entity.AdOssEntity;
import com.yitongyin.modules.ad.entity.TMerchantEntity;
import com.yitongyin.modules.ad.service.AdMerchantRegionShowService;
import com.yitongyin.modules.ad.service.AdOssService;
import com.yitongyin.modules.ad.service.TMerchantService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 商户广告素材表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-08-12 17:00:55
 */
@RestController
@RequestMapping("mp/merchantRegionShow")
public class MpMerchantRegionShowController {
    @Autowired
    private AdMerchantRegionShowService adMerchantRegionShowService;
    @Autowired
    private TMerchantService tMerchantService;
    @Autowired
    private AdOssService adOssService;

    /**
     * 列表
     */
    @ApiOperation("获取不同位置广告列表")
    @GetMapping("/listByRegion")
    public R list(Long merchantId){
        if(merchantId==null||merchantId==0){
          return R.error("无效的参数");
        }
     //   TMerchantEntity merchantEntity = tMerchantService.getById(merchantId);
        Map<String, List<AdMerchantRegionShowEntity>> shows=adMerchantRegionShowService.getByMerchantId(merchantId);
        List<AdMerchantRegionShowEntity> list= new ArrayList<>();
  //      AdMerchantRegionShowEntity showEntity =null;
        for (String key: shows.keySet()) {
//            if(key.equals("首页幻灯片")&&merchantEntity.getShopphotoid()!=null){
//                AdOssEntity ossEntity = adOssService.findById(merchantEntity.getShopphotoid());
////                if(shows.get(key).size()>0){
//                    showEntity = new AdMerchantRegionShowEntity();
//                    showEntity.setCoverUrl(ossEntity.getUrl());
//                    shows.get(key).add(0,showEntity);
////                }
//            }
            AdMerchantRegionShowEntity showEntity1 =new AdMerchantRegionShowEntity();
            showEntity1.setRegion(key);
            showEntity1.setList(shows.get(key));
            list.add(showEntity1);
        }
        return R.ok().put("list", list);
    }




}
