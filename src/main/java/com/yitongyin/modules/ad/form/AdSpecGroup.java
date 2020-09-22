package com.yitongyin.modules.ad.form;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class AdSpecGroup {
    private Long productId;
    private Long typeId;
    private BigDecimal price;
    private Integer days;
    private Long msgId;
    private List<AdSpecSingle> spec;
    private Long groupId;
    private Long supplierId;
    private String specNote;

}
