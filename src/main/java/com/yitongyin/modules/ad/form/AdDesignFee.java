package com.yitongyin.modules.ad.form;

import lombok.Data;

@Data
public class AdDesignFee {
    private String feeName;
    private Double feePrice;
    private Boolean autoInclude;
    private String feeNote;
}
