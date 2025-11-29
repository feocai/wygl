package com.itmk.web.role.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @Classname Role
 * @Description 角色实体类
 * @Date 2024/2/22 17:52
 * @Created by wangxiaojie
 */
@Data
@TableName("sys_role")
public class Role implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long roleId;
    //角色名称
    private String roleName;
    //备注
    private String remark;
}
