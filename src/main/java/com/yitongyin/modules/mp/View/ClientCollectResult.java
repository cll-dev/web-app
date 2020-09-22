package com.yitongyin.modules.mp.View;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ClientCollectResult {
    private BigDecimal price;
    private Integer deliveryDay;
    private String url;
}
