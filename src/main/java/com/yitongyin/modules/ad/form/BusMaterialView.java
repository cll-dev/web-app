package com.yitongyin.modules.ad.form;

import lombok.Data;

@Data
public class BusMaterialView {
    private  Long id;
    private  String imgUrl;
    private  String title;
    private  String sourceUrl;
    private  Integer type;
    private  String typeStr;
    private  String toEmail;
}
