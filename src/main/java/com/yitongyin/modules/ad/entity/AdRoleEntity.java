package com.yitongyin.modules.ad.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

@Data
@TableName("ad_role")
public class AdRoleEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId
    private Long roleid;

    @NotBlank(message="角色名称不能为空")
    private String roleName;

    private String remark;

    private Date createTime;

    private Date updateTime;

}
