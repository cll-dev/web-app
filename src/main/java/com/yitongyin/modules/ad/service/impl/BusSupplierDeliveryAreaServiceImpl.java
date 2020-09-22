package com.yitongyin.modules.ad.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yitongyin.modules.ad.dao.BusSupplierDeliveryAreaDao;
import com.yitongyin.modules.ad.entity.BusSupplierDeliveryAreaEntity;
import com.yitongyin.modules.ad.service.BusSupplierDeliveryAreaService;
import org.springframework.stereotype.Service;




@Service("busSupplierDeliveryAreaService")
public class BusSupplierDeliveryAreaServiceImpl extends ServiceImpl<BusSupplierDeliveryAreaDao, BusSupplierDeliveryAreaEntity> implements BusSupplierDeliveryAreaService {
//    @Override
//    public List<BusSupplierDeliveryAreaEntity> getSupIdsByAreaIds(Integer cityId) {
//        QueryWrapper<BusSupplierDeliveryAreaEntity> query = new QueryWrapper<>();
//        query.eq("city_id", cityId);
//        return this.list(query);
//    }


}