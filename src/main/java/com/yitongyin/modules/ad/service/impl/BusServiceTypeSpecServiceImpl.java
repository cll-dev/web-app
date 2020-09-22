package com.yitongyin.modules.ad.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yitongyin.modules.ad.dao.BusServiceTypeSpecDao;
import com.yitongyin.modules.ad.entity.BusServiceTypeSpecEntity;
import com.yitongyin.modules.ad.entity.BusSpecEntity;
import com.yitongyin.modules.ad.service.BusServiceTypeSpecService;
import com.yitongyin.modules.ad.service.BusSpecService;
import com.yitongyin.modules.ad.service.BusSpecValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service("busServiceTypeSpecService")
public class BusServiceTypeSpecServiceImpl extends ServiceImpl<BusServiceTypeSpecDao, BusServiceTypeSpecEntity> implements BusServiceTypeSpecService {

    @Autowired
    private BusSpecService busSpecService;
    @Autowired
    private BusSpecValueService busSpecValueService;
    @Override
    public List<BusSpecEntity> getKeysByServiceTypeId(Long serviceTypeId) {
        QueryWrapper<BusServiceTypeSpecEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("specKey");
        queryWrapper.eq("service_type_id",serviceTypeId);
        List<Object> objects=this.listObjs(queryWrapper);
        List<BusSpecEntity> busSpecEntityList =objects.stream().map(e->{
            BusSpecEntity entity = new BusSpecEntity();
            entity.setSpeckey(e.toString());
            entity.setValueList(busSpecValueService.getByKey(e.toString()));
            entity.setSpecName(busSpecService.getById(e.toString())==null
                    ?null:busSpecService.getById(e.toString()).getSpecName());
            return entity;
        }).collect(Collectors.toList());
        return busSpecEntityList;
    }
}