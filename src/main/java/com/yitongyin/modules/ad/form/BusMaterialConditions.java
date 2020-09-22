package com.yitongyin.modules.ad.form;

import lombok.Data;

@Data
public class BusMaterialConditions {
    private  Long id;
    private  Integer typeId;
    private  String keyWord;
    private  Long btmId;
    private  Integer isMostSend;
    private  Integer isMostView;
    private  Integer isNew;
    private  Long bitId;
}
