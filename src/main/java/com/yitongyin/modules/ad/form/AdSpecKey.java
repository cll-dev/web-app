package com.yitongyin.modules.ad.form;

import lombok.Data;

import java.util.List;

@Data
public class AdSpecKey {
    private String specKey;
    private String specName;
    private List<AdSpecValue> values;
}
