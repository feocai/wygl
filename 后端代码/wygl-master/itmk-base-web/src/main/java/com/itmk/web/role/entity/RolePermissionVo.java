package com.itmk.web.role.entity;

import com.itmk.web.menu.entity.Menu;
import lombok.Data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @Classname RolePermissionVo
 * @Description
 * @Date 2024/2/24 13:09
 * @Created by wangxiaojie
 */
@Data
public class RolePermissionVo implements Serializable {
    //当前登录系统用户的菜单数据
    List<Menu> listmenu = new ArrayList<>();
    //角色原来分配的菜单
    private Object[] checkList;
}
