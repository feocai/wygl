package com.itmk.web.user_repair.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itmk.utils.ResultUtils;
import com.itmk.utils.ResultVo;
import com.itmk.web.user_repair.entity.UserRepair;
import com.itmk.web.user_repair.entity.UserRepairParm;
import com.itmk.web.user_repair.service.UserRepairService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @Classname UserRepairController
 * @Description
 * @Date 2024/3/17 20:47
 * @Created by wangxiaojie
 */
@RestController
@RequestMapping("/api/userRepair")
public class UserRepairController {

    @Autowired
    private UserRepairService userRepairService;
    //分页查询
    @GetMapping("/getList")
    @PreAuthorize("hasAuthority('sys:userRepairList')")
    public ResultVo getList(UserRepairParm userRepairParm){
        //构造查询条件
        QueryWrapper<UserRepair> query = new QueryWrapper<>();
        query.lambda().like(UserRepair::getRepairContent,userRepairParm.getRepairContent());
        //构造分页参数
        IPage<UserRepair> page = new Page<>();
        page.setSize(userRepairParm.getPageSize());
        page.setCurrent(userRepairParm.getCurrentPage());
        IPage<UserRepair> list = userRepairService.page(page, query);
        return ResultUtils.success("查询成功",list);
    }
    //我的维修
    @GetMapping("/getMyList")
    public ResultVo getMyList(UserRepairParm userRepairParm){
        //构造查询条件
        QueryWrapper<UserRepair> query = new QueryWrapper<>();
        query.lambda().like(UserRepair::getRepairContent,userRepairParm.getRepairContent())
                .eq(UserRepair::getUserId,userRepairParm.getUserId());
        //构造分页参数
        IPage<UserRepair> page = new Page<>();
        page.setSize(userRepairParm.getPageSize());
        page.setCurrent(userRepairParm.getCurrentPage());
        IPage<UserRepair> list = userRepairService.page(page, query);
        return ResultUtils.success("查询成功",list);
    }
    //新增
    @PostMapping
    @PreAuthorize("hasAuthority('sys:userRepairList:add')")
    public ResultVo addUserRepair(@RequestBody UserRepair userRepair){
        //维修时间
        userRepair.setConmmitTime(new Date());
        //维修状态
        userRepair.setStatus("0");//维修状态 0：未维修，1：已维修
        boolean b = userRepairService.save(userRepair);
        if(b){
            return ResultUtils.success("新增成功");
        }
        return ResultUtils.error("新增失败");
    }

    //编辑
    @PutMapping
    @PreAuthorize("hasAuthority('sys:userRepairList:edit')")
    public ResultVo editUserRepair(@RequestBody UserRepair userRepair){
        //维修时间
        userRepair.setConmmitTime(new Date());
        boolean b = userRepairService.updateById(userRepair);
        if(b){
            return ResultUtils.success("编辑成功");
        }
        return ResultUtils.error("编辑失败");
    }
    //删除
    @DeleteMapping("/{repairId}")
    @PreAuthorize("hasAuthority('sys:userRepairList:delete')")
    public ResultVo deleteUserRepair(@PathVariable("repairId") Long repairId){
        boolean b = userRepairService.removeById(repairId);
        if(b){
            return ResultUtils.success("删除成功");
        }
        return ResultUtils.error("删除失败");
    }
}
