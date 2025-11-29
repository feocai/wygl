package com.itmk.web.role_menu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Classname RoleMenu
 * @Description 角色菜单关联表
 * @Date 2024/2/24 12:17
 * @Created by wangxiaojie
 */
@Data
@TableName("sys_role_menu")
public class RoleMenu {
    @TableId(type = IdType.AUTO)
    private Long roleMenuId; //主键自动生成
    private Long roleId;
    private Long menuId;
}
