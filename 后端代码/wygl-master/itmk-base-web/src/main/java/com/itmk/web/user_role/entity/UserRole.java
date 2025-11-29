package com.itmk.web.user_role.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @Classname UserMenu
 * @Description
 * @Date 2024/2/25 10:59
 * @Created by wangxiaojie
 */
@Data
@TableName("sys_user_role")
public class UserRole implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long userRoleId;
    private Long userId;
    private Long roleId;
}
