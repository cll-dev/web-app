package com.yitongyin.modules.ad.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yitongyin.common.utils.UtilString;
import com.yitongyin.modules.ad.dao.BusSupplierSpecGroupDao;
import com.yitongyin.modules.ad.dao.BusSupplierSpecValueDao;
import com.yitongyin.modules.ad.entity.AdMerchantSpecGroupEntity;
import com.yitongyin.modules.ad.entity.BusSupplierProductEntity;
import com.yitongyin.modules.ad.entity.BusSupplierSpecGroupEntity;
import com.yitongyin.modules.ad.entity.BusSupplierSpecValueEntity;
import com.yitongyin.modules.ad.form.SpecKeyOrder;
import com.yitongyin.modules.ad.service.BusSupplierProductService;
import com.yitongyin.modules.ad.service.BusSupplierSpecGroupService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;


@Service("busSupplierSpecGroupService")
public class BusSupplierSpecGroupServiceImpl extends ServiceImpl<BusSupplierSpecGroupDao, BusSupplierSpecGroupEntity> implements BusSupplierSpecGroupService {

    @Autowired
    BusSupplierSpecGroupDao busSupplierSpecGroupDao;
    @Autowired
    BusSupplierSpecValueDao busSupplierSpecValueDao;
    @Autowired
    BusSupplierProductService busSupplierProductService;
    @Override
    public List<BusSupplierSpecGroupEntity> queryBySupAndProId(Long supplierId,Long productId,List<BusSupplierSpecValueEntity> values,List<String> valueIds){
        List<BusSupplierSpecGroupEntity> list = new ArrayList<>();
        QueryWrapper<BusSupplierSpecGroupEntity> query = new QueryWrapper<>();
        query.eq("supplier_id",supplierId);
        query.eq("product_id",productId);
        if(valueIds==null){
           list=this.list(query);
        }else{
             list =busSupplierSpecGroupDao.getListBySupIdAndProIdAndValueIds(supplierId,productId,valueIds);
        }
        list.forEach(e -> {
            e.setSpecNote(null);
        });
        BusSupplierProductEntity productEntity=busSupplierProductService.getOrderByProIdAndSupId(supplierId,productId);
        String jsonOrder=productEntity==null?null:productEntity.getSpecKeyOrder();
        List<SpecKeyOrder> orders = new ArrayList<>();
        if(StringUtils.isNotBlank(jsonOrder)){
           orders=JSONArray.parseArray(jsonOrder,SpecKeyOrder.class);
        }
        if (list != null && list.size() > 0) {
            for (BusSupplierSpecGroupEntity group : list) {
                if(group.getPrice()!=null&&group.getSalePrice()!=null){
                    group.setIsShow(1);
                }else{
                    group.setIsShow(0);
                }
                List<BusSupplierSpecValueEntity> headList = busSupplierSpecValueDao.findIsFootBySsgId(group.getSsgid(),0);
                for (BusSupplierSpecValueEntity valueEntity: headList) {
                    for (SpecKeyOrder order: orders) {
                        if(valueEntity.getSpeckey().equals(order.getSpecKey())){
                            valueEntity.setKeyOrderNumber(order.getOrderNumber());
                            break;
                        }
                    }
                }
                List<BusSupplierSpecValueEntity> sortHeadList = headList.stream().sorted(Comparator.comparing(BusSupplierSpecValueEntity::getKeyOrderNumber,Comparator.nullsFirst(Integer::compareTo))).collect(Collectors.toList());
                group.setHeadSpecValues(sortHeadList);
                List<BusSupplierSpecValueEntity> footList = busSupplierSpecValueDao.findIsFootBySsgId(group.getSsgid(),1);
                for (BusSupplierSpecValueEntity valueEntity: footList) {
                    for (SpecKeyOrder order: orders) {
                        if(valueEntity.getSpeckey().equals(order.getSpecKey())){
                            valueEntity.setKeyOrderNumber(order.getOrderNumber());
                            break;
                        }
                    }
                }
                group.setFootSpecValues(footList);
                if(footList!=null&&footList.size()!=0){
                    group.setOrderNumber(footList.get(0).getOrderNumber());
                }
               try{
                   List<BusSupplierSpecValueEntity> depHeadList=deepCopy(headList);
                   List<BusSupplierSpecValueEntity> depFootList=deepCopy(footList);
                   values.addAll(depHeadList);
                   values.addAll(depFootList);
               }catch (Exception e){
                    e.printStackTrace();
               }

                sortHeadList.forEach(e->{
                    e.setKeyOrderNumber(null);
                });
                footList.forEach(e->{
                    e.setKeyOrderNumber(null);
                });
            }
            return list;
        } else {
            return null;
        }
    }

    @Override
    public BusSupplierSpecGroupEntity getOneByGroupId(Long groupId) {
        QueryWrapper<BusSupplierSpecGroupEntity> queryWrapper =  new QueryWrapper<>();
        queryWrapper.eq("group_id",groupId);
        return this.getOne(queryWrapper);
    }

    @Override
    public List<BusSupplierSpecGroupEntity> getListBySupplierIdsAndProId(List<Long> supplierIds, Long productId) {
        QueryWrapper<BusSupplierSpecGroupEntity> query =  new QueryWrapper<>();
        query.eq("product_id",productId);
        query.in("supplier_id",supplierIds);
        return this.list(query);
    }
    public static <T> List<T> deepCopy(List<T> src) throws IOException, ClassNotFoundException {
        ByteArrayOutputStream byteOut = new ByteArrayOutputStream();
        ObjectOutputStream out = new ObjectOutputStream(byteOut);
        out.writeObject(src);

        ByteArrayInputStream byteIn = new ByteArrayInputStream(byteOut.toByteArray());
        ObjectInputStream in = new ObjectInputStream(byteIn);
        @SuppressWarnings("unchecked")
        List<T> dest = (List<T>) in.readObject();
        return dest;
    }



}