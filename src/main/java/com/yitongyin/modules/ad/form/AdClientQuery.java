package com.yitongyin.modules.ad.form;

import lombok.Data;

@Data
public class AdClientQuery {
    private Long merchantId;
    private Integer groupId;
    private Integer tagId;
    private String name;
    private Integer noGroupId;
    private Integer noTagId;
    private Integer isLastLogin;
}
