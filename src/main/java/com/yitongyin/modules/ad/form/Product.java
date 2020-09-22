package com.yitongyin.modules.ad.form;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;


@Data
public class Product {

    private  long id;
    private long busId;
    private String name;
    private Integer status;
    private String url;
    private Integer selectCount;
    private Integer supplierCount;
    private List<Map<String,Object>> suppliers;
    private Integer IsSupModel;
    private Integer isPrice;
    private Integer havPrice;
    private String note;
    private BigDecimal rate;
}
