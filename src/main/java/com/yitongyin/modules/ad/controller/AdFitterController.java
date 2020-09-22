package com.yitongyin.modules.ad.controller;

import com.yitongyin.common.utils.PageUtils;
import com.yitongyin.common.utils.R;
import com.yitongyin.common.utils.UtilString;
import com.yitongyin.modules.ad.entity.AdFitterEntity;
import com.yitongyin.modules.ad.entity.AdOssEntity;
import com.yitongyin.modules.ad.entity.TMerchantEntity;
import com.yitongyin.modules.ad.service.AdFitterService;
import com.yitongyin.modules.ad.service.AdOssService;
import com.yitongyin.modules.ad.service.TMerchantService;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 安装工表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-10-30 09:23:07
 */
@RestController
@RequestMapping("ad/fitter")
public class AdFitterController {
    @Autowired
    private AdFitterService adFitterService;
    @Autowired
    private AdOssService adOssService;
    @Autowired
    private TMerchantService tMerchantService;

    /**
     * 列表
     */
    @GetMapping("/list")
    @ApiOperation("安装工列表")
    public R list(@RequestParam Map<String, Object> params,Long merchantId){

        PageUtils page=new PageUtils(new ArrayList<>(),0,0,1);
        if(merchantId==null){
             page = adFitterService.queryPage(params);
        }else{
            TMerchantEntity tMerchantEntity=tMerchantService.getById(merchantId);
            if(tMerchantEntity==null){
                return R.error("数据异常");
            }
             page = adFitterService.queryPage(params,tMerchantEntity.getCounty());
        }
        List<AdFitterEntity> list=(List<AdFitterEntity>)page.getList();
        for (AdFitterEntity entity: list) {
            if(entity.getHeadImgId()!=null){
                AdOssEntity oss=adOssService.findById(entity.getHeadImgId());
                entity.setHeadUrl(oss==null?null:oss.getUrl());
            }
        }
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    @ApiOperation("安装工信息")
    public R info(@PathVariable("id") Long id){
		AdFitterEntity adFitter = adFitterService.getById(id);
		if(adFitter==null){
            return R.ok().put("info", null);
        }
        if(adFitter.getHeadImgId()!=null){
            AdOssEntity oss=adOssService.findById(adFitter.getHeadImgId());
            adFitter.setHeadUrl(oss==null?null:oss.getUrl());
        }
        if(StringUtils.isNotBlank(adFitter.getCertificatePhotos())){
            List<String> ids =UtilString.stringToList(adFitter.getCertificatePhotos());
            List<Long> longList=ids.stream().map(e->Long.valueOf(e)).collect(Collectors.toList());
            List<AdOssEntity> ossEntities =adOssService.findByIds(longList);
            List<String> urls=ossEntities.stream().map(e->e.getUrl()).collect(Collectors.toList());
            adFitter.setCertificateUrls(urls);
        }
        adFitter.setViewCounts(adFitter.getViewCounts()+1);
        adFitterService.updateById(adFitter);
        return R.ok().put("info", adFitter);
    }
    /**
     * 增加联系次数
     */
    @GetMapping("/count")
    @ApiOperation("增加联系次数")
    public R addCount(Long id,Long merchantId){
        AdFitterEntity adFitterEntity = adFitterService.getById(id);
        if(adFitterEntity==null){
            return R.error("数据异常");
        }
        adFitterEntity.setContactedNumber(adFitterEntity.getContactedNumber()+1);
        if(adFitterService.updateById(adFitterEntity)){
            TMerchantEntity tMerchantEntity= new TMerchantEntity();
            if(merchantId!=null){
                tMerchantEntity  = tMerchantService.getById(merchantId);
            }else{
                tMerchantEntity  = tMerchantService.getById(3395105745751040l);
            }
            if(tMerchantEntity==null){
                return R.error("数据异常");
            }
            tMerchantEntity.setFitterContactNumber(tMerchantEntity.getFitterContactNumber()+1);
            tMerchantService.updateById(tMerchantEntity);
            return R.ok();
        }
        return R.error("服务异常");
    }


}
