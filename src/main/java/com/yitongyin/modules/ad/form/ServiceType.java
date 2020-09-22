package com.yitongyin.modules.ad.form;

import lombok.Data;

@Data
public class ServiceType {
    private Long typeId;
    private String  name;
    private Integer status;
    private Integer order;
    private Long parentTypeId;
    private Integer proCount;
    private Integer supplierCount;
    private String coverUrl;
    private Long serviceTypeId;
    private String intro;
    private Integer childNumber;

}
