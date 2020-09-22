package com.yitongyin.modules.ad.controller;

import com.yitongyin.common.utils.PageUtils;
import com.yitongyin.common.utils.R;
import com.yitongyin.modules.ad.entity.AdGroupEntity;
import com.yitongyin.modules.ad.entity.AdGroupProductRateEntity;
import com.yitongyin.modules.ad.entity.AdMerchantProductEntity;
import com.yitongyin.modules.ad.entity.TMerchantEntity;
import com.yitongyin.modules.ad.service.*;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


/**
 * 客户组表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-11-07 09:31:21
 */
@RestController
@RequestMapping("ad/group")
public class AdGroupController extends  AbstractController{
    @Autowired
    private AdGroupService adGroupService;
    @Autowired
    private TMerchantService tMerchantService;
    @Autowired
    private AdClientGroupService adClientGroupService;
    @Autowired
    private AdGroupProductRateService adGroupProductRateService;
    @Autowired
    private AdMerchantProductService adMerchantProductService;

    /**
     * 列表
     */
    @GetMapping("/list")
    @ApiOperation("客户等级列表")
    public R list(){
        TMerchantEntity tMerchantEntity = tMerchantService.getInfoByUserId(getUserId());
        if(tMerchantEntity==null)return R.error("数据异常");
        List<AdGroupEntity> list =adGroupService.getClientByMerchantId(tMerchantEntity.getMerchantid());
        return R.ok().put("list", list);
    }


    /**
     * 信息
     */
    @GetMapping("/info/{groupId}")
    @ApiOperation("等级详情")
    public R info(@PathVariable("groupId") Integer groupId){
        AdGroupEntity adGroup = adGroupService.getById(groupId);
        return R.ok().put("info", adGroup);
    }

    /**
     * 添加等级
     */
    @PostMapping("/save")
    @ApiOperation("添加等级")
    public R save(@RequestBody AdGroupEntity adGroup){
        TMerchantEntity tMerchantEntity = tMerchantService.getInfoByUserId(getUserId());
        if(tMerchantEntity==null)return R.error("数据异常");
        AdGroupEntity entity = adGroupService.getOneByName(adGroup.getName(),tMerchantEntity.getMerchantid());
        if(entity!=null)return R.error("名称已存在");
        adGroup.setMerchantId(tMerchantEntity.getMerchantid());
        if(adGroupService.save(adGroup)){
            adGroupProductRateService.saveListByAdGroupAndMerchantId(adGroup,tMerchantEntity.getMerchantid());
        }

        return R.ok();
    }
    /**
     * 编辑
     */
    @PostMapping("/update")
    @ApiOperation("编辑")
    public R update(@RequestBody AdGroupEntity adGroup){
        TMerchantEntity tMerchantEntity = tMerchantService.getInfoByUserId(getUserId());
        if(tMerchantEntity==null)return R.error("数据异常");
        if(adGroup.getPriceRate()==null){
            return R.error("价格倍数不能为空");
        }
        if(adGroupService.updateById(adGroup)){
            adGroupProductRateService.saveListByAdGroupAndMerchantId(adGroup,tMerchantEntity.getMerchantid());
        }
        return R.ok();
    }

    /**
     * 开关是否显示价格
     */
    @PostMapping("/isShow")
    @ApiOperation("开关是否显示价格")
    public R isShow(@RequestBody AdGroupEntity adGroup){
        adGroupService.updateById(adGroup);

        return R.ok();
    }

    /**
     * 删除
     */
    @PostMapping("/delete")
    @ApiOperation("删除")
    public R delete(Integer id){
        if(adClientGroupService.getOneByGroupId(id)!=null)return R.error("该等级下已存在用户");
        if(adGroupService.deleteGroupAndProRateByGroupId(id)){
            return R.ok();
        }
        return R.error();
    }
    /**
     * 搜索下拉
     */
    @PostMapping("/listName")
    @ApiOperation("搜索下拉列表")
    public R getSearchList(){
        TMerchantEntity tMerchantEntity = tMerchantService.getInfoByUserId(getUserId());
        if(tMerchantEntity==null)return R.error("数据异常");
        List<AdGroupEntity> list = adGroupService.getListByMerchantId(tMerchantEntity.getMerchantid());
        return R.ok().put("list",list);
    }
    /**
     * 获取产品列表带价格倍数
     */
    @ApiOperation("获取产品列表带价格倍数")
    @RequestMapping(value = "/product/list", method = RequestMethod.GET)
    @ResponseBody
    public R proList(@RequestParam Map<String, Object> params,Long groupId,String name,Long typeId){
        TMerchantEntity merchantEntity=tMerchantService.getInfoByUserId(getUserId());
        PageUtils page = adMerchantProductService.queryRatePage(params,merchantEntity,groupId,name,typeId);
        return R.ok().put("page", page);
    }

}
