package com.yitongyin.modules.ad.form;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class SupSpecSingleMode {
    private Long ssgId;
    private Long productId;
    private Long typeId;
    private BigDecimal price;
    private Integer days;
    private BigDecimal salePrice;
    private List<AdSpecSingle> valueIds;
}
