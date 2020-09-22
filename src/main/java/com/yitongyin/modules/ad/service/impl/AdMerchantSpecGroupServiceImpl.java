package com.yitongyin.modules.ad.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yitongyin.common.utils.R;
import com.yitongyin.common.utils.UtilString;
import com.yitongyin.modules.ad.dao.AdMerchantSpecGroupDao;
import com.yitongyin.modules.ad.dao.AdMerchantSpecValueDao;
import com.yitongyin.modules.ad.entity.*;
import com.yitongyin.modules.ad.form.AdSpecGroup;
import com.yitongyin.modules.ad.form.SpecKeyOrder;
import com.yitongyin.modules.ad.service.*;
import com.yitongyin.modules.mp.View.ClientCollectResult;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;


@Service("adMerchantSpecGroupService")
public class AdMerchantSpecGroupServiceImpl extends ServiceImpl<AdMerchantSpecGroupDao, AdMerchantSpecGroupEntity> implements AdMerchantSpecGroupService {

    @Autowired
    AdMerchantSpecValueService adMerchantSpecValueService;
    @Autowired
    AdMerchantSpecValueDao adMerchantSpecValueDao;
    @Autowired
    AdMerchantSpecGroupDao adMerchantSpecGroupDao;
    @Autowired
    AdMerchantProductService adMerchantProductService;
    @Autowired
    BusSupplierSpecGroupService busSupplierSpecGroupService;
    @Autowired
    BusSupplierProductService busSupplierProductService;
    @Autowired
    BusSupplierSpecValueService busSupplierSpecValueService;
    @Autowired
    BusSupplierService busSupplierService;


    @Override
    public List<AdMerchantSpecGroupEntity> getListByProAndMerchant(Long merchantId, Long productId) {
        QueryWrapper<AdMerchantSpecGroupEntity> query = new QueryWrapper<>();
        query.eq("merchant_id", merchantId);
        query.eq("product_id", productId);
        return this.list(query);
    }

    @Override
    public List<AdMerchantSpecGroupEntity> getMpListByProAndMerchant(Long merchantId, Long productId) {
        AdMerchantSpecGroupEntity groupEntity =getByMerAndPro(merchantId,productId);
        if(groupEntity==null){
            return null;
        }
        BusSupplierEntity busSupplierEntity = busSupplierService.getById(groupEntity.getSupplierId());
        Long supplierId=null;
        if(busSupplierEntity==null){
            BusSupplierEntity supplierEntity1=busSupplierService.getByLevelId();
            if(supplierEntity1!=null){
                supplierId=supplierEntity1.getSupplierid();
            }else{
                return null;
            }
        }else{
            supplierId=busSupplierEntity.getSupplierid();
        }
        return adMerchantSpecGroupDao.getMpListAndSupPriceAndDaysByProId(merchantId,productId,supplierId);
    }

