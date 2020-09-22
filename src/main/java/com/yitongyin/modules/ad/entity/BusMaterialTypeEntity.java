package com.yitongyin.modules.ad.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
@TableName("bus_material_type")
public class BusMaterialTypeEntity {

    @TableId
    private Long bmtid;

    private String name;

    private Long parentTypeId;

    private String descr;

    private Date createTime;

    private Date updateTime;
    @TableField(exist = false)
    private List<BusMaterialTypeEntity> child;
}
