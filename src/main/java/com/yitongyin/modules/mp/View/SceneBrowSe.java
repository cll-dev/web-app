package com.yitongyin.modules.mp.View;

import com.yitongyin.modules.ad.entity.AdClientProductBrowseRecordEntity;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class SceneBrowSe {
    private Date time;
    private List<AdClientProductBrowseRecordEntity> list;
}
