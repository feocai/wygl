package com.itmk.web.menu.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itmk.web.menu.entity.Menu;
import com.itmk.web.menu.entity.MenuParm;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Classname Service
 * @Description 菜单service层
 * @Date 2024/2/23 13:28
 * @Created by wangxiaojie
 */
public interface MenuService extends IService<Menu> {

    //上级菜单的查询
    List<Menu> getParentList();

    //查询菜单列表
    List<Menu> getList();

    //根据用户id查询权限
    List<Menu> getMenuByUserId(Long userId);
    //根据角色id查询权限
    List<Menu> getMenuByRoleId(Long roleId);
    //根据业主的id查询菜单
    List<Menu> getMenuByUserIdForLiveUser(Long userId);
}
