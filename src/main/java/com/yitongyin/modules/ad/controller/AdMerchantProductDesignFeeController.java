package com.yitongyin.modules.ad.controller;

import com.yitongyin.common.utils.R;
import com.yitongyin.modules.ad.entity.AdMerchantProductDesignFeeEntity;
import com.yitongyin.modules.ad.entity.TMerchantEntity;
import com.yitongyin.modules.ad.service.AdMerchantProductDesignFeeService;
import com.yitongyin.modules.ad.service.TMerchantService;
import com.yitongyin.modules.mp.View.DesignFee;
import io.swagger.annotations.ApiOperation;
import org.apache.lucene.search.Collector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;


/**
 * 商户产品设计价格表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-09-07 16:53:52
 */
@RestController
@RequestMapping("ad/designfee")
public class AdMerchantProductDesignFeeController extends AbstractController{
    @Autowired
    private AdMerchantProductDesignFeeService adMerchantProductDesignFeeService;
    @Autowired
    private TMerchantService tMerchantService;

    /**
     * 列表
     */
    @ApiOperation("设计费列表")
    @GetMapping("/list")
    public R list(Long proId){
        TMerchantEntity tMerchantEntity = tMerchantService.getInfoByUserId(getUserId());
        return R.ok().put("list",adMerchantProductDesignFeeService.getListByProId(tMerchantEntity.getMerchantid(),proId));
    }


    /**
     * 信息
     */
    @ApiOperation("设计费详情")
    @GetMapping("/info")
    public R info( Long id){
		AdMerchantProductDesignFeeEntity adMerchantProductDesignFee = adMerchantProductDesignFeeService.getById(id);
        return R.ok().put("info", adMerchantProductDesignFee);
    }

    /**
     * 保存
     */
    @ApiOperation("保存设计费")
    @PostMapping("/save")
    public R save(@RequestBody List<AdMerchantProductDesignFeeEntity> list){
        TMerchantEntity tMerchantEntity = tMerchantService.getInfoByUserId(getUserId());
        list.forEach( e -> {
            e.setMerchantId(tMerchantEntity.getMerchantid());
            if(e.getId()==null){
                adMerchantProductDesignFeeService.save(e);
            }else{
                adMerchantProductDesignFeeService.updateById(e);
            }
        } );
        return R.ok();
    }


    /**
     * 删除
     */
    @ApiOperation("删除设计费")
    @GetMapping("/delete")
    public R delete(Long id){
		adMerchantProductDesignFeeService.removeById(id);
        return R.ok();
    }
    /**
     * 开启/关闭
     */
    @ApiOperation("开启/关闭")
    @GetMapping("/updSta")
    public R updSta(Long id,Integer status){
        TMerchantEntity tMerchantEntity = tMerchantService.getInfoByUserId(getUserId());
        adMerchantProductDesignFeeService.updStatus(tMerchantEntity.getMerchantid(),id,status);
        return R.ok();
    }
    /**
     * 获取状态
     */
    @ApiOperation("获取状态")
    @GetMapping("/getSta")
    public R getSta(Long id){
        TMerchantEntity tMerchantEntity = tMerchantService.getInfoByUserId(getUserId());
        AdMerchantProductDesignFeeEntity entity = adMerchantProductDesignFeeService.getOneByProId(tMerchantEntity.getMerchantid(),id);
        if(entity==null){
            return R.ok().put("status",0);
        }
        return R.ok().put("status",entity.getShowAble());
    }
    /**
     * 获取后台产品设计费配置
     */
    @ApiOperation("获取后台产品设计费配置")
    @GetMapping("/synDesign")
    public R getSerConfig(Long proId){
        List<DesignFee> list =adMerchantProductDesignFeeService.getServiceConfigByProId(proId);
        if(list==null||list.size()==0){
            return R.error("官网无数据,无法同步,");
        }
        TMerchantEntity tMerchantEntity = tMerchantService.getInfoByUserId(getUserId());
        List<AdMerchantProductDesignFeeEntity> relList=list.stream().map(e->{
            AdMerchantProductDesignFeeEntity entity = new AdMerchantProductDesignFeeEntity();
            entity.setDesignFeeName(e.getFeeName());
            entity.setDesignFee(e.getFeePrice());
            entity.setAutoInclude(e.getAutoInclude());
            entity.setFeeNote(e.getFeeNote());
            entity.setMerchantProductId(proId);
            entity.setMerchantId(tMerchantEntity.getMerchantid());
            entity.setFeeDay(e.getFeeDay());
            return entity;
        }).collect(Collectors.toList());
       if(adMerchantProductDesignFeeService.removeAll(tMerchantEntity.getMerchantid(),proId)){
            adMerchantProductDesignFeeService.saveBatch(relList);
        }
       return R.ok().put("list",relList);
    }

}
