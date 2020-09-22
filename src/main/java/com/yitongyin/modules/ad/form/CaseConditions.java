package com.yitongyin.modules.ad.form;

import lombok.Data;

import java.util.List;

@Data
public class CaseConditions {
    private Long proCat;
    private Boolean isMoreHist;
    private Boolean isNewDate;
    private String proName;
    private List<Long> productId;
}
