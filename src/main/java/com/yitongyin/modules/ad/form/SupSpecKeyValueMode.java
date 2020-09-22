package com.yitongyin.modules.ad.form;

import com.yitongyin.modules.ad.entity.BusSupplierSpecValueEntity;
import lombok.Data;

import java.util.List;

@Data
public class SupSpecKeyValueMode {
    private String keyName;
    private List<BusSupplierSpecValueEntity> list;
    private Integer keyOrder;
}
