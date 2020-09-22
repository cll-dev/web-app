package com.yitongyin.modules.ad.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yitongyin.common.utils.PageUtils;
import com.yitongyin.modules.ad.dao.BusSupplierProductDao;
import com.yitongyin.modules.ad.entity.BusSupplierEntity;
import com.yitongyin.modules.ad.entity.BusSupplierProductEntity;
import com.yitongyin.modules.ad.form.Product;
import com.yitongyin.modules.ad.form.SupplierProductList;
import com.yitongyin.modules.ad.service.AdMerchantProductService;
import com.yitongyin.modules.ad.service.BusSupplierProductService;
import com.yitongyin.modules.ad.service.BusSupplierService;
import org.apache.commons.lang.StringUtils;
import org.omg.CORBA.OBJ_ADAPTER;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;


@Service("busSupplierProductService")
public class BusSupplierProductServiceImpl extends ServiceImpl<BusSupplierProductDao, BusSupplierProductEntity> implements BusSupplierProductService {
    @Autowired
    private BusSupplierProductDao busSupplierProductDao;
    @Autowired
    private AdMerchantProductService adMerchantProductService;
    @Autowired
    private BusSupplierService busSupplierService;
    @Override
    public List<Object> getListByProId(long proId) {
        QueryWrapper<BusSupplierProductEntity> query = new QueryWrapper<>();
        query.select("supplier_id");
        query.eq("product_id",proId);
        query.groupBy("supplier_id");
        List<Object> objects =busSupplierService.filterTemplate(this.listObjs(query));
        return objects;
    }

    @Override
    public List<BusSupplierProductEntity> getEntListByProId(Long proId) {
        QueryWrapper<BusSupplierProductEntity> query = new QueryWrapper<>();
        query.select("supplier_id");
        query.eq("product_id",proId);
        query.groupBy("supplier_id");
        List<BusSupplierEntity> objects =busSupplierService.filterEntTemplate(this.listObjs(query));
        List<BusSupplierProductEntity> list =objects.stream().map(e->{
            BusSupplierProductEntity entity = new BusSupplierProductEntity();
            entity.setSupplierId(e.getSupplierid());
            return entity;
        }).collect(Collectors.toList());
        return list;
    }


    @Override
    public Integer getCountByServiceType(List<Object> ids) {
       return busSupplierProductDao.getSupCountByServiceType(ids);
    }

    @Override
    public Integer getCountBySingleServiceType(String id) {
        return busSupplierProductDao.getSupCountBySingleServiceType(id);
    }

    @Override
    public List<BusSupplierEntity> getSupIdBySingleServiceType(Long id) {
        return busSupplierProductDao.getIdsBySingleServiceType(id);
    }

    @Override
    public List<String> getTypeNamesBySupId(Long id) {
        return busSupplierProductDao.getTypeNamesBySupId(id);
    }

    @Override
    public List<BusSupplierEntity> getIdAndNamesByType(Long id) {
        return busSupplierProductDao.getIdAndNamesBySingleServiceType(id);
    }

    @Override
    public PageUtils getProListByType(Map<String, Object> params,Long supId, Long merchantId, Long serviceTypeId) {
        int page=Integer.valueOf(params.get("page").toString());
        int limit=Integer.valueOf(params.get("limit").toString());
        int start=(Integer.valueOf(params.get("page").toString())-1)*limit;
        List<SupplierProductList> list= busSupplierProductDao.getProListByTypeId(supId,merchantId,serviceTypeId);
        if(list==null||list.size()==0){
            return null;
        }
        List<Long> proIds=list.stream().map(e->e.getProductId()).collect(Collectors.toList());
        List<Product> productList= adMerchantProductService.getByMerchantId(merchantId,proIds,start,limit);
        for (SupplierProductList supplierProlist:list) {
            for (Product product: productList) {
                if(product.getBusId()==supplierProlist.getProductId()){
                    product.setNote(supplierProlist.getProductNote());
                    break;
                }
            }
        }
        QueryWrapper<BusSupplierProductEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("supplier_id",supId);
        queryWrapper.eq("service_type_id",serviceTypeId);
        Integer count =this.count(queryWrapper);
        return new PageUtils(productList,count,limit,page);
    }

    @Override
    public PageUtils getAllProList(Map<String, Object> params,Long supId, Long merchantId) {
        int page=Integer.valueOf(params.get("page").toString());
        int limit=Integer.valueOf(params.get("limit").toString());
        int start=(Integer.valueOf(params.get("page").toString())-1)*limit;
        List<SupplierProductList> list= busSupplierProductDao.getAllProList(supId);
        if(list==null||list.size()==0){
            return null;
        }
        List<Long> proIds=list.stream().map(e->e.getProductId()).collect(Collectors.toList());
        List<Product> productList= adMerchantProductService.getByMerchantId(merchantId,proIds,start,limit);
        for (SupplierProductList supplierProlist:list) {
            for (Product product: productList) {
                if(product.getBusId()==supplierProlist.getProductId()){
                    product.setNote(supplierProlist.getProductNote());
                    break;
                }
            }
        }
        QueryWrapper<BusSupplierProductEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("supplier_id",supId);
        Integer count =this.count(queryWrapper);
        return new PageUtils(productList,count,limit,page);
    }

    @Override
    public List<Long> getSupIdsByProNameAndSupName(String name) {
        return busSupplierProductDao.getSupIdsByProNameAndSupName(name);
    }

    @Override
    public List<Long> getAreaListByProId(Long proId, Integer county) {
        QueryWrapper<BusSupplierProductEntity> query = new QueryWrapper<>();
        query.select("supplier_id");
        query.eq("product_id",proId);
        query.groupBy("supplier_id");
        List<BusSupplierEntity> list =busSupplierService.queryByMerchantCounty(county);
        List<BusSupplierProductEntity> entities =this.list(query);
        List<Long> ids = entities.stream().map(p -> p.getSupplierId()).collect(Collectors.toList());
        List<Long> defaultIds = list.stream().map(p -> p.getSupplierid()).collect(Collectors.toList());
        ids = ids.stream().filter(item -> defaultIds.contains(item)).collect(Collectors.toList());
        return ids;
    }

    @Override
    public BusSupplierProductEntity getOrderByProIdAndSupId(Long supId, Long proId) {
        QueryWrapper<BusSupplierProductEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("supplier_id",supId).eq("product_id",proId);
        return  this.getOne(queryWrapper);
    }

    @Override
    public BusSupplierProductEntity getOrderByProIdAndMerchantId(Long proId, Long merchantId) {
        return busSupplierProductDao.getOneByProIdAndMerId(proId,merchantId);
    }

    @Override
    public List<BusSupplierProductEntity> getListBySupIds(List<Long> supplierIds) {
        return busSupplierProductDao.getListBySupIds(supplierIds);
    }

}