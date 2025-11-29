package com.itmk.web.role.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.itmk.utils.ResultUtils;
import com.itmk.utils.ResultVo;
import com.itmk.web.role.entity.*;
import com.itmk.web.role.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Classname RoleController
 * @Description 角色控制层
 * @Date 2024/2/22 17:54
 * @Created by wangxiaojie
 */
@RestController
@RequestMapping("/api/role")
public class RoleController {

    @Autowired
    private RoleService roleService;

    //获取角色列表
    @GetMapping("/list")
    public ResultVo list(RoleParm roleParm){
        IPage<Role> list = roleService.list(roleParm);
        return ResultUtils.success("查询成功",list);
    }
    //新增角色
    @PostMapping
    @PreAuthorize("hasAuthority('sys:role:add')")
    public ResultVo addRole(@RequestBody Role role){
        boolean save = roleService.save(role);
        if(save){
            return ResultUtils.success("新增角色成功");
        }
        return ResultUtils.error("新增角色失败");
    }
    //编辑角色
    @PutMapping
    @PreAuthorize("hasAuthority('sys:role:edit')")
    public ResultVo editRole(@RequestBody Role role){
        boolean save = roleService.updateById(role);
        if(save){
            return ResultUtils.success("编辑角色成功");
        }
        return ResultUtils.error("编辑角色失败");
    }

    //删除角色
    @DeleteMapping("/{roleId}")
    @PreAuthorize("hasAuthority('sys:role:delete')")
    public ResultVo deleteRole(@PathVariable Long roleId){
        boolean b = roleService.removeById(roleId);
        if(b){
            return ResultUtils.success("删除角色成功");
        }
        return ResultUtils.error("删除角色失败");
    }
    //查询分配权限树回显
    @GetMapping("/getAssignTree")
    public ResultVo getAssignTree(RoleAssignParm parm){
        RolePermissionVo assignTree = roleService.getAssignTree(parm);
        return ResultUtils.success("查询成功",assignTree);
    }
    //保存权限
    @PostMapping("/saveAssignTree")
    //@PreAuthorize("hasAuthority('sys:role:assignMenu')")
    public ResultVo saveAssignTree(@RequestBody RolePermissionParm parm){
        roleService.saveAssign(parm);
        return ResultUtils.success("分配权限成功");
    }
    //获取角色列表
    @GetMapping("/getRoleList")
    public ResultVo getRoleList(){
        List<Role> list = roleService.list();
        return ResultUtils.success("查询成功",list);
    }

}