    @Override
    //test循环ids减一
    public AdMerchantSpecGroupEntity  getMsgIdByValueIds(Long merchantId,Long proId,String specKey,List<Long> ids) {
        QueryWrapper<AdMerchantSpecGroupEntity> groupQuery= new QueryWrapper<>();
        groupQuery.eq("merchant_id",merchantId).eq("product_id",proId);
        List<AdMerchantSpecGroupEntity> groupEntities =this.list(groupQuery);
        List<Long> relIds=new ArrayList<>();
        Map<Integer,List<?>> maps= new HashMap<>();
        AdMerchantSpecGroupEntity groupEntity = new AdMerchantSpecGroupEntity();
        for (AdMerchantSpecGroupEntity group: groupEntities) {
            QueryWrapper<AdMerchantSpecValueEntity> valueQuery = new QueryWrapper<>();
            valueQuery.eq("msgId",group.getMsgid());
            List<AdMerchantSpecValueEntity> valueEntities =adMerchantSpecValueService.list(valueQuery);
            QueryWrapper<AdMerchantSpecValueEntity> valueQuery1 = new QueryWrapper<>();
            valueQuery1.eq("msgId",group.getMsgid()).eq("spec_on_footer",0);
            List<AdMerchantSpecValueEntity> valueEntities1 =adMerchantSpecValueService.list(valueQuery1);
            maps.put(valueEntities.size(),valueEntities);
            if(ids.size()==valueEntities1.size()){
                List<Long> specIds=valueEntities1.stream().map(e->e.getSpecValueId()).collect(Collectors.toList());
                if(specIds.containsAll(ids)){
                    groupEntity.setSpecNote(group.getSpecNote());
                }
            }
        }
        Integer maxNumber = UtilString.getMaxKey(maps);
        for (AdMerchantSpecGroupEntity group: groupEntities) {
            QueryWrapper<AdMerchantSpecValueEntity> valueQuery = new QueryWrapper<>();
            valueQuery.eq("msgId",group.getMsgid());
            List<AdMerchantSpecValueEntity> valueEntities =adMerchantSpecValueService.list(valueQuery);
            List<Long> valIds=valueEntities.stream().map(e->e.getSpecValueId()).collect(Collectors.toList());
            if(ids.size()==1){
                List<Long> msgIds=groupEntities.stream().map(e->e.getMsgid()).collect(Collectors.toList());
                QueryWrapper<AdMerchantSpecValueEntity> valueQuery1 = new QueryWrapper<>();
                valueQuery1.in("msgId",msgIds);
                valueQuery1.eq("specKey",specKey);
                List<AdMerchantSpecValueEntity> valueEntities1 =adMerchantSpecValueService.list(valueQuery1);
                relIds.addAll(valueEntities1.stream().map(e->e.getSpecValueId()).collect(Collectors.toList()));
                if(valIds.containsAll(ids)){
                    relIds.addAll(valIds);
                }
            }else{
                if(ids.size()==maxNumber){
                    for (int i = 0; i < ids.size(); i++) {
                        List<Long> copyIds= new ArrayList<>();
                        copyIds.addAll(ids);
                        copyIds.remove(ids.get(i));
                        if(valIds.containsAll(copyIds)){
                            relIds.addAll(valIds);
                        }
                    }
                }else{
                    for (int i = 0; i < ids.size(); i++) {
                        List<Long> copyIds= new ArrayList<>();
                        copyIds.addAll(ids);
                        copyIds.remove(ids.get(i));
                        if(valIds.containsAll(copyIds)){
                            QueryWrapper<AdMerchantSpecValueEntity> valueQuery1 = new QueryWrapper<>();
                            valueQuery1.eq("spec_value_id",ids.get(i));
                            AdMerchantSpecValueEntity valueEntities1 =adMerchantSpecValueService.getOne(valueQuery1);
                            for (AdMerchantSpecValueEntity value: valueEntities) {
                                if(value.getSpeckey().equals(valueEntities1.getSpeckey()))
                                    relIds.add(value.getSpecValueId());
                            }

                        }
                    }
                    if(valIds.containsAll(ids))
                        relIds.addAll(valIds);
                }

            }
            if(valIds.size()==ids.size()&&valIds.containsAll(ids)){
                groupEntity=group;
//               BusSupplierSpecGroupEntity specGroupEntity= busSupplierSpecGroupService.getOneByGroupId(groupEntity.getGroupId());
//               if(specGroupEntity!=null){
//                   groupEntity.setSpecNote(specGroupEntity.getSpecNote());
//               }
            }
        }
        relIds.removeAll(ids);
        relIds = relIds.stream().distinct().collect(Collectors.toList());
        groupEntity.setValueIds(relIds);
        return  groupEntity;
    }



    @Override
    public List<AdMerchantSpecGroupEntity> findSpecGroupByProductId(Long merchantId,Long productId,Long supplierId,List<BusSupplierSpecValueEntity> valueEntityList,List<AdMerchantSpecGroupEntity> headValueEntityList) {
        List<AdMerchantSpecGroupEntity> groupList = adMerchantSpecGroupDao.getListAndSupPriceAndDaysByProId(merchantId,productId,supplierId);
        BusSupplierProductEntity productEntity=busSupplierProductService.getOrderByProIdAndMerchantId(productId,merchantId);
        String jsonOrder=productEntity==null?null:productEntity.getSpecKeyOrder();
        List<SpecKeyOrder> orders = new ArrayList<>();
        if(StringUtils.isNotBlank(jsonOrder)){
            orders= JSONArray.parseArray(jsonOrder,SpecKeyOrder.class);
        }

        List<Long> msgIds=groupList.stream().map(e->e.getMsgid()).collect(Collectors.toList());
        valueEntityList.addAll(busSupplierSpecValueService.getListBySupAndPro(supplierId,productId));
        valueEntityList.addAll(adMerchantSpecValueDao.getGroupListByMsgIds(msgIds));
        for (BusSupplierSpecValueEntity valueEntity: valueEntityList) {
            for (SpecKeyOrder order: orders) {
                if(valueEntity.getSpeckey().equals(order.getSpecKey())){
                    valueEntity.setKeyOrderNumber(order.getOrderNumber());
                    break;
                }
            }
        }
        if (groupList != null && groupList.size() > 0) {
            for (AdMerchantSpecGroupEntity group : groupList) {
                group.setIsShow(1);
                List<BusSupplierSpecValueEntity> headList = adMerchantSpecValueDao.findByMsgId(group.getMsgid(),0);
                for (BusSupplierSpecValueEntity valueEntity: headList) {
                    for (SpecKeyOrder order: orders) {
                        if(valueEntity.getSpeckey().equals(order.getSpecKey())){
                            valueEntity.setKeyOrderNumber(order.getOrderNumber());
                            break;
                        }
                    }
                }
                List<BusSupplierSpecValueEntity> sortHeadList = headList.stream().sorted(Comparator.comparing(BusSupplierSpecValueEntity::getKeyOrderNumber,Comparator.nullsFirst(Integer::compareTo))).collect(Collectors.toList());
                sortHeadList.forEach(e->{
                    e.setKeyOrderNumber(null);
                });
                group.setHeadSpecValues(sortHeadList);
                List<BusSupplierSpecValueEntity> footList = adMerchantSpecValueDao.findByMsgId(group.getMsgid(),1);
                for (BusSupplierSpecValueEntity valueEntity: footList) {
                    for (SpecKeyOrder order: orders) {
                        if(valueEntity.getSpeckey().equals(order.getSpecKey())){
                            valueEntity.setKeyOrderNumber(order.getOrderNumber());
                            break;
                        }
                    }
                }
                footList.forEach(e->{
                    e.setKeyOrderNumber(null);
                    e.setInCommon(null);
                });
                group.setFootSpecValues(footList);
                if(footList!=null&&footList.size()!=0){
                    group.setOrderNumber(footList.get(0).getOrderNumber());
                }
            }
            try{
                List<AdMerchantSpecGroupEntity> depHeadList=deepCopy(groupList);
                headValueEntityList.addAll(depHeadList);
            }catch (Exception e){
                e.printStackTrace();
            }
            groupList.forEach(e->{
                e.getHeadSpecValues().forEach(c->{
                    c.setInCommon(null);
                });
            });
            // List<Long> groupIds=groupList.stream().map(e->e.getGroupId()).collect(Collectors.toList());
            List<String> valueIds=groupList.stream().map(e->e.getValueId()).collect(Collectors.toList());
            List<BusSupplierSpecGroupEntity> list = busSupplierSpecGroupService.queryBySupAndProId(supplierId,productId,new ArrayList<>(),valueIds);
            if(list!=null&&list.size()!=0){
                List<AdMerchantSpecGroupEntity> listTurn=list.stream().map(e->{
                    AdMerchantSpecGroupEntity groupEntity = new AdMerchantSpecGroupEntity();
                    groupEntity.setHeadSpecValues(e.getHeadSpecValues());
                    groupEntity.setFootSpecValues(e.getFootSpecValues());
                    groupEntity.setOrderNumber(e.getOrderNumber());
                    groupEntity.setDeliveryDay(e.getDeliveryDay());
                    groupEntity.setSalePrice(e.getSalePrice());
                    groupEntity.setPrice(e.getPrice());
                    groupEntity.setProductId(e.getProductId());
                    groupEntity.setServiceTypeId(e.getServiceTypeId());
                    groupEntity.setGroupId(e.getGroupId());
                    groupEntity.setIsShow(0);
                    groupEntity.setSpecNote(e.getSpecNote());
                    groupEntity.setUrl(e.getUrl());
                    return groupEntity;
                }).collect(Collectors.toList());
                groupList.addAll(listTurn);
            }
            return groupList;
        } else {
            return null;
        }
    }

