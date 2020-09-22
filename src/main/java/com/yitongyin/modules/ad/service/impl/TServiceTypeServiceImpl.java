package com.yitongyin.modules.ad.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yitongyin.modules.ad.dao.TServiceTypeDao;
import com.yitongyin.modules.ad.entity.BusProductEntity;
import com.yitongyin.modules.ad.entity.TMerchantEntity;
import com.yitongyin.modules.ad.entity.TServiceTypeEntity;
import com.yitongyin.modules.ad.service.BusProductService;
import com.yitongyin.modules.ad.service.TServiceTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("tServiceTypeService")
public class TServiceTypeServiceImpl extends ServiceImpl<TServiceTypeDao, TServiceTypeEntity> implements TServiceTypeService {

    @Autowired
    BusProductService busProductService;

    @Override
    public List<Object> getChilds(Long serviceTypeId) {
        QueryWrapper<TServiceTypeEntity> query = new QueryWrapper<>();
        query.select("serviceTypeId");
        query.eq("parentServiceTypeId",serviceTypeId);
        return this.listObjs(query);
    }

    @Override
    public List<TServiceTypeEntity> getAllListChild() {
        QueryWrapper<TServiceTypeEntity> queryWrapper =new QueryWrapper<>();
        queryWrapper.select("serviceTypeId","serviceName","parentServiceTypeId");
        List<TServiceTypeEntity> list= this.list(queryWrapper);
        List<TServiceTypeEntity> parentList=list.stream().filter(a->a.getParentservicetypeid().equals(1l)).collect(Collectors.toList());
        List<TServiceTypeEntity> childList=list.stream().filter(a->!a.getParentservicetypeid().equals(1l)).collect(Collectors.toList());
        Map<Long, List<TServiceTypeEntity>> map = childList.stream().collect(Collectors.groupingBy(TServiceTypeEntity::getParentservicetypeid));
        for (TServiceTypeEntity parent: parentList) {
            for (Long key: map.keySet()) {
                if(key.equals(parent.getServicetypeid()))
                    parent.setChildList(map.get(key));
            }
        }
        return parentList;
    }

    @Override
    public List<TServiceTypeEntity> getAllListAndProChild(TMerchantEntity merchantEntity,Integer state) {
        QueryWrapper<TServiceTypeEntity> queryWrapper =new QueryWrapper<>();
        queryWrapper.select("serviceTypeId","serviceName","parentServiceTypeId","childNumber");
        queryWrapper.eq("wxShowAble",1);
        queryWrapper.orderByAsc("orderNumber");
        List<TServiceTypeEntity> list= this.list(queryWrapper);
        List<TServiceTypeEntity> parentList=list.stream().filter(a->a.getParentservicetypeid().equals(1l)).collect(Collectors.toList());
        List<TServiceTypeEntity> childList=list.stream().filter(a->!a.getParentservicetypeid().equals(1l)).collect(Collectors.toList());
        Map<Long, List<TServiceTypeEntity>> map = childList.stream().collect(Collectors.groupingBy(TServiceTypeEntity::getParentservicetypeid));
        for (TServiceTypeEntity parent: parentList) {
            if(parent.getChildnumber()>0){
                for (Long key: map.keySet()) {
                    if(key.equals(parent.getServicetypeid())){
                        parent.setChildList(map.get(key));
                        break;
                    }
                }
                for (TServiceTypeEntity typeEntity:parent.getChildList()) {
                    List<BusProductEntity> productEntities = busProductService.getNameByTypeId(typeEntity.getServicetypeid(),merchantEntity,state);
                   typeEntity.setProductList(productEntities);
                }
            }else{
               List<BusProductEntity> productEntities = busProductService.getNameByTypeId(parent.getServicetypeid(),merchantEntity,state);
               parent.setProductList(productEntities);
            }
        }
        return parentList;
    }

}