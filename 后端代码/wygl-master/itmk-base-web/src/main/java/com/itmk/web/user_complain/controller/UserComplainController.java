package com.itmk.web.user_complain.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itmk.utils.ResultUtils;
import com.itmk.utils.ResultVo;
import com.itmk.web.user_complain.entity.UserComplain;
import com.itmk.web.user_complain.entity.UserComplainParm;
import com.itmk.web.user_complain.service.UserComplainService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @Classname UserComplainController
 * @Description
 * @Date 2024/3/16 9:31
 * @Created by wangxiaojie
 */
@RestController
@RequestMapping("/api/userComplain")
public class UserComplainController {
    @Autowired
    private UserComplainService userComplainService;

    //分页查询
    @GetMapping("/getList")
    public ResultVo getList(UserComplainParm parm){
        //构造查询条件
        QueryWrapper<UserComplain> query = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(parm.getTitle())){
            query.lambda().like(UserComplain::getTitle,parm.getTitle());
        }
        if(StringUtils.isNotEmpty(parm.getComplaintContent())){
            query.lambda().like(UserComplain::getComplaintContent,parm.getComplaintContent());
        }
        //构造分页条件
        IPage<UserComplain> page = new Page<>();
        page.setCurrent(parm.getCurrentPage());
        page.setSize(parm.getPageSize());
        IPage<UserComplain> list = userComplainService.page(page, query);
        return ResultUtils.success("查询成功",list);
    }

    //我的投诉
    //分页查询
    @GetMapping("/getMyList")
    public ResultVo getMyList(UserComplainParm parm){
        //构造查询条件
        QueryWrapper<UserComplain> query = new QueryWrapper<>();
        if(StringUtils.isNotEmpty(parm.getTitle())){
            query.lambda().like(UserComplain::getTitle,parm.getTitle());
        }
        if(StringUtils.isNotEmpty(parm.getComplaintContent())){
            query.lambda().like(UserComplain::getComplaintContent,parm.getComplaintContent());
        }
        if(parm.getUserId() != null){
            query.lambda().like(UserComplain::getUserId,parm.getUserId());
        }
        //构造分页条件
        IPage<UserComplain> page = new Page<>();
        page.setCurrent(parm.getCurrentPage());
        page.setSize(parm.getPageSize());
        IPage<UserComplain> list = userComplainService.page(page, query);
        return ResultUtils.success("查询成功",list);
    }
    //新增
    @PostMapping
    @PreAuthorize("hasAuthority('sys:myUserComplain:add')")
    public ResultVo save(@RequestBody UserComplain userComplain){
        //设置投诉状态
        userComplain.setStatus("0");
        //设置投诉时间
        userComplain.setCreateTime(new Date());
        //入库保存
        boolean save = userComplainService.save(userComplain);
        if(save){
            return ResultUtils.success("新增成功");
        }
        return ResultUtils.error("新增失败");
    }

    //编辑
    @PutMapping
    @PreAuthorize("hasAuthority('sys:myUserComplain:edit')" + " || hasAuthority('sys:userComplainList:do')")
    public ResultVo edit(@RequestBody UserComplain userComplain){
        //设置投诉时间
        userComplain.setCreateTime(new Date());
        //入库保存
        boolean b = userComplainService.updateById(userComplain);
        if(b){
            return ResultUtils.success("编辑成功");
        }
        return ResultUtils.error("编辑失败");
    }
    //删除
    @DeleteMapping("/{complaintId}")
    @PreAuthorize("hasAuthority('sys:myUserComplain:delete')")
    public ResultVo delete(@PathVariable("complaintId") Long complaintId){
        boolean b = userComplainService.removeById(complaintId);
        if(b){
            return ResultUtils.success("删除成功");
        }
        return ResultUtils.error("删除失败");
    }

}
