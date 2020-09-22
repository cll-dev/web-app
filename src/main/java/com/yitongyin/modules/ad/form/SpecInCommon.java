package com.yitongyin.modules.ad.form;

import lombok.Data;

import java.util.List;

@Data
public class SpecInCommon {
    private List<Long> msgIds;
    private List<Long> valueIds;
    private Integer status;
    private Long productId;
}
