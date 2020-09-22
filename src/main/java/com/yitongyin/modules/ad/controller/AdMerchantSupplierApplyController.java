package com.yitongyin.modules.ad.controller;

import com.yitongyin.common.annotion.DuplicateSubmitToken;
import com.yitongyin.common.utils.R;
import com.yitongyin.common.validator.ValidatorUtils;
import com.yitongyin.modules.ad.entity.AdMerchantSupplierApplyEntity;
import com.yitongyin.modules.ad.entity.BusSupplierEntity;
import com.yitongyin.modules.ad.entity.TMerchantEntity;
import com.yitongyin.modules.ad.service.AdMerchantSupplierApplyService;
import com.yitongyin.modules.ad.service.BusSupplierService;
import com.yitongyin.modules.ad.service.TMerchantService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;



/**
 * 商户对应厂家信息的申请表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-08-27 14:35:52
 */
@RestController
@RequestMapping("ad/supplierApply")
public class AdMerchantSupplierApplyController extends  AbstractController{
    @Autowired
    private AdMerchantSupplierApplyService adMerchantSupplierApplyService;
    @Autowired
    private TMerchantService tMerchantService;
    @Autowired
    BusSupplierService busSupplierService;

    /**
     * 提交厂家
     */
    @ApiOperation("提交厂家")
    @DuplicateSubmitToken
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public R save(@RequestBody AdMerchantSupplierApplyEntity adMerchantSupplierApply, HttpServletRequest request) throws  InterruptedException{
        TMerchantEntity tMerchantEntity= tMerchantService.getInfoByUserId(getUserId());
        if(tMerchantEntity==null)
            return R.error("商户不存在");

        AdMerchantSupplierApplyEntity existMerchantSupplier = adMerchantSupplierApplyService.findByName(adMerchantSupplierApply.getSupplierName());
        if(existMerchantSupplier!=null){
            return R.error("申请的厂家名称已存在，不能重复提交");
        }

        BusSupplierEntity existSupplier = busSupplierService.findByName(adMerchantSupplierApply.getSupplierName());
        if(existSupplier!= null){
            return R.error("该厂家名称已存在，不能重复申请");
        }

        adMerchantSupplierApply.setMerchantId(tMerchantEntity.getMerchantid());
        adMerchantSupplierApply.setApplyState(0);
        adMerchantSupplierApply.setCreateTime(new Date());
        ValidatorUtils.validateEntity(adMerchantSupplierApply);
		adMerchantSupplierApplyService.save(adMerchantSupplierApply);
        return R.ok();
    }


}
