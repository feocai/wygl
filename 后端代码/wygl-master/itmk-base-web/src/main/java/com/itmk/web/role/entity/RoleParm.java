package com.itmk.web.role.entity;

import lombok.Data;

/**
 * @Classname RoleParm
 * @Description
 * @Date 2024/2/22 18:04
 * @Created by wangxiaojie
 */
@Data
public class RoleParm {
    //页容量
    private Long pageSize;
    //当前页
    private Long currentPage;
    //角色名称
    private String roleName;
}
