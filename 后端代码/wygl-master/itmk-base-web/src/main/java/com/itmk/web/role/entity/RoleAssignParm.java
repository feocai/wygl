package com.itmk.web.role.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Classname RoleAssignParm
 * @Description
 * @Date 2024/2/24 13:07
 * @Created by wangxiaojie
 */
@Data
public class RoleAssignParm implements Serializable {
    //用户id
    private Long userId;
    //角色id
    private Long roleId;
}
