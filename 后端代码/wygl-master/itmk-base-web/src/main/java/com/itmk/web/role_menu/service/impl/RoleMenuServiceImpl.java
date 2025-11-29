package com.itmk.web.role_menu.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itmk.web.role_menu.entity.RoleMenu;
import com.itmk.web.role_menu.mapper.RoleMenuMapper;
import com.itmk.web.role_menu.service.RoleMenuService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Classname RoleMenuServiceImpl
 * @Description RoleMenu service实现层
 * @Date 2024/2/24 12:24
 * @Created by wangxiaojie
 */
@Service
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {

    //保存权限
    @Override
    public void saveRoleMenu(Long roleId, List<Long> menuIds) {
        this.baseMapper.saveRoleMenu(roleId,menuIds);
    }

}
