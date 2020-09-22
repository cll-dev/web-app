package com.yitongyin.modules.ad.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yitongyin.common.validator.AddGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@TableName("ad_user_role")
public class AdUserRoleEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;

    @NotBlank(message = "userId不能为空", groups = AddGroup.class)
    private Long userId;

    @NotBlank(message = "roleId不能为空", groups = AddGroup.class)
    private Long roleId;

}
