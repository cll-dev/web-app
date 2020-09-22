package com.yitongyin.modules.ad.controller;

import com.yitongyin.common.utils.PageUtils;
import com.yitongyin.common.utils.R;
import com.yitongyin.common.utils.UtilString;
import com.yitongyin.modules.ad.entity.*;
import com.yitongyin.modules.ad.form.CaseConditions;
import com.yitongyin.modules.ad.form.CaseForm;
import com.yitongyin.modules.ad.form.CaseResult;
import com.yitongyin.modules.ad.form.CaseSubmit;
import com.yitongyin.modules.ad.service.*;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
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
@RequestMapping("ad/case")
public class AdMerchantCaseController extends AbstractController{
    @Autowired
    private AdMerchantCaseService adMerchantCaseService;
    @Autowired
    private AdOssService adOssService;
    @Autowired
    private TMerchantService tMerchantService;
    @Autowired
    private BusProductCommentService busProductCommentService;
    @Autowired
    private BusProductService busProductService;

    /**
     * 列表
     */
    @ApiOperation("商户案例列表")
    @PostMapping("/list")
    public R list(@RequestParam Map<String, Object> params, @RequestBody CaseConditions caseConditions){
        TMerchantEntity tMerchantEntity =tMerchantService.getInfoByUserId(getUserId());
        PageUtils page = adMerchantCaseService.queryPage(params,caseConditions,tMerchantEntity.getMerchantid());
        List<AdMerchantCaseEntity> caseEntities=(List<AdMerchantCaseEntity>) page.getList();
        List<CaseResult> caseResults=caseEntities.stream().map(adMerchantCaseEntity -> {
             CaseResult caseResult = new CaseResult();
             caseResult.setId(adMerchantCaseEntity.getId());
             caseResult.setContent(adMerchantCaseEntity.getContent());
             caseResult.setProName(adMerchantCaseEntity.getProductName());
             caseResult.setCreateTime(adMerchantCaseEntity.getCreateTime());
            if(StringUtils.isNotBlank(adMerchantCaseEntity.getAttachmentIds())){
                List<String> imgList= UtilString.stringToList(adMerchantCaseEntity.getAttachmentIds());
                AdOssEntity adOssEntity=adOssService.findById(Long.valueOf(imgList.get(0)));
                caseResult.setImg(adOssEntity==null?null:adOssEntity.getUrl());
                List<Long> longList=imgList.stream().map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
                caseResult.setImgList(adOssService.findByIds(longList));
            }
             return caseResult;
        }).collect(Collectors.toList());
        page.setList(caseResults);
        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @ApiOperation("商户案例信息")
    @GetMapping("/info")
    @ResponseBody
    public R info(Long id){
		AdMerchantCaseEntity adMerchantCase = adMerchantCaseService.getById(id);
		if(adMerchantCase==null)
		    return R.error("数据异常");
        CaseResult caseResult=new CaseResult();
        BusProductEntity busProductEntity =busProductService.getSomePropertyById(adMerchantCase.getProductId());
        caseResult.setProName(busProductEntity==null?null:busProductEntity.getProductname());
        caseResult.setContent(adMerchantCase.getContent());
        caseResult.setProId(adMerchantCase.getProductId());
        if(StringUtils.isNotBlank(adMerchantCase.getAttachmentIds())){
            List<String> imgList= UtilString.stringToList(adMerchantCase.getAttachmentIds());
            List<Long> longList=imgList.stream().map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
            caseResult.setImgList(adOssService.findByIds(longList));
        }
        return R.ok().put("adMerchantCase", caseResult);
    }

    /**
     * 保存
     */
    @ApiOperation("商户案例保存")
    @PostMapping("/save")
    public R save(@RequestBody CaseSubmit caseSubmit){
		AdMerchantCaseEntity caseEntity= new AdMerchantCaseEntity();
		caseEntity.setMerchantId(tMerchantService.getInfoByUserId(getUserId()).getMerchantid());
		caseEntity.setProductId(caseSubmit.getProId());
		BusProductEntity productEntity=busProductService.getSomePropertyById(caseSubmit.getProId());
		caseEntity.setServiceTypeId(productEntity==null?0:productEntity.getServicetypeid());
		caseEntity.setContent(caseSubmit.getContent());
		if(caseSubmit.getImgIds()!=null){
            String img=UtilString.listToString(caseSubmit.getImgIds());
            caseEntity.setAttachmentIds(img);
        }
		caseEntity.setCreateTime(new Date());
		caseEntity.setHits(0);
		adMerchantCaseService.save(caseEntity);
		if(caseSubmit.getImgIds()!=null){
            List<Long> longList=caseSubmit.getImgIds().stream().map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
            adOssService.updateStatusByIds(longList,1);
        }
        return R.ok();
    }

    /**
     * 修改
     */
    @ApiOperation("商户案例修改")
    @PostMapping("/update")
    public R update(@RequestBody  CaseForm caseForm){
        AdMerchantCaseEntity caseEntity= new AdMerchantCaseEntity();
        caseEntity.setId(caseForm.getId());
        caseEntity.setContent(caseForm.getContent());
        AdMerchantCaseEntity oldCase=adMerchantCaseService.getById(caseForm.getId());
        List<String> stringList=caseForm.getImgList();
        if(caseForm.getImgList()!=null){
            String img=UtilString.listToString(stringList,",");
            caseEntity.setAttachmentIds(img);
        }
        caseEntity.setUpdateTime(new Date());
        caseEntity.setProductId(caseForm.getProId());
		adMerchantCaseService.updateById(caseEntity);
		if(oldCase.getAttachmentIds()!=null){
            List<String> imgList= UtilString.stringToList(oldCase.getAttachmentIds());
            List<Long> longPics=imgList.stream().map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
            adOssService.updateStatusByIds(longPics,2);
        }
		if(caseForm.getImgList()!=null){
            List<Long> longList=caseForm.getImgList().stream().map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
            adOssService.updateStatusByIds(longList,1);
        }
        return R.ok();
    }

    /**
     * 删除
     */
    @ApiOperation("商户案例删除")
    @PostMapping("/delete")
    public R delete(@RequestBody List<Long> ids){
        System.out.println(ids);
        List<AdMerchantCaseEntity> entitys=adMerchantCaseService.getOssIdsByIds(ids);
		adMerchantCaseService.removeByIds(ids);
        for (AdMerchantCaseEntity entity: entitys) {
            if(entity.getAttachmentIds()!=null){
                List<String> imgList= UtilString.stringToList(entity.getAttachmentIds());
                List<Long> longPics=imgList.stream().map(s -> Long.parseLong(s.trim())).collect(Collectors.toList());
                adOssService.updateStatusByIds(longPics,2);
            }
        }

        return R.ok();
    }
    /**
     * 同步平台案例
     */
    @ApiOperation("商户案例同步")
    @PostMapping("/syn")
    @ResponseBody
    public R synCase( ){
        Long merchantId=tMerchantService.getInfoByUserId(getUserId()).getMerchantid();
        List<BusProductCommentEntity> busProductCommentEntities= busProductCommentService.getList();
        List<AdMerchantCaseEntity> adMerchantCaseEntities=busProductCommentEntities.stream().map(bean -> {
            AdMerchantCaseEntity entity=new AdMerchantCaseEntity();
            BusProductEntity busProductEntity =busProductService.getSomePropertyById(bean.getProductid());
            entity.setMerchantId(merchantId);
            entity.setServiceTypeId(busProductEntity==null?0:busProductEntity.getServicetypeid());
            entity.setProductId(bean.getProductid());
            entity.setAttachmentIds(bean.getOssids());
            entity.setHits(0);
            entity.setContent(bean.getContent());
            entity.setCreateTime(new Date());
            entity.setCommentid(bean.getCommentid());
            return  entity;
        }).collect(Collectors.toList());
       // adMerchantCaseEntities.removeAll(Collections.singleton(null));
        if(adMerchantCaseService.synList(adMerchantCaseEntities,merchantId)){
            return R.ok();
        }
        return R.error("同步失败");
    }

}
