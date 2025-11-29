package com.itmk.web.user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itmk.web.user.entity.User;
import com.itmk.web.user.entity.UserParm;
import com.itmk.web.user_role.entity.UserRole;

/**
 * @Classname UserService
 * @Description
 * @Date 2024/2/21 18:09
 * @Created by wangxiaojie
 */
public interface UserService extends IService<User> {

    //查询用户列表
    IPage<User> list(UserParm parm);
    //根据用户id查询角色
    UserRole getRoleByUserId(UserRole userRole);
    //保存角色
    void saveRole(UserRole userRole);
    //根据登录名查询用户信息
    User loadUser(String username);
}
