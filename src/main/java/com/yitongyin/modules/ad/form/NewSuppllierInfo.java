package com.yitongyin.modules.ad.form;

import lombok.Data;

import java.util.List;

@Data
public class NewSuppllierInfo {
    private String supName;
    private Integer collectCount;
    private String aboutUs;
    private String payWay;
    private String tel;
    private String phone;
    private String adr;
    private String otherInfo;
    private List<String> urls;
    private List<IdValue> types;
    private List<SupplierProductList> productLists;
    private Integer status;
    private List<String> typeName;
    private String webPath;
    private String deliveryMethod;

}
