package com.yitongyin.modules.ad.form;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CaseForm implements Serializable {
    private long id;
    private List<String> imgList;
    private String content;
    private Long proId;

}
