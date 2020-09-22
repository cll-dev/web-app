package com.yitongyin.modules.ad.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yitongyin.modules.ad.dao.BusMaterialTypeDao;
import com.yitongyin.modules.ad.entity.BusMaterialTypeEntity;
import com.yitongyin.modules.ad.service.BusMaterialTypeService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("busMaterialTypeService")
public class BusMaterialTypeServiceImpl extends ServiceImpl<BusMaterialTypeDao, BusMaterialTypeEntity> implements BusMaterialTypeService {

    @Override
    public List<BusMaterialTypeEntity> getAllListAndChild() {
       List<BusMaterialTypeEntity> list= this.list();
       List<BusMaterialTypeEntity> parentList=list.stream().filter(a->a.getParentTypeId().equals(0l)).collect(Collectors.toList());
       List<BusMaterialTypeEntity> childList=list.stream().filter(a->!a.getParentTypeId().equals(0l)).collect(Collectors.toList());
       Map<Long, List<BusMaterialTypeEntity>> map = childList.stream().collect(Collectors.groupingBy(BusMaterialTypeEntity::getParentTypeId));
       for (BusMaterialTypeEntity parent: parentList) {
           for (Long key: map.keySet()) {
               if(key.equals(parent.getBmtid()))
                   parent.setChild(map.get(key));
           }
       }
       return parentList;
    }
}