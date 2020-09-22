package com.yitongyin.modules.mp.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.yitongyin.common.annotion.Login;
import com.yitongyin.common.annotion.LoginUser;
import com.yitongyin.common.exception.RRException;
import com.yitongyin.common.utils.PageUtils;
import com.yitongyin.common.utils.R;
import com.yitongyin.common.utils.UtilString;
import com.yitongyin.modules.ad.entity.*;
import com.yitongyin.modules.ad.form.SpecKeyOrder;
import com.yitongyin.modules.ad.service.*;
import com.yitongyin.modules.mp.View.ClientCollectResult;
import com.yitongyin.modules.mp.View.SpecCollectKeyView;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.Collectors;


/**
 * 客户产品方案收藏记录表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-07 14:44:04
 */
@RestController
@RequestMapping("smp/speccollect")
public class MpClientSpecCollectRecordController {
    @Autowired
    private AdClientSpecCollectRecordService adClientSpecCollectRecordService;
    @Autowired
    private AdMerchantSpecGroupService adMerchantSpecGroupService;
    @Autowired
    private AdMerchantProductService adMerchantProductService;
    @Autowired
    private BusProductService busProductService;
    @Autowired
    private AdOssService adOssService;
    @Autowired
    private AdMerchantProductDesignFeeService adMerchantProductDesignFeeService;
    @Autowired
    private BusSpecValueService busSpecValueService;
    @Autowired
    private AdGroupProductRateService adGroupProductRateService;

    /**
     * 列表
     */
    @Login
    @GetMapping("/list")
    @ApiOperation("方案管理列表")
    public R list(@RequestParam Map<String, Object> params, Long merchantId, @LoginUser AdClientEntity user){
        PageUtils page = adClientSpecCollectRecordService.queryPage(params,user.getClientid(),merchantId);
        List<AdClientSpecCollectRecordEntity> list =(List<AdClientSpecCollectRecordEntity>) page.getList();
        for (AdClientSpecCollectRecordEntity entity:list) {
            AdMerchantProductEntity productEntity = adMerchantProductService.getById(entity.getMerchantProductId());
            SpecCollectKeyView keyView = new SpecCollectKeyView();
            String jsonView= entity.getProductSpecValueJson();
            keyView= JSONObject.parseObject(jsonView,SpecCollectKeyView.class);
            entity.setHeight(keyView.getHeight());
            entity.setWidth(keyView.getWidth());
            Long designFeeId=keyView.getDesignFeeId();
            if(designFeeId!=null){
                AdMerchantProductDesignFeeEntity feeEntity = adMerchantProductDesignFeeService.getById(designFeeId);
                if(feeEntity!=null){
                    entity.setDesignPrice(feeEntity.getDesignFee());
                    entity.setDesignFeeName(feeEntity.getDesignFeeName());
                    entity.setFeeDay(feeEntity.getFeeDay());
                }
            }
            List<String> valueIds= new ArrayList<>();
            Collection<BusSpecValueEntity> busValues= busSpecValueService.listByIds(keyView.getSpecValueIds());
            List<String> valueNames=busValues.stream().map(e->e.getValName()).collect(Collectors.toList());
            entity.setValueNames(valueNames);
            try{
                Collections.sort(keyView.getSpecValueIds());
                valueIds = keyView.getSpecValueIds().stream().map(e->e.toString()).collect(Collectors.toList());
            }catch (Exception e){
                throw new RRException("数据异常");
            }

            String strValueIds= UtilString.listToString(valueIds);
            AdMerchantSpecGroupEntity groupEntity = adMerchantSpecGroupService.getLowestPriceByMerAndPro(productEntity.getMerchantId(),productEntity.getProductId());
            List<ClientCollectResult> clientCollectResults = adMerchantSpecGroupService.getMpClientCollectByConditions(groupEntity.getMerchantId()
                    ,groupEntity.getProductId(),groupEntity.getSupplierId(),strValueIds);
            if(clientCollectResults!=null&&clientCollectResults.size()!=0){
                entity.setPrice(clientCollectResults.get(0).getPrice());
                entity.setDays(clientCollectResults.get(0).getDeliveryDay());
                entity.setUrl(clientCollectResults.get(0).getUrl());
                entity.setIsUse(1);
                if(entity.getPrice()!=null){
                    if(entity.getWidth()!=null&&entity.getHeight()!=null){
                        entity.setPrice(entity.getPrice().multiply(entity.getWidth().multiply(entity.getHeight())));
                    }

                    AdGroupProductRateEntity adGroupProductRateEntity = adGroupProductRateService.getRateByClientIdAndMerchantProcutId(user.getClientid()
                            ,entity.getMerchantProductId(),merchantId);
                    if(adGroupProductRateEntity!=null){
                        if(adGroupProductRateEntity.getIsShow()==1){
                            entity.setPrice(entity.getPrice().multiply(adGroupProductRateEntity.getPriceRate()));
                        }else{
                            entity.setPrice(null);
                        }
                    }
                    if(entity.getDesignPrice()!=null){
                        entity.setPrice(entity.getPrice().add(entity.getDesignPrice()));
                    }
                }
            }else{
                entity.setIsUse(0);
            }
            BusProductEntity busProductEntity = busProductService.getSomePropertyById(groupEntity.getProductId());
            if(busProductEntity!=null){
                entity.setProName(busProductEntity.getProductname());
                if(entity.getUrl()==null){
                    AdOssEntity ossEntity = adOssService.getById(busProductEntity.getOssid());
                    entity.setUrl(ossEntity==null?null:ossEntity.getUrl());
                }
            }
        }
        return R.ok().put("page", page);
    }


    /**
     * 复制方案
     */
    @Login
    @PostMapping("/save")
    @ApiOperation("添加方案")
    public R list(@RequestBody SpecCollectKeyView view, @LoginUser AdClientEntity user){
        Collections.sort(view.getSpecValueIds());
        AdClientSpecCollectRecordEntity entity = new AdClientSpecCollectRecordEntity();
        entity.setCreateTime(new Date());
        entity.setClientId(user.getClientid());
        entity.setMerchantProductId(view.getMerchantProductId());
        view.setMerchantProductId(null);
        String json= JSONObject.toJSONString(view);
        entity.setProductSpecValueJson(json);
        adClientSpecCollectRecordService.save(entity);
        return R.ok();
    }
    /**
     * 是否收藏
     */
    @Login
    @PostMapping("/isCollect")
    @ApiOperation("是否收藏")
    public R isCollect(@RequestBody SpecCollectKeyView view, @LoginUser AdClientEntity user){
        Collections.sort(view.getSpecValueIds());
        AdClientSpecCollectRecordEntity entity = new AdClientSpecCollectRecordEntity();
        entity.setClientId(user.getClientid());
        entity.setMerchantProductId(view.getMerchantProductId());
        view.setMerchantProductId(null);
        String json= JSONObject.toJSONString(view);
        entity.setProductSpecValueJson(json);
        AdClientSpecCollectRecordEntity search= adClientSpecCollectRecordService.getByEntity(entity);
        if(search!=null){
           return R.ok().put("isCollect",true);
        }else{
            return R.ok().put("isCollect",false);
        }
    }
    /**
     * 删除
     */
    @Login
    @PostMapping("/delete")
    @ApiOperation("删除")
    public R del(@RequestBody List<Long> ids){
        adClientSpecCollectRecordService.removeByIds(ids);
        return R.ok();
    }



}
