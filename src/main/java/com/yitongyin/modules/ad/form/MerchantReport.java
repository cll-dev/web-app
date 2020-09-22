package com.yitongyin.modules.ad.form;

import lombok.Data;

import java.util.Date;

@Data
public class MerchantReport {
    private Long merchantId;
    private String merchantName;
    private Date lastLoginTime;
    private Date createTime;
    private Integer sumViewCount;
    private Integer sumHitCount;
    private Integer countMpShow;
    private Integer countSpc;
    private Integer countSupplier;
    private Integer countCase;
    private Integer designerContactNumber;
    private Integer materialSendNumber;
    private  double avgSumViewCount;
    private  double avgSumHitCount;
    private  double avgCountMpShow;
    private  double avgCountSpc;
    private  double avgCountSupplier;
    private  double avgCountCase;
    private  double avgDesignerContactNumber;
    private  double avgMaterialSendNumber;

}
