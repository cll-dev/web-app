package com.yitongyin.modules.ad.form;

import com.yitongyin.modules.ad.entity.AdOssEntity;
import lombok.Data;

import java.util.Date;
import java.util.List;


@Data
public class CaseResult {
    private Long id;
    private String img;
    private List<AdOssEntity> imgList;
    private String content;
    private String proName;
    private Long proId;
    private Date createTime;
}
