package com.yitongyin.modules.mp.View;

import lombok.Data;

import java.util.List;

@Data
public class SceneProduct {
    private Long productId;
    private String proTitle;
    private String proName;
    private String serviceName;
    private String productDesc;
    private List<String> proUrls;
    private Long merchantProductId;
    private String position;
    private String bigMainUrl;
}
