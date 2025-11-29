package com.itmk.web.house_unit.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.itmk.utils.ResultUtils;
import com.itmk.utils.ResultVo;
import com.itmk.web.house_unit.entity.HouseUnit;
import com.itmk.web.house_unit.entity.HouseUnitParm;
import com.itmk.web.house_unit.service.HouseUnitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @Classname HouseUnitController
 * @Description 房屋单元控制层
 * @Date 2024/2/27 10:57
 * @Created by wangxiaojie
 */
@RestController
@RequestMapping("/api/houseUnit")
public class HouseUnitController {

    @Autowired
    private HouseUnitService houseUnitService;

    //分页查询
    @GetMapping("/list")
    public ResultVo getList(HouseUnitParm HouseUnitParm){
        IPage<HouseUnit> list = houseUnitService.getList(HouseUnitParm);
        return ResultUtils.success("查询成功",list);
    }

    //添加
    @PostMapping
    @PreAuthorize("hasAuthority('sys:houseUnit:add')")
    public ResultVo saveHouseUnit(@RequestBody HouseUnit houseUnit){
        boolean save = houseUnitService.save(houseUnit);
        if(save){
            return ResultUtils.success("添加成功");
        }
        return ResultUtils.error("添加失败");
    }

    //修改
    @PutMapping
    @PreAuthorize("hasAuthority('sys:houseUnit:edit')")
    public ResultVo editHouseUnit(@RequestBody HouseUnit houseUnit){
        boolean b = houseUnitService.updateById(houseUnit);
        if(b){
            return ResultUtils.success("修改成功");
        }
        return ResultUtils.error("修改失败");
    }
    //删除
    @DeleteMapping("/{unitId}")
    @PreAuthorize("hasAuthority('sys:houseUnit:delete')")
    public ResultVo deleteHouseUnit(@PathVariable Long unitId){
        boolean b = houseUnitService.removeById(unitId);
        if(b){
            return ResultUtils.success("删除成功");
        }
        return ResultUtils.error("删除失败");
    }
    //根据栋数id查询单元
    @GetMapping("/getUnitByBuildId")
    public ResultVo getUnitListByBuildId(HouseUnit houseUnit){
        //构造查询条件
        QueryWrapper<HouseUnit> query = new QueryWrapper<>();
        query.lambda().eq(HouseUnit::getBuildId,houseUnit.getBuildId());
        List<HouseUnit> list = houseUnitService.list(query);
        return ResultUtils.success("查询成功",list);
    }
}
