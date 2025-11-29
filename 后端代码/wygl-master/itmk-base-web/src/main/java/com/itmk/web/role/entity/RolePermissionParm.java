package com.itmk.web.role.entity;

import lombok.Data;

import java.util.List;

/**
 * @Classname RolePermissionParm
 * @Description
 * @Date 2024/2/24 14:19
 * @Created by wangxiaojie
 */
@Data
public class RolePermissionParm {
    private Long roleId;
    List<Long> list;
}
