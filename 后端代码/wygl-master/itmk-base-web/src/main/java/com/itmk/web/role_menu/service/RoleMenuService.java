package com.itmk.web.role_menu.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.itmk.web.role_menu.entity.RoleMenu;

import java.util.List;

/**
 * @Classname RoleMenuService
 * @Description RoleMenu service层
 * @Date 2024/2/24 12:23
 * @Created by wangxiaojie
 */
public interface RoleMenuService extends IService<RoleMenu> {
    //保存权限
    void saveRoleMenu(Long roleId, List<Long> menuId);
}
