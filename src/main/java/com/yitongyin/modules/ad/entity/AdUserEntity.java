package com.yitongyin.modules.ad.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.yitongyin.common.validator.AddGroup;
import com.yitongyin.common.validator.UpdateGroup;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

@Data
@TableName("ad_user")
public class AdUserEntity implements Serializable {
    private static final long serialVersionUID = 1L;

    @TableId
    private Long userid;

    @NotBlank(message = "手机号码不能为空",groups = {AddGroup.class, UpdateGroup.class})
    private String mobile;

    @NotBlank(message = "密码不能为空", groups = AddGroup.class)
    private String password;

    private String salt;

    private Integer status;

    private Date createTime;

    private Date updateTime;

    private Integer postMerchantInfo;

    private String openId;

    private Date lastLoginTime;

    private String email;


    public Long getUserId() {
        return userid;
    }

    public void setUserId(Long userId) {
        this.userid = userId;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Integer getPostMerchantInfo() {
        return postMerchantInfo;
    }

    public void setPostMerchantInfo(Integer postMerchantInfo) {
        this.postMerchantInfo = postMerchantInfo;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }
}
