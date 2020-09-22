package com.yitongyin.modules.mp.controller;


import com.yitongyin.common.utils.PageUtils;
import com.yitongyin.common.utils.R;
import com.yitongyin.modules.ad.controller.AbstractController;
import com.yitongyin.modules.ad.entity.AdMerchantServiceTypeEntity;
import com.yitongyin.modules.ad.entity.TMerchantEntity;
import com.yitongyin.modules.ad.form.ProTypeAndChild;
import com.yitongyin.modules.ad.service.AdMerchantServiceTypeService;
import com.yitongyin.modules.ad.service.TServiceTypeService;
import com.yitongyin.modules.mp.View.PriceAndDays;
import com.yitongyin.modules.mp.View.ProductType;
import com.yitongyin.modules.mp.View.TypeAndPro;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


/**
 * 商户分类表
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2019-07-10 09:58:57
 */
@RestController
@RequestMapping("mp/proCat")
public class MpMerchantServiceTypeController extends  AbstractController{
    @Autowired
    private AdMerchantServiceTypeService adMerchantServiceTypeService;
    @Autowired
    private TServiceTypeService tServiceTypeService;


    /**
     * 获取产品分类
     */
    @ApiOperation("获取产品分类")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public R list(Long id){
        if(id == null|| id == 0) {
            return  R.error("无效的参数");
        }
         List<AdMerchantServiceTypeEntity> types =adMerchantServiceTypeService.getByMerchantId(id);
         return R.ok().put("list",types);
    }
    /**
     * 获取首页分类及其属热门产品
     */
    @ApiOperation("获取首页分类及其属热门产品")
    @RequestMapping(value = "/listPopular", method = RequestMethod.GET)
    public R listAndProList(@RequestParam Map<String, Object> params,Long id){
        if(id == null|| id == 0) {
            return  R.error("无效的参数");
        }
        PageUtils page =adMerchantServiceTypeService.getProByPopular(params,id);
        return R.ok().put("page",page);
    }
    /**
     * 根据一级分类分页获取其下所有产品(除了名片获取的是二级分类)
     */
    @ApiOperation("根据一级分类分页获取其下所有产品(除了名片获取的是二级分类)")
    @RequestMapping(value = "/proListByTypeId", method = RequestMethod.POST)
    public R proListByTypeId(@RequestParam Map<String, Object> params,Long typeId){
//        if(id == null|| id == 0) {
//            return  R.error("无效的参数");
//        }
        AdMerchantServiceTypeEntity type =adMerchantServiceTypeService.getById(typeId);
        if(type==null)
            return R.ok().put("page",null);
        PageUtils page =adMerchantServiceTypeService.getProByTypeId(params,type.getMerchantId(),type.getServiceTypeId());
        return R.ok().put("page",page);
    }

    /**
     * 根据一级分类ID获取二级分类列表页
     */
    @ApiOperation("二级分类列表页")
    @RequestMapping(value = "/listChild", method = RequestMethod.GET)
    public R listChild(Long typeId){
        AdMerchantServiceTypeEntity adMerchantServiceTypeEntity =adMerchantServiceTypeService.getById(typeId);
        if(adMerchantServiceTypeEntity==null)
            return R.error("数据错误");
        List<ProductType> types =adMerchantServiceTypeService.getChildList(
                adMerchantServiceTypeEntity.getServiceTypeId(),adMerchantServiceTypeEntity.getMerchantId());
        return R.ok().put("list",types);
    }
    /**
     * 获取产品分类名称
     */
    @ApiOperation("获取产品分类名称")
    @RequestMapping(value = "/list/Name", method = RequestMethod.GET)
    @ResponseBody
    public R getTypeName(Long merchantId) {

        List<AdMerchantServiceTypeEntity> list= adMerchantServiceTypeService.getMpShowType(merchantId);
        List<ProTypeAndChild> proTypeAndChildList =new ArrayList<>();
        for (AdMerchantServiceTypeEntity type: list) {
            if(type.getParentTypeId()==1){
                ProTypeAndChild proTypeAndChild = new ProTypeAndChild();
                proTypeAndChild.setServiceType(type.getServiceTypeId());
                proTypeAndChild.setName(type.getTypeName());
                proTypeAndChild.setTypeId(type.getTypeid());
                proTypeAndChildList.add(proTypeAndChild);
            }
        }
        for (ProTypeAndChild typeAndChild1: proTypeAndChildList) {
            List<ProTypeAndChild> proTypeAndChildList1 =new ArrayList<>();
            for (AdMerchantServiceTypeEntity type: list) {
                if(type.getParentTypeId()!=1&&type.getParentTypeId().equals(typeAndChild1.getServiceType())){
                    ProTypeAndChild proTypeAndChild = new ProTypeAndChild();
                    proTypeAndChild.setServiceType(type.getServiceTypeId());
                    proTypeAndChild.setName(type.getTypeName());
                    proTypeAndChild.setTypeId(type.getTypeid());
                    proTypeAndChildList1.add(proTypeAndChild);
                }
            }
            typeAndChild1.setChildList(proTypeAndChildList1);
        }
        return R.ok().put("list",proTypeAndChildList);
    }
    /**
     * 获取产品分类详情
     */
    @ApiOperation("获取产品分类详情")
    @RequestMapping(value = "/list/info", method = RequestMethod.GET)
    @ResponseBody
    public R getInfo(Long typeId) {
        AdMerchantServiceTypeEntity typeEntity =adMerchantServiceTypeService.getById(typeId);
        typeEntity.setTypeName(tServiceTypeService.getById(typeEntity.getServiceTypeId()).getServicename());
        return R.ok().put("info",typeEntity);
    }
//    /**
//     * 获取所有最底级分类
//     */
//    @ApiOperation("获取所有最底级分类")
//    @RequestMapping(value = "/listNoChild", method = RequestMethod.GET)
//    public R listNoChild(Long id){
//        if(id == null|| id == 0) {
//            return  R.error("无效的参数。");
//        }
//        List<ProductType> types =adMerchantServiceTypeService.getListNoChild(id);
//        return R.ok().put("list",types);
//    }
//    /**
//     * 获取所有有子类的分类
//     */
//    @ApiOperation("获取所有有子类的分类")
//    @RequestMapping(value = "/listHaveChild", method = RequestMethod.GET)
//    public R listHaveChild(Long id){
//        if(id == null|| id == 0) {
//            return  R.error("无效的参数。");
//        }
//        List<ProductType> types =adMerchantServiceTypeService.getListHaveChild(id);
//        return R.ok().put("list",types);
//    }
//    /**
//     * 根据分类Id及条件获取其下子产品
//     */
//    @ApiOperation("根据分类Id及条件获取其下子产品")
//    @RequestMapping(value = "/listChildAndCondition", method = RequestMethod.POST)
//    public R listChildByCondition(Long id, Long typeId, PriceAndDays priceAndDays){
//        if(id == null|| id == 0) {
//            return  R.error("无效的参数。");
//        }
//        if(typeId == null|| typeId == 0) {
//            return  R.error("无效的参数");
//        }
//        List<ProductType> types =adMerchantServiceTypeService.getChildListByConditions(typeId,id,priceAndDays);
//        return R.ok().put("list",types);
//    }

}
