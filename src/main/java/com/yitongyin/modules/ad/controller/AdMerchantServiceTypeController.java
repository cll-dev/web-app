package com.yitongyin.modules.ad.controller;


import com.yitongyin.common.utils.PageUtils;
import com.yitongyin.common.utils.R;
import com.yitongyin.config.ManagerConfig;
import com.yitongyin.modules.ad.entity.AdMerchantServiceTypeEntity;
import com.yitongyin.modules.ad.entity.TMerchantEntity;
import com.yitongyin.modules.ad.form.ProTypeAndChild;
import com.yitongyin.modules.ad.form.ServiceType;
import com.yitongyin.modules.ad.inti.SpringContextSupport;
import com.yitongyin.modules.ad.service.*;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
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
@RequestMapping("ad/proCat")
public class AdMerchantServiceTypeController extends  AbstractController{
    @Autowired
    private AdMerchantServiceTypeService adMerchantServiceTypeService;
    @Autowired
    private BusProductService busProductService;
    @Autowired
    private TMerchantService tMerchantService;
    @Autowired
    private TServiceTypeService tServiceTypeService;
    @Autowired
    private BusSupplierProductService busSupplierProductService;

    /**
     * 获取产品分类
     */
    @ApiOperation("获取产品分类")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public R list(@RequestParam Map<String, Object> params){
        ManagerConfig managerConfig =SpringContextSupport.getBean(ManagerConfig.class);
        TMerchantEntity tMerchantEntity = tMerchantService.getInfoByUserId(getUserId());
        if(tMerchantEntity == null){
            return R.error("用户不存在");
        }
        PageUtils page = adMerchantServiceTypeService.queryPage(params,tMerchantEntity.getMerchantid());
        List<AdMerchantServiceTypeEntity> list=(List<AdMerchantServiceTypeEntity>)page.getList();
        List<ServiceType> typeList= new ArrayList<>();
        for ( AdMerchantServiceTypeEntity adMerchantServiceTypeEntity: list){
            ServiceType serviceType= new ServiceType();
            serviceType.setParentTypeId(adMerchantServiceTypeEntity.getParentTypeId());
            serviceType.setTypeId(adMerchantServiceTypeEntity.getTypeid());
            serviceType.setServiceTypeId(adMerchantServiceTypeEntity.getServiceTypeId());
            serviceType.setStatus(adMerchantServiceTypeEntity.getMpShow());
            serviceType.setName(adMerchantServiceTypeEntity.getTypeName());
            serviceType.setOrder(adMerchantServiceTypeEntity.getOrderNumber());
            serviceType.setIntro(adMerchantServiceTypeEntity.getDescr());
            serviceType.setChildNumber(adMerchantServiceTypeEntity.getChildNumber());
            if(StringUtils.isNotBlank(adMerchantServiceTypeEntity.getIconPath())){
                serviceType.setCoverUrl(managerConfig.getUrl()+adMerchantServiceTypeEntity.getIconPath());
            }

            if(adMerchantServiceTypeEntity.getChildNumber()!=null&&adMerchantServiceTypeEntity.getChildNumber()>0){
                List<Object> objects=tServiceTypeService.getChilds(adMerchantServiceTypeEntity.getServiceTypeId());
                serviceType.setProCount((busProductService.getCountByChildTypes(objects,tMerchantEntity.getMerchantid())));
//                String ids= UtilString.objListToString(objects,".....");
                serviceType.setSupplierCount(busSupplierProductService.getCountByServiceType(objects));
            }else{
                serviceType.setProCount(busProductService.getCountByType(adMerchantServiceTypeEntity.getServiceTypeId(),tMerchantEntity.getMerchantid()));
                serviceType.setSupplierCount(busSupplierProductService.getCountBySingleServiceType(adMerchantServiceTypeEntity.getServiceTypeId().toString()));
             }
            typeList.add(serviceType);

        }
        page.setList(typeList);
        return R.ok().put("page", page);
    }
    /**
     * 获取二级产品分类
     */
    @ApiOperation("获取二级产品分类")
    @RequestMapping(value = "/listChild", method = RequestMethod.GET)
    @ResponseBody
    public R listChild(@RequestParam Map<String, Object> params){
        ManagerConfig managerConfig =SpringContextSupport.getBean(ManagerConfig.class);
        if(getUserId()==null){
            return  R.error("请重新登录");
        }
        TMerchantEntity tMerchantEntity = tMerchantService.getInfoByUserId(getUserId());
        if(tMerchantEntity == null){
            return R.error("用户不存在");
        }
        PageUtils page = adMerchantServiceTypeService.queryChildPage(params,tMerchantEntity.getMerchantid());
        List<AdMerchantServiceTypeEntity> list=(List<AdMerchantServiceTypeEntity>)page.getList();
        List<ServiceType> typeList= new ArrayList<>();
        for ( AdMerchantServiceTypeEntity adMerchantServiceTypeEntity: list){
            ServiceType serviceType= new ServiceType();
            serviceType.setName(adMerchantServiceTypeEntity.getTypeName());
            serviceType.setOrder(adMerchantServiceTypeEntity.getOrderNumber());
            serviceType.setParentTypeId(adMerchantServiceTypeEntity.getParentTypeId());
            serviceType.setTypeId(adMerchantServiceTypeEntity.getTypeid());
            serviceType.setStatus(adMerchantServiceTypeEntity.getMpShow());
            serviceType.setServiceTypeId(adMerchantServiceTypeEntity.getServiceTypeId());
            serviceType.setIntro(adMerchantServiceTypeEntity.getDescr());
            if(StringUtils.isNotBlank(adMerchantServiceTypeEntity.getIconPath())){
                serviceType.setCoverUrl(managerConfig.getUrl()+adMerchantServiceTypeEntity.getIconPath());
            }
            serviceType.setProCount(busProductService.getCountByType(adMerchantServiceTypeEntity.getServiceTypeId(),tMerchantEntity.getMerchantid()));
            serviceType.setSupplierCount(busSupplierProductService.getCountBySingleServiceType(adMerchantServiceTypeEntity.getServiceTypeId().toString()));
            typeList.add(serviceType);

        }
        page.setList(typeList);
        return R.ok().put("page", page);
    }
    /**
     * 获取产品分类名称
     */
    @ApiOperation("获取产品分类名称")
    @RequestMapping(value = "/list/Name", method = RequestMethod.GET)
    @ResponseBody
    public R getTypeName() {
        TMerchantEntity tMerchantEntity =tMerchantService.getInfoByUserId(getUserId());
        if(tMerchantEntity==null){
            return R.error("不存在该用户");
        }
        List<AdMerchantServiceTypeEntity> list= adMerchantServiceTypeService.getShowType(tMerchantEntity.getMerchantid());
        List<ProTypeAndChild> proTypeAndChildList =new ArrayList<>();
        for (AdMerchantServiceTypeEntity type: list) {
            if(type.getParentTypeId()==1){
                ProTypeAndChild proTypeAndChild = new ProTypeAndChild();
                proTypeAndChild.setServiceType(type.getServiceTypeId());
                proTypeAndChild.setName(type.getTypeName());
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
                    proTypeAndChildList1.add(proTypeAndChild);
                }
            }
            typeAndChild1.setChildList(proTypeAndChildList1);
        }
        return R.ok().put("list",proTypeAndChildList);
    }
    /**
     * 修改产品分类显示状态(该分类下的产品状态也会变)
     */
    @ApiOperation("修改产品分类显示状态")
    @RequestMapping(value = "/update/show", method = RequestMethod.POST)
    @ResponseBody
    public R updateStatus(AdMerchantServiceTypeEntity entity) {
        TMerchantEntity tMerchantEntity = tMerchantService.getInfoByUserId(getUserId());
        if(tMerchantEntity == null){
            return R.error("用户不存在");
        }
        entity.setMpShow(entity.getWxShow());
        adMerchantServiceTypeService.updateStatusById(entity,tMerchantEntity.getMerchantid());
        return R.ok();
    }


}
