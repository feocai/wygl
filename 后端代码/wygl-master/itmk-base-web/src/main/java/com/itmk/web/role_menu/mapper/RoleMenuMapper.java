package com.itmk.web.role_menu.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.itmk.web.role_menu.entity.RoleMenu;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * @Classname RoleMenuMapper
 * @Description RoleMenu mapper层
 * @Date 2024/2/24 12:21
 * @Created by wangxiaojie
 */
public interface RoleMenuMapper extends BaseMapper<RoleMenu> {

    //保存权限
    boolean saveRoleMenu(@Param("roleId") Long roleId, @Param("menuIds") List<Long> menuIds);
}
