package com.yitongyin.modules.ad.form;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class SpecGroup {

    private BigDecimal price;
    private Integer deliveryDay;
    private String specNote;
    private List<Long> specValueIds;
    private String url;
    private Integer isShowPrice;
}
