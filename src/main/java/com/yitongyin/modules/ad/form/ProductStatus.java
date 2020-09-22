package com.yitongyin.modules.ad.form;

import lombok.Data;

import java.util.List;

@Data
public class ProductStatus {

    private List<Integer> ids;
    private Integer status;
    private List<Long> busIds;
}
