package com.yitongyin.modules.ad.form;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class SupplierProlistGroup {
    private Long supplierId;
    private List<Long> proIds;
    private BigDecimal price;
    private Integer day;
}
