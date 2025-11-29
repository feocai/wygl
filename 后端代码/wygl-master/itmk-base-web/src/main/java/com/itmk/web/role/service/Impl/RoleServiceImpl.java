package com.itmk.web.role.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itmk.web.menu.entity.MakeMenuTree;
import com.itmk.web.menu.entity.Menu;
import com.itmk.web.menu.service.MenuService;
import com.itmk.web.role.entity.*;
import com.itmk.web.role.mapper.RolerMapper;
import com.itmk.web.role.service.RoleService;
import com.itmk.web.role_menu.entity.RoleMenu;
import com.itmk.web.role_menu.service.RoleMenuService;
import com.itmk.web.user.entity.User;
import com.itmk.web.user.service.UserService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @Classname RoleServiceImpl
 * @Description 角色service实现层
 * @Date 2024/2/22 17:54
 * @Created by wangxiaojie
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RolerMapper, Role> implements RoleService {

    @Autowired
    private UserService userService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private RoleMenuService roleMenuService;

    @Override
    public IPage<Role> list(RoleParm parm) {
        //构造查询条件
        QueryWrapper<Role> query = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(parm.getRoleName())){
            query.lambda().like(Role::getRoleName,parm.getRoleName());
        }
        //构造分页对象
        IPage<Role> page = new Page<>();
        page.setSize(parm.getPageSize());
        page.setCurrent(parm.getCurrentPage());
        return this.baseMapper.selectPage(page,query);
    }

    //查询分配权限树回显
    @Override
    public RolePermissionVo getAssignTree(RoleAssignParm parm) {
        //查询当前用户的信息
        User user = userService.getById(parm.getUserId());
        //判断当前用户是否是管理员
        //如果是，拥有所有权限，如果不是，根据用户id查询权限
        List<Menu> menus = null;
        if("1".equals(user.getIsAdmin())){//是管理员
            menus = menuService.list();
        }else{//不是管理员，根据id查询权限
            menus = menuService.getMenuByUserId(parm.getUserId());
        }
        //组装成树的格式
        List<Menu> menuList = MakeMenuTree.maketree(menus, 0L);
        //根据角色id查询权限
        List<Menu> roleMenuList = menuService.getMenuByRoleId(parm.getRoleId());
       //需要查询出回显的部分
        List<Long> ids = new ArrayList<>();
        //根据角色id查询角色原来的权限信息
        Optional.ofNullable(roleMenuList).orElse(new ArrayList<>()).stream().filter(item ->item != null).forEach(item->{
            ids.add(item.getMenuId());
        });
//        Optional.ofNullable(menuList).orElse(new ArrayList<>()).stream().filter(item -> item !=null).forEach(item ->{
//            Optional.ofNullable(roleMenuList).orElse(new ArrayList<>()).stream().filter(dom -> dom != null).forEach(dom ->{
//                if(item.getMenuId().equals(dom.getMenuId())){
//                    ids.add(dom.getMenuId());
//                    return;
//                }
//            });
//        });
        RolePermissionVo vo = new RolePermissionVo();
        //vo.setListmenu(menus);
        vo.setListmenu(menuList);
        vo.setCheckList(ids.toArray());
        return vo;
    }
    //保存权限
    @Override
    @Transactional
    public void saveAssign(RolePermissionParm parm) {
        //删除原来的权限
        QueryWrapper<RoleMenu> query = new QueryWrapper<>();
        query.lambda().eq(RoleMenu::getRoleId,parm.getRoleId());
        roleMenuService.remove(query);
        //保存新的权限
        roleMenuService.saveRoleMenu(parm.getRoleId(),parm.getList());
    }
}
