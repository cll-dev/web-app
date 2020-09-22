package com.yitongyin.modules.ad.form;

import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
public class CaseSubmit implements Serializable {

    private List<String> imgIds;
    private Long proId;
    private String content;
}
