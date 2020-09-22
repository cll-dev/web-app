package com.yitongyin.modules.mp.View;

import lombok.Data;

import java.util.List;

@Data
public class SceneProject {
    private Long spid;
    private String projectTitle;
    private String merchantName;
    private String url;
    private Long spaceStId;
    private Long smId;
    private String space;
    private List<String> urls;
    private String projectDesc;
    private String industryName;
    private Integer thumbsUpNumber;
    private List<SceneProduct> products;
    private List<SceneProjectSimilarView> similars;
    private Long bigMainPicId;
    private String bigMainUrl;
}
