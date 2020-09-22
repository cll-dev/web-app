package com.yitongyin.modules.mp.View;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class DesignFee {
    private String feeName;
    private BigDecimal feePrice;
    private Integer autoInclude;
    private String feeNote;
    private Integer feeDay;
}
