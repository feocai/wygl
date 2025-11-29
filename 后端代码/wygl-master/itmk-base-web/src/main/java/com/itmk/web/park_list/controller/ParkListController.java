package com.itmk.web.park_list.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.itmk.utils.ResultUtils;
import com.itmk.utils.ResultVo;
import com.itmk.web.park_list.entity.ParkList;
import com.itmk.web.park_list.entity.ParkListParm;
import com.itmk.web.park_list.service.ParkListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.xml.transform.Result;
import java.util.List;

/**
 * @Classname ParkListController
 * @Description
 * @Date 2024/2/29 17:04
 * @Created by wangxiaojie
 */
@RestController
@RequestMapping("/api/parkList")
public class ParkListController {

    @Autowired
    private ParkListService parkListService;
    //分页查询
    @GetMapping("/list")
    public ResultVo getList(ParkListParm parm){
        IPage<ParkList> list = parkListService.getList(parm);
        return ResultUtils.success("查询成功",list);
    }
    //新增
    @PostMapping
    @PreAuthorize("hasAuthority('sys:parkList:add')")
    public ResultVo save(@RequestBody ParkList parm){
        boolean save = parkListService.save(parm);
        if(save){
           return ResultUtils.success("新增成功");
        }
        return ResultUtils.error("新增失败");
    }
    //编辑
    @PutMapping
    @PreAuthorize("hasAuthority('sys:parkList:edit')")
    public ResultVo edit(@RequestBody ParkList parm){
        boolean b = parkListService.updateById(parm);
        if(b){
            return ResultUtils.success("编辑成功");
        }
        return ResultUtils.error("编辑失败");
    }
    //删除
    @DeleteMapping("/{parkId}")
    @PreAuthorize("hasAuthority('sys:parkList:delete')")
    public ResultVo delete(@PathVariable("parkId") Long parkId){
        boolean b = parkListService.removeById(parkId);
        if(b){
            return ResultUtils.success("删除成功");
        }
        return ResultUtils.error("删除失败");
    }
    //查询已使用停车列表
    @GetMapping("/getParkList")
    public ResultVo getParkList(){
        QueryWrapper<ParkList> query = new QueryWrapper<>();
        query.lambda().eq(ParkList::getParkStatus,"1");
        List<ParkList> list = parkListService.list(query);
        return ResultUtils.success("查询成功",list);

    }
}
