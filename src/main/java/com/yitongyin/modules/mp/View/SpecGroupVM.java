package com.yitongyin.modules.mp.View;

import lombok.Data;

import java.util.HashSet;
import java.util.List;

@Data
public class SpecGroupVM {

    private String key;

    private String keyName;

    private List<SpecValues> specValues;

    private Integer keyOrder;

    private Integer isFoot;

}
