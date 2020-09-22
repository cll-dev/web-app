package com.yitongyin.modules.ad.form;

import lombok.Data;

import java.util.List;

@Data
public class ProTypeAndChild {
    private Long serviceType;
    private String name;
    private List<ProTypeAndChild> childList;
    private Long typeId;

}
