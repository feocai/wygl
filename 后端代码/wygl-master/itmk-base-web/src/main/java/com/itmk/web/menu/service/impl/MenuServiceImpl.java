package com.itmk.web.menu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itmk.web.menu.entity.MakeMenuTree;
import com.itmk.web.menu.entity.Menu;
import com.itmk.web.menu.mapper.MenuMapper;
import com.itmk.web.menu.service.MenuService;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * @Classname MenuServiceImpl
 * @Description 菜单service实现层
 * @Date 2024/2/23 13:36
 * @Created by wangxiaojie
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    //上级菜单的查询
    @Override
    public List<Menu> getParentList() {
        //上级菜单只需要查询目录和菜单
        String[] types = {"0","1"};
        //构造查询条件
        QueryWrapper<Menu> query = new QueryWrapper<>();
        query.lambda().in(Menu::getType, Arrays.asList(types)).orderByAsc(Menu::getOrderNum);
        List<Menu> menus = this.baseMapper.selectList(query);
        //构造顶级树形数据
        Menu menu = new Menu();
        menu.setMenuId(0L);
        menu.setParentId(-1L);
        menu.setMenuLabel("顶级菜单");
        menus.add(menu);
        //构造树形数据
        List<Menu> menuList = MakeMenuTree.maketree(menus, -1L);
        return menuList;
    }

    //查询菜单列表
    @Override
    public List<Menu> getList() {
        //构造查询条件
        QueryWrapper<Menu> query = new QueryWrapper<>();
        query.lambda().orderByAsc(Menu::getOrderNum);
        List<Menu> menus = this.baseMapper.selectList(query);
        //构造树形数据
        List<Menu> menuList = MakeMenuTree.maketree(menus, 0L);
        return menuList;
    }
    //根据用户id查询权限
    @Override
    public List<Menu> getMenuByUserId(Long userId) {
        return this.baseMapper.getMenuByUserId(userId);
    }
    //根据角色id查询权限
    @Override
    public List<Menu> getMenuByRoleId(Long roleId) {
        return this.baseMapper.getMenuByRoleId(roleId);
    }

    @Override
    public List<Menu> getMenuByUserIdForLiveUser(Long userId) {
        return this.baseMapper.getMenuByUserIdForLiveUser(userId);
    }
}
