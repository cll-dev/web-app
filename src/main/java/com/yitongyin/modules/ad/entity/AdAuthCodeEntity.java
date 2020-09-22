package com.yitongyin.modules.ad.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yitongyin.common.validator.AddGroup;
import lombok.Data;


import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

/**
 * 验证码
 */
@Data
@TableName("ad_auth_code")
public class AdAuthCodeEntity implements Serializable {


    private static final long serialVersionUID = 1L;


    @TableId
    private Long authCodeId;

    @NotBlank(message = "验证码不能为空", groups = AddGroup.class)
    private String authCode;

    private Date sendDate;

    private Date timeoutDate;

    @NotBlank(message = "手机号不能为空", groups = AddGroup.class)
    private String mobilePhone;

    @NotBlank(message = "类型不能为空", groups = AddGroup.class)
    private Integer type;


}
