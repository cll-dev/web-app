package com.yitongyin.modules.ad.form;

import com.yitongyin.modules.ad.entity.BusSupplierSpecValueEntity;
import lombok.Data;

import java.util.List;

@Data
public class SupSpecMode {
    private List<BusSupplierSpecValueEntity> head;
    private List<?> lines;
    private Integer inCommon;
}
