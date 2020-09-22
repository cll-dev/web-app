package com.yitongyin.modules.mp.View;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ProductPriceInfo {
    private Long id;
    private Long productId;
    private String productName;
    private String url;
    private BigDecimal price;
}
