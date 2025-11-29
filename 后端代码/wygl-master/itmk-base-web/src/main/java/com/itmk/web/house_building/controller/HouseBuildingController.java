package com.itmk.web.house_building.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.itmk.utils.ResultUtils;
import com.itmk.utils.ResultVo;
import com.itmk.web.house_building.entity.HouseBuilding;
import com.itmk.web.house_building.entity.HouseBuildingParm;
import com.itmk.web.house_building.service.HouseBuildingService;
import com.itmk.web.house_unit.entity.HouseUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Classname HouseBuildingController
 * @Description 栋数管理 控制器
 * @Date 2024/2/26 9:35
 * @Created by wangxiaojie
 */
@RestController
@RequestMapping("/api/HouseBuilding")
public class HouseBuildingController {

    @Autowired
    private HouseBuildingService houseBuildingService;

    //分页查询
    @GetMapping("/list")
    public ResultVo list(HouseBuildingParm houseBuildingParm){
        IPage<HouseBuilding> list = houseBuildingService.getList(houseBuildingParm);
        return ResultUtils.success("查询成功",list);
    }

    //新增栋数
    @PostMapping
    @PreAuthorize("hasAuthority('sys:houseBuilding:add')")
    public ResultVo save(@RequestBody HouseBuilding HouseBuilding){
        boolean save = houseBuildingService.save(HouseBuilding);
        if(save){
            return ResultUtils.success("添加成功");
        }
        return ResultUtils.error("添加失败");
    }
    //编辑栋数
    @PutMapping
    @PreAuthorize("hasAuthority('sys:houseBuilding:edit')")
    public ResultVo editHouseBuilding(@RequestBody HouseBuilding houseBuilding){
        boolean b = houseBuildingService.updateById(houseBuilding);
        if(b){
            return ResultUtils.success("修改成功");
        }
        return ResultUtils.error("修改失败");
    }
    //删除栋数
    @DeleteMapping("/{buildId}")
    @PreAuthorize("hasAuthority('sys:houseBuilding:delete')")
    public ResultVo deleteHouseBuilding(@PathVariable("buildId") Long buildId){
        boolean b = houseBuildingService.removeById(buildId);
        if(b){
            return ResultUtils.success("删除成功");
        }
        return ResultUtils.error("删除失败");
    }

    //给单元查询列表
    @GetMapping("/getBuildList")
    public ResultVo getBuildList(){
        List<HouseBuilding> list = houseBuildingService.list();
        return ResultUtils.success("查询成功",list);
    }
}
