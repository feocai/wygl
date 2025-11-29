package com.itmk.web.house_list.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.itmk.utils.ResultUtils;
import com.itmk.utils.ResultVo;
import com.itmk.web.house_list.entity.HouseList;
import com.itmk.web.house_list.entity.HouseListParm;
import com.itmk.web.house_list.service.HouseListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Classname HouseListController
 * @Description 房屋列表 控制层
 * @Date 2024/2/28 9:39
 * @Created by wangxiaojie
 */
@RestController
@RequestMapping("/api/houseList")
public class HouseListController {

    @Autowired
    private HouseListService houseListService;

    //分页查询
    @GetMapping("/list")
    public ResultVo getList(HouseListParm houseListParm){
        IPage<HouseList> list = houseListService.getList(houseListParm);
        return ResultUtils.success("查询成功",list);
    }
    //添加
    @PostMapping
    @PreAuthorize("hasAuthority('sys:house:add')")
    public ResultVo save(@RequestBody HouseList houseList){
        boolean save = houseListService.save(houseList);
        if(save) {
            return ResultUtils.success("添加成功");
        }
        return ResultUtils.error("添加失败");
    }
    //修改
    @PutMapping
    @PreAuthorize("hasAuthority('sys:house:edit')")
    public ResultVo edit(@RequestBody HouseList houseList){
        boolean b = houseListService.updateById(houseList);
        if(b) {
            return ResultUtils.success("修改成功");
        }
        return ResultUtils.error("修改失败");
    }

    //删除
    @DeleteMapping("/{houseId}")
    @PreAuthorize("hasAuthority('sys:house:delete')")
    public ResultVo delete(@PathVariable Long houseId){
        boolean b = houseListService.removeById(houseId);
        if(b) {
            return ResultUtils.success("删除成功");
        }
        return ResultUtils.error("删除失败");
    }

    //根据单元id获取房屋列表
    @GetMapping("/getHouseByUnitId")
    public ResultVo getHouseByUnitId(HouseList houseList){
        QueryWrapper<HouseList> query = new QueryWrapper<>();
        query.lambda().eq(HouseList::getUnitId,houseList.getUnitId());
        List<HouseList> list = houseListService.list(query);
        return ResultUtils.success("查询成功",list);
    }

}
