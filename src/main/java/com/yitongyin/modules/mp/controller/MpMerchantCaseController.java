package com.yitongyin.modules.mp.controller;

import com.yitongyin.common.utils.PageUtils;
import com.yitongyin.common.utils.R;
import com.yitongyin.common.utils.UtilString;
import com.yitongyin.modules.ad.controller.AbstractController;
import com.yitongyin.modules.ad.entity.AdMerchantCaseEntity;
import com.yitongyin.modules.ad.entity.AdMerchantProductEntity;
import com.yitongyin.modules.ad.entity.AdOssEntity;
import com.yitongyin.modules.ad.form.CaseConditions;
import com.yitongyin.modules.ad.form.CaseResult;
import com.yitongyin.modules.ad.service.AdMerchantCaseService;
import com.yitongyin.modules.ad.service.AdMerchantProductService;
import com.yitongyin.modules.ad.service.AdOssService;
import com.yitongyin.modules.mp.View.CaseInfo;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 商户案例表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-16 11:34:13
 */
@RestController
@RequestMapping("mp/case")
public class MpMerchantCaseController extends AbstractController{
    @Autowired
    private AdMerchantCaseService adMerchantCaseService;
    @Autowired
    private AdOssService adOssService;
    @Autowired
    private AdMerchantProductService adMerchantProductService;
    /**
     * 列表
     */
    @ApiOperation("商户案例列表")
    @PostMapping("/list")
    public R list(@RequestParam Map<String, Object> params, @RequestBody CaseConditions caseConditions,Long merchantId){
        PageUtils page = adMerchantCaseService.queryPage(params,caseConditions,merchantId);
        List<AdMerchantCaseEntity> caseEntities=(List<AdMerchantCaseEntity>) page.getList();
        List<CaseResult> caseResults=caseEntities.stream().map(adMerchantCaseEntity -> {
            CaseResult caseResult = new CaseResult();
            caseResult.setId(adMerchantCaseEntity.getId());
            caseResult.setContent(adMerchantCaseEntity.getContent());
            caseResult.setProName(adMerchantCaseEntity.getProductName());
            caseResult.setCreateTime(adMerchantCaseEntity.getCreateTime());
            if(StringUtils.isNotBlank(adMerchantCaseEntity.getAttachmentIds())){
                List<String> imgList= UtilString.stringToList(adMerchantCaseEntity.getAttachmentIds());
               if(imgList!=null){
                   AdOssEntity adOssEntity=adOssService.findById(Long.valueOf(imgList.get(0)));
                   caseResult.setImg(adOssEntity==null?null:adOssEntity.getUrl());
                   List<Long> longList=imgList.stream().map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
                   caseResult.setImgList(adOssService.findByIds(longList));
               }
            }
            return caseResult;
        }).collect(Collectors.toList());
        page.setList(caseResults);
        return R.ok().put("page", page);
    }
    /**
     * 根据产品Id查看更多案例
     */
    @ApiOperation("根据产品Id查看更多案例")
    @GetMapping("/listMore")
    @ResponseBody
    public R listMore(@RequestParam Map<String, Object> params,Long proId){
        AdMerchantProductEntity product=adMerchantProductService.getById(proId);
        if(product==null)
            return R.ok().put("page", null);
        PageUtils page =adMerchantCaseService.listByMerchantIdAndProId(params,product.getProductId(),product.getMerchantId());
        List<AdMerchantCaseEntity> cases=(List<AdMerchantCaseEntity>) page.getList();
        List<CaseInfo> caseInfos =cases.stream().map(entity -> {
             CaseInfo caseInfo=new CaseInfo();
             caseInfo.setContent(entity.getContent());
             caseInfo.setId(entity.getId());
             caseInfo.setTime(entity.getCreateTime());
            List<String> urls=new ArrayList<>();
             if(entity.getAttachmentIds()!=null&&!entity.getAttachmentIds().equals("")){
                 List<String> ids = UtilString.stringToList(entity.getAttachmentIds());
                 List<Long> longList=ids.stream().map(e->Long.valueOf(e)).collect(Collectors.toList());;
                 List<AdOssEntity> ossEntities=adOssService.findByIds(longList);
                 urls =ossEntities.stream().map(o->o.getUrl()).collect(Collectors.toList());
             }
            caseInfo.setPicUrls(urls);
             return  caseInfo;
        }).collect(Collectors.toList());
        page.setList(caseInfos);
        return R.ok().put("page", page);
    }
    /**
     * 案例增加点击量
     */
    @ApiOperation("案例增加点击量")
    @PostMapping("/addHits")
    public R list(Long id){
        AdMerchantCaseEntity entity = adMerchantCaseService.getById(id);
        if(entity==null) return R.error("参数错误");
        if(entity.getHits()==null){
            entity.setHits(0);
            adMerchantCaseService.updateById(entity);
            return R.ok();
        }else{
            entity.setHits(entity.getHits()+1);
            adMerchantCaseService.updateById(entity);
            return R.ok();
        }

    }

}
