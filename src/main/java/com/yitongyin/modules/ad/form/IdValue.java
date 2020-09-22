package com.yitongyin.modules.ad.form;

import lombok.Data;

import java.util.List;

@Data
public class IdValue {
    private Long serviceTypeId;
    private String serviceName;
//    private Long parentServiceTypeId;
    private List<IdValue> childList;
}
