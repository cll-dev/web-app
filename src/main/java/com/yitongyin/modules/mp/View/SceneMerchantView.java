package com.yitongyin.modules.mp.View;

import com.yitongyin.common.utils.PageUtils;
import com.yitongyin.modules.ad.entity.AdSceneMerchantEntity;
import lombok.Data;

import java.util.List;

@Data
public class SceneMerchantView {

    private Long smid;
    private String name;
    private String mainNo;
    private String industryName;
    private Long bitId;
    private PageUtils page;
    private String address;
    private String houseNumber;
    private String mainDesc;
    private List<AdSceneMerchantEntity> similarList;
}
