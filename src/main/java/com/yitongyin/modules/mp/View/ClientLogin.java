package com.yitongyin.modules.mp.View;

import lombok.Data;

@Data
public class ClientLogin {
    private String moblie;
    private String password;
    private String code;
    private Long merchantId;
}
