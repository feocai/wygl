package com.itmk.web.fee_power.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itmk.utils.ResultUtils;
import com.itmk.utils.ResultVo;
import com.itmk.web.fee_park.entity.FeePark;
import com.itmk.web.fee_park.entity.FeeParkParm;
import com.itmk.web.fee_power.entity.FeePower;
import com.itmk.web.fee_power.entity.FeePowerParm;
import com.itmk.web.fee_power.service.FeePowerService;
import com.itmk.web.live_house.entity.LiveHouse;
import com.itmk.web.live_house.service.LiveHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.xml.transform.Result;
import java.util.Date;

/**
 * @Classname FeePowerController
 * @Description
 * @Date 2024/3/9 10:20
 * @Created by wangxiaojie
 */
@RestController
@RequestMapping("/api/feePower")
public class FeePowerController {

    @Autowired
    private FeePowerService feePowerService;

    @Autowired
    private LiveHouseService liveHouseService;

    //我的电费
    @GetMapping("/getMyPowerFee")
    public ResultVo getMyPowerFee(FeePowerParm parm){
        //构造查询条件
        QueryWrapper<FeePower> query = new QueryWrapper<>();
        query.lambda().eq(FeePower::getUserId,parm.getUserId());
        //构造分页对象
        IPage<FeePower> page = new Page<>();
        page.setCurrent(parm.getCurrentPage());
        page.setSize(parm.getPageSize());
        IPage<FeePower> list = feePowerService.page(page, query);
        return ResultUtils.success("查询成功",list);
    }

    //分页查询
    @GetMapping("/getList")
    public ResultVo getList(FeePowerParm parm){
        IPage<FeePower> list = feePowerService.getList(parm);
        return ResultUtils.success("查询成功",list);
    }
    //新增
    @PostMapping
    @PreAuthorize("hasAuthority('sys:feePower:add')")
    public ResultVo saveFeePower(@RequestBody FeePower feePower){
        //根据id查询正在使用该房间的用户
        QueryWrapper<LiveHouse> query = new QueryWrapper<>();
        query.lambda().eq(LiveHouse::getHouseId,feePower.getHouseId())
                .eq(LiveHouse::getUseType,"0");
        LiveHouse liveHouse = liveHouseService.getOne(query);
        if(liveHouse  == null){
            return ResultUtils.error("该房间没有人使用，不能添加电费");
        }
        //把查询出来的用户id设置到电放费实体里面
        feePower.setUserId(liveHouse.getUserId());
        feePowerService.saveFeePower(feePower);
        return ResultUtils.success("新增成功");

    }
    //编辑
    @PutMapping
    @PreAuthorize("hasAuthority('sys:feePower:edit')")
    public ResultVo editFeePower(@RequestBody FeePower feePower){
        //根据id查询正在使用该房间的用户
        QueryWrapper<LiveHouse> query = new QueryWrapper<>();
        query.lambda().eq(LiveHouse::getHouseId,feePower.getHouseId())
                .eq(LiveHouse::getUseType,"0");
        LiveHouse liveHouse = liveHouseService.getOne(query);
        if(liveHouse  == null){
            return ResultUtils.error("该房间没有人使用，不能添加电费");
        }
        //把查询出来的用户id设置到电放费实体里面
        feePower.setUserId(liveHouse.getUserId());
        feePowerService.updateFeePower(feePower);
        return ResultUtils.success("编辑成功");
    }
    //删除
    @DeleteMapping("/{powerId}")
    @PreAuthorize("hasAuthority('sys:feePower:delete')")
    public ResultVo deleteFeePower(@PathVariable("powerId") Long powerId){
        //如果已经缴费，不能删除
        QueryWrapper<FeePower> query = new QueryWrapper<>();
        query.lambda().eq(FeePower::getPowerId,powerId).eq(FeePower::getPayPowerStatus,"1");
        FeePower one = feePowerService.getOne(query);
        if(one != null){
            return ResultUtils.error("已缴费，不能删除");
        }
        //删除操作
        boolean b = feePowerService.removeById(powerId);
        if(b){
            return ResultUtils.success("删除成功");
        }
        return ResultUtils.error("删除失败");
    }
    //缴费
    @PostMapping("/payFeePower")
    @PreAuthorize("hasAuthority('sys:feePower:pay')")
    public ResultVo payFeePower(@RequestBody FeePower feePower){
        //缴费时间
        feePower.setPayPowerTime(new Date());
        boolean b = feePowerService.updateById(feePower);
        if(b){
            return ResultUtils.success("缴费成功!");
        }
        return ResultUtils.error("缴费失败");
    }
}
