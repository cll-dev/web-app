package com.yitongyin.modules.ad.form;

import lombok.Data;


import java.util.List;
import java.util.Map;

@Data
public class SupplierInfo {
    private  Long id;
    private  Integer status;
    private  String name;
    private  String tel;
    private  String phone;
    private  String adr;
    private List<String> urls;
    private  List<String> typeName;
    private  Integer collectCount;
    private String payWay;
    //private  List<Object> proName;
    private  double distance;
    private String deliveryMethod;
    private String webPath;
    //private  Map<String, List<T>> detailMaps;

}