    @Override
    public Boolean getByProductIdAndMerchantId(Long merchantId, Long productId) {
        QueryWrapper<AdMerchantSpecGroupEntity> query = new QueryWrapper<>();
        query.eq("merchant_id", merchantId);
        query.eq("product_id", productId);
        if(this.getOne(query)!=null){
            return true;
        }else{
            return false;
        }

    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean upePriceAndDaysById(Long id,Integer days, BigDecimal price,String specNote) {
        UpdateWrapper<AdMerchantSpecGroupEntity> update =new UpdateWrapper<>();
        update.set("delivery_day",days).set("price",price).set("spec_note",specNote);
        update.eq("msgId",id);
        return this.update(update);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean deleteByMsgId(List<Long> ids,Long merchantId,Long productId) {
        if(adMerchantSpecValueService.deleteByMsgId(ids)){
            QueryWrapper<AdMerchantSpecGroupEntity> query = new QueryWrapper<>();
            query.eq("merchant_id",merchantId);
            query.eq("product_id",productId);
            query.in("msgId",ids);
            return this.remove(query);
        }else{
            return  false;
        }
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean
    saveOrUpdBatch(List<AdSpecGroup> specGroups,Long merchantId,Long productId) {
        if(specGroups!=null&&specGroups.size()!=0){
            AdMerchantProductEntity productEntity=adMerchantProductService.getOneByMerchantIdAndProductId(merchantId,productId);
            if(productEntity!=null){
                specGroups.forEach(e->{
                    e.setTypeId(productEntity.getServiceTypeId());
                });
            }
        }
        Boolean flag =true;
//        if(!deleteByMsgId(filterList,merchantId,productId)){
//            flag=false;
//            return flag;
//        }
        if(specGroups==null||specGroups.size()==0){
            List<AdMerchantSpecGroupEntity> curList=getListByProAndMerchant(merchantId,productId);
            List<Long> curMsgIds=curList.stream().map(e->e.getMsgid()).collect(Collectors.toList());
            return deleteByMsgId(curMsgIds,merchantId,productId);
        }else{
            List<Long> msgIds=specGroups.stream().map(e->e.getMsgId()).collect(Collectors.toList());
            List<AdMerchantSpecGroupEntity> curList=getListByProAndMerchant(merchantId,productId);
            List<Long> curMsgIds=curList.stream().map(e->e.getMsgid()).collect(Collectors.toList());
            List<Long> filterList = curMsgIds.stream().filter(item -> !msgIds.contains(item)).collect(Collectors.toList());
            deleteByMsgId(filterList,merchantId,productId);
        }
        for (AdSpecGroup group:specGroups) {
            if(group.getMsgId()!=null){
                if(!this.upePriceAndDaysById(group.getMsgId(),group.getDays(),group.getPrice(),group.getSpecNote())){
                    flag=false;
                    break;
                }
            }else{
                AdMerchantSpecGroupEntity specGroupEntity = new AdMerchantSpecGroupEntity();
                specGroupEntity.setGroupId(group.getGroupId());
                specGroupEntity.setDeliveryDay(group.getDays());
                specGroupEntity.setPrice(group.getPrice());
                specGroupEntity.setMerchantId(merchantId);
                specGroupEntity.setProductId(group.getProductId());
                specGroupEntity.setServiceTypeId(group.getTypeId());
                specGroupEntity.setSupplierId(group.getSupplierId());
                specGroupEntity.setSpecNote(group.getSpecNote());
                if(this.save(specGroupEntity)){
                    List<AdMerchantSpecValueEntity> specValueList=group.getSpec().stream().map(e->{
                        AdMerchantSpecValueEntity valueEntity = new AdMerchantSpecValueEntity();
                        valueEntity.setMsgid(specGroupEntity.getMsgid());
                        valueEntity.setSpeckey(e.getKey());
                        valueEntity.setSpecValueId(e.getValueId());
                        valueEntity.setSpecOnFooter(e.getSpecOnFooter());
                        return valueEntity;
                    }).collect(Collectors.toList());
                    if(!adMerchantSpecValueService.saveBatch(specValueList)){
                        flag=false;
                        break;
                    }else{
                        BusSupplierSpecGroupEntity groupEntity = busSupplierSpecGroupService.getOneByGroupId(group.getGroupId());
                        if(groupEntity!=null){
                            BusSupplierProductEntity busSupplierProductEntity = busSupplierProductService.getOrderByProIdAndSupId(
                                    groupEntity.getSupplierId(),groupEntity.getProductId());
                            if(busSupplierProductEntity!=null){
                                adMerchantProductService.insertSupProId(merchantId,busSupplierProductEntity.getProductId(),busSupplierProductEntity.getId());
                            }
                        }
                    }
                }else{
                    flag=false;
                    break;
                }
            }
        }
        return flag;
    }

    @Override
    public AdMerchantSpecGroupEntity getByMerAndPro(Long merchantId, Long productId) {
        QueryWrapper<AdMerchantSpecGroupEntity> query = new QueryWrapper<>();
        query.eq("merchant_id", merchantId);
        query.eq("product_id", productId);
        return this.getOne(query);
    }

    @Override
    public AdMerchantSpecGroupEntity getLowestPriceByMerAndPro(Long merchantId, Long productId) {
        QueryWrapper<AdMerchantSpecGroupEntity> query = new QueryWrapper<>();
        query.eq("merchant_id", merchantId);
        query.eq("product_id", productId);
        query.isNotNull("price");
        query.ne("price",0);
        query.orderByAsc("price");
        return this.getOne(query);
    }

    @Override
    public List<ClientCollectResult> getMpClientCollectByConditions(Long merchantId, Long productId, Long supplierId, String valueIds) {
        if(supplierId.equals(0l)){
            BusSupplierEntity supplierEntity1=busSupplierService.getByLevelId();
            supplierId=supplierEntity1.getSupplierid();
        }
        List<ClientCollectResult> list=adMerchantSpecGroupDao.getMpClientSpecCollectAndDaysByProId(merchantId,productId,supplierId,valueIds);
        return list;
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