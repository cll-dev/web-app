package com.yitongyin.modules.mp.View;

import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class CaseInfo {
    private Long Id;
    private String proName;
    private String content;
    private String picUrl;
    private List<String> picUrls;
    private Date time;
}
