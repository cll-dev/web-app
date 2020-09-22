package com.yitongyin.modules.ad.form;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class SupplierProductList {
    private Long productId;
    private String productName;
    private Long coverResId;
    private String coverUrl;
    private Integer selectCount;
    private Integer mpShow;
    private Long merchantProId;
    private Integer isSupModel;
    private Integer supplierCount;
    private List<Map<String,Object>> suppliers;
    private String productNote;
//    private Integer collectStatus;
}
