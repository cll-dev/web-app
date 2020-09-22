package com.yitongyin.modules.ad.form;

import com.yitongyin.modules.ad.entity.AdMerchantSpecGroupEntity;
import com.yitongyin.modules.ad.entity.AdMerchantSpecValueEntity;
import lombok.Data;

import java.util.List;

@Data
public class AdSpecMode {
    private List<AdMerchantSpecValueEntity> head;
    private List<AdMerchantSpecGroupEntity> lines;
}
