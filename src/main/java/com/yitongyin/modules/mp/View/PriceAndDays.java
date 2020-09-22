package com.yitongyin.modules.mp.View;

import lombok.Data;

@Data
public class PriceAndDays {
    private Double lowestPrice;
    private Double highestPrice;
    private Integer lowestDays;
    private Integer highestDays;
    private Integer hits;
    private String tags;
    private String ip;
    private Long merchantId;
    private Long typeId;

}
