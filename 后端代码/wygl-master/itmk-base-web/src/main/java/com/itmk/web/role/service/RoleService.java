package com.itmk.web.role.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itmk.web.role.entity.*;

/**
 * @Classname RoleService
 * @Description 角色service层
 * @Date 2024/2/22 17:53
 * @Created by wangxiaojie
 */
public interface RoleService extends IService<Role> {
    //分页查询
    IPage<Role> list(RoleParm roleParm);
    //查询分配权限树回显
    RolePermissionVo getAssignTree(RoleAssignParm parm);
    //保存权限
    void saveAssign(RolePermissionParm parm);
}
