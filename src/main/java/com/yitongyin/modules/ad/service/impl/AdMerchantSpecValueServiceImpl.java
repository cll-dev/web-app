package com.yitongyin.modules.ad.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yitongyin.common.utils.PageUtils;
import com.yitongyin.common.utils.Query;
import com.yitongyin.modules.ad.dao.AdMerchantSpecValueDao;
import com.yitongyin.modules.ad.entity.AdMerchantSpecValueEntity;
import com.yitongyin.modules.ad.entity.BusSpecValueEntity;
import com.yitongyin.modules.ad.form.SpecInCommon;
import com.yitongyin.modules.ad.service.AdMerchantSpecValueService;
import com.yitongyin.modules.ad.service.BusSpecService;
import com.yitongyin.modules.ad.service.BusSpecValueService;
import com.yitongyin.modules.mp.View.SpecGroupVM;
import com.yitongyin.modules.mp.View.SpecKeys;
import com.yitongyin.modules.mp.View.SpecValues;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;


@Service("adMerchantSpecValueService")
public class AdMerchantSpecValueServiceImpl extends ServiceImpl<AdMerchantSpecValueDao, AdMerchantSpecValueEntity> implements AdMerchantSpecValueService {
    @Autowired
    BusSpecService busSpecService;
    @Autowired
    BusSpecValueService busSpecValueService;
    @Autowired
    AdMerchantSpecValueDao adMerchantSpecValueDao;
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<AdMerchantSpecValueEntity> page = this.page(
                new Query<AdMerchantSpecValueEntity>().getPage(params),
                new QueryWrapper<AdMerchantSpecValueEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public boolean deleteAll() {
        QueryWrapper<AdMerchantSpecValueEntity> query = new QueryWrapper<>();
        query.isNotNull("id");
        return this.remove(query);
    }

    @Override
    public List<AdMerchantSpecValueEntity> getListByMsgId(Long msgId) {
        QueryWrapper<AdMerchantSpecValueEntity> query = new QueryWrapper<>();
        query.eq("msgId",msgId);
        return  this.list(query);
    }

//    @Override
//    public Map<SpecKeys,List<SpecValues>> ListByMsgId(List<Long> msgIds) {
//        QueryWrapper<AdMerchantSpecValueEntity> query = new QueryWrapper<>();
//        query.in("msgId",msgIds);
//        List<AdMerchantSpecValueEntity> specValueEntities=this.list(query);
//        Map<SpecKeys,List<SpecValues>> maps= new HashMap<>();
//        Map<String, List<AdMerchantSpecValueEntity>> listMap = specValueEntities.stream()
//                .collect(Collectors.groupingBy(AdMerchantSpecValueEntity::getSpeckey));
//        for(String key : listMap.keySet()){
//           SpecKeys specKeys =new SpecKeys();
//           specKeys.setKey(key);
//           specKeys.setKeyName(busSpecService.getById(key).getSpecName());
//           List<SpecValues> specValues =listMap.get(key).stream().map(adMerchantSpecValueEntity -> {
//                   SpecValues specValue=new SpecValues();
//                   specValue.setValueId(adMerchantSpecValueEntity.getSpecValueId());
//                   specValue.setValueName(busSpecValueService.getById(adMerchantSpecValueEntity.getSpecValueId()).getValName());
//                   return specValue;
//           }).collect(Collectors.toList());
//           maps.put(specKeys,specValues);
//        }
//        for (List<SpecValues> specValuesList : maps.values()) {
//            HashSet h = new HashSet(specValuesList);
//            specValuesList.clear();
//            specValuesList.addAll(h);
//        }
//        return maps;
//    }

    @Override
    public List<SpecGroupVM> getSpecGroupByMsgId(List<Long> msgIds) {
//        QueryWrapper<AdMerchantSpecValueEntity> query = new QueryWrapper<>();
//        query.in("msgId",msgIds);
        List<AdMerchantSpecValueEntity> specValueEntities=adMerchantSpecValueDao.findByMsgIds(msgIds);

//        for (AdMerchantSpecValueEntity entity: specValueEntities) {
//            entity.setValDes(busSpecValueService.getById(entity.getSpecValueId()).getValDes());
//        }
        Map<String, List<AdMerchantSpecValueEntity>> listMap = specValueEntities.stream()
                .collect(Collectors.groupingBy(AdMerchantSpecValueEntity::getSpeckey));

        List<SpecGroupVM> specGroupVMList = new ArrayList<>();
        for(String key : listMap.keySet()){
            SpecGroupVM specGroupVM = new SpecGroupVM();
            specGroupVM.setKey(key);
            specGroupVM.setKeyName(listMap.get(key).get(0).getSpecKeyName());
            specGroupVM.setIsFoot(listMap.get(key).get(0).getSpecOnFooter());

            List<SpecValues> specValues =listMap.get(key).stream().map(adMerchantSpecValueEntity -> {
                SpecValues specValue=new SpecValues();
                specValue.setValueId(adMerchantSpecValueEntity.getSpecValueId());
               // BusSpecValueEntity busSpecValue=busSpecValueService.getById(adMerchantSpecValueEntity.getSpecValueId());
                specValue.setValueName(adMerchantSpecValueEntity.getSpecValueName());
                specValue.setOrder(adMerchantSpecValueEntity.getOrderNumber());
                specValue.setInCommon(adMerchantSpecValueEntity.getInCommon());
                return specValue;
            }).collect(Collectors.toList());
//            List<Long> ids = new ArrayList<>();//用来临时存储person的id
//            specValues = specValues.stream().filter(// 过滤去重
//                    v -> {
//                        boolean flag = !ids.contains(v.getValueId())&&v.getInCommon().equals(1);
//                        ids.add(v.getValueId());
//                        return flag;
//                    }
//            ).collect(Collectors.toList());
            List<SpecValues> isIncommon=specValues.stream().filter(e->e.getInCommon().equals(1)).collect(Collectors.toList())
                    .stream().distinct().collect(Collectors.toList());
            specValues = specValues.stream().filter(distinctByKey((p) -> (p.getValueId()))).collect(Collectors.toList());
            for (SpecValues specValues1: specValues) {
                for (SpecValues specValues2: isIncommon) {
                    if(specValues1.getValueId().equals(specValues2.getValueId())&&specValues2.getInCommon().equals(1)){
                        specValues1.setInCommon(1);
                    }
                }
            }
            specValues = specValues.stream().sorted(Comparator.comparing(SpecValues::getOrder,Comparator.nullsFirst(Integer::compareTo))).collect(Collectors.toList());
//            specValues=specValues.stream().sorted((u1, u2) -> u2.getOrder()==null?0:u2.getOrder().compareTo(u1.getOrder()==null?0:u1.getOrder())).collect(Collectors.toList());
            specGroupVM.setSpecValues(specValues);
            specGroupVMList.add(specGroupVM);
        }

        return specGroupVMList;
    }


    @Override
    public boolean deleteByMsgId(List<Long> ids) {
        if(ids==null||ids.size()==0){
            return true;
        }
        QueryWrapper<AdMerchantSpecValueEntity> query = new QueryWrapper<>();
        query.in("msgId",ids);
        return this.remove(query);
    }
    @Transactional
    @Override
    public boolean updInCommon(SpecInCommon atr,List<Long> curMsgIds) {
//        List<Long> reduce1 = curMsgIds.stream().filter(item -> !atr.getMsgIds().contains(item)).collect(Collectors.toList());
        UpdateWrapper<AdMerchantSpecValueEntity> updBefore = new UpdateWrapper<>();
        updBefore.in("msgId",curMsgIds);
        updBefore.set("in_common",0);
        if(this.update(updBefore)){
            UpdateWrapper<AdMerchantSpecValueEntity> upd = new UpdateWrapper<>();
            upd.in("msgId",atr.getMsgIds()).in("spec_value_id",atr.getValueIds());
            upd.set("in_common",atr.getStatus());
            return this.update(upd);
        }
        return false;
    }
    private static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> seen = new ConcurrentHashMap<>();
        return t -> seen.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }


}