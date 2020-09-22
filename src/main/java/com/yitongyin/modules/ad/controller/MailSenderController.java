package com.yitongyin.modules.ad.controller;

import com.yitongyin.common.utils.EHCacheUtils;
import com.yitongyin.common.utils.R;
import com.yitongyin.common.utils.ReadHtml;
import com.yitongyin.modules.ad.entity.*;
import com.yitongyin.modules.ad.form.BusMaterialView;
import com.yitongyin.modules.ad.service.*;
import io.swagger.annotations.ApiOperation;

import javafx.scene.shape.TriangleMesh;
import net.sf.ehcache.CacheManager;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

/**
 * 邮件发送
 */
@RestController
@RequestMapping("ad/mail")
public class MailSenderController extends  AbstractController{

    @Autowired
    MailService mailService;
    @Autowired
    private CacheManager cacheManager;
    @Autowired
    private BusMaterialService busMaterialService;
    @Autowired
    private TMerchantService tMerchantService;
    @Autowired
    AdOssService adOssService;
    @Autowired
    private AdUserRoleService adUserRoleService;
    @Autowired
    private AdRoleService adRoleService;
    @Autowired
    private AdDesignerService adDesignerService;


    /**
     * 发送邮件
     */
    @ApiOperation("发送邮件")
    @RequestMapping(value = "/send", method = RequestMethod.POST)
    @RequiresPermissions("ad:mail:send")
    public R updateEmail(@RequestBody BusMaterialView view) {
         if(view.getId()==null)
             return R.error("id参数不能为空");
         Object object= EHCacheUtils.getCache(cacheManager,view.getId().toString()+getUserId().toString());
         if(object!=null){
             return  R.error("邮件发送频繁,请稍后重试");
         }else {
             BusMaterialEntity busMaterialEntity = busMaterialService.getById(view.getId());
             if(busMaterialEntity==null){
                 return  R.error("数据错误");
             }
             if(busMaterialEntity.getFileResId()!=null){
                 AdOssEntity ossEntity1 = adOssService.findById(busMaterialEntity.getFileResId());
                 view.setSourceUrl(ossEntity1==null?"":ossEntity1.getUrl());
             }
             String htmlBody = ReadHtml.reMailString("mail.html");
             String htmlText = htmlBody.replaceAll("\\{materialId\\}", view.getId().toString())
                     .replaceAll("\\{fileFormat\\}", view.getTypeStr())
                     .replaceAll("\\{materialName\\}", view.getTitle())
                     .replaceAll("\\{downloadUrl\\}", view.getSourceUrl())
                     .replaceAll("\\{materialPicture\\}", view.getImgUrl());
             System.out.println(htmlText);
             MailSenderModal mailSenderModal = mailService.sendMime(view.getToEmail(), "印下单素材", htmlText, null, false);
             if (mailSenderModal.getCode() != 0) {
                 return R.error(mailSenderModal.getCode(), mailSenderModal.getMsg());
             }
             busMaterialEntity.setId(view.getId());
             busMaterialEntity.setSendCounts(busMaterialEntity.getSendCounts()==null?1:busMaterialEntity.getSendCounts()+ 1);

             AdUserRoleEntity userRoleEntity = adUserRoleService.getByUserId(getUserId());
             AdRoleEntity roleEntity = adRoleService.getById(userRoleEntity.getRoleId());
             if(roleEntity.getRoleName().contains("商户")){
                 if(busMaterialService.updateById(busMaterialEntity)){
                     TMerchantEntity tMerchantEntity = tMerchantService.getInfoByUserId(getUserId());
                     tMerchantEntity.setMaterialSendNumber(tMerchantEntity.getMaterialSendNumber()+1);
                     tMerchantService.updateById(tMerchantEntity);
                 }
             }else if(roleEntity.getRoleName().contains("设计师")){
                 AdDesignerEntity designerEntity = adDesignerService.findByUserId(getUserId());
                 if(designerEntity!=null){
                     designerEntity.setMaterialSendNumber(designerEntity.getMaterialSendNumber()+1);
                     adDesignerService.updateById(designerEntity);
                 }
             }
             EHCacheUtils.setCache(cacheManager, view.getId().toString() + getUserId().toString(), getUser());
             return  R.ok();
         }
    }

}
