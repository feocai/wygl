package com.itmk.web.fee_water.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itmk.utils.ResultUtils;
import com.itmk.utils.ResultVo;
import com.itmk.web.fee_power.entity.FeePower;
import com.itmk.web.fee_power.entity.FeePowerParm;
import com.itmk.web.fee_water.entity.FeeWater;
import com.itmk.web.fee_water.entity.FeeWaterParm;
import com.itmk.web.fee_water.service.FeeWaterService;
import com.itmk.web.live_house.entity.LiveHouse;
import com.itmk.web.live_house.service.LiveHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.xml.transform.Result;
import java.util.Date;
import java.util.List;

/**
 * @Classname FeeWaterController
 * @Description
 * @Date 2024/3/9 15:01
 * @Created by wangxiaojie
 */
@RestController
@RequestMapping("/api/feeWater")
public class FeeWaterController {

    @Autowired
    private FeeWaterService feeWaterService;
    @Autowired
    private LiveHouseService liveHouseService;

    //我的水费
    @GetMapping("/getMyWaterFee")
    public ResultVo getMyWaterFee(FeePowerParm parm){
        //构造查询条件
        QueryWrapper<FeeWater> query = new QueryWrapper<>();
        query.lambda().eq(FeeWater::getUserId,parm.getUserId());
        //构造分页对象
        IPage<FeeWater> page = new Page<>();
        page.setCurrent(parm.getCurrentPage());
        page.setSize(parm.getPageSize());
        IPage<FeeWater> list = feeWaterService.page(page, query);
        return ResultUtils.success("查询成功",list);
    }

    //分页查询
    @GetMapping("/getList")
    public ResultVo getList(FeeWaterParm parm){
        IPage<FeeWater> list = feeWaterService.getList(parm);
        return ResultUtils.success("查询成功",list);
    }
    //新增
    @PostMapping
    @PreAuthorize("hasAuthority('sys:feeWater:add')")
    public ResultVo saveFeeWater(@RequestBody FeeWater feeWater){
        //根据id查询正在使用该房间的用户
        QueryWrapper<LiveHouse> query = new QueryWrapper<>();
        query.lambda().eq(LiveHouse::getHouseId,feeWater.getHouseId())
                .eq(LiveHouse::getUseType,"0");
        LiveHouse liveHouse = liveHouseService.getOne(query);
        if(liveHouse  == null){
            return ResultUtils.error("该房间没有人使用，不能添加电费");
        }
        //把查询出来的用户id设置到电放费实体里面
        feeWater.setUserId(liveHouse.getUserId());
        boolean save = feeWaterService.save(feeWater);
        if(save){
            return ResultUtils.success("新增成功");
        }
        return ResultUtils.error("新增失败");
    }
    //编辑
    @PutMapping
    @PreAuthorize("hasAuthority('sys:feeWater:edit')")
    public ResultVo editFeeWater(@RequestBody FeeWater feeWater){
        //根据id查询正在使用该房间的用户
        QueryWrapper<LiveHouse> query = new QueryWrapper<>();
        query.lambda().eq(LiveHouse::getHouseId,feeWater.getHouseId())
                .eq(LiveHouse::getUseType,"0");
        LiveHouse liveHouse = liveHouseService.getOne(query);
        if(liveHouse  == null){
            return ResultUtils.error("该房间没有人使用，不能添加电费");
        }
        //把查询出来的用户id设置到电放费实体里面
        feeWater.setUserId(liveHouse.getUserId());
        boolean b = feeWaterService.updateById(feeWater);
        if(b){
            return ResultUtils.success("编辑成功");
        }
        return ResultUtils.error("编辑失败");
    }
    //删除
    @DeleteMapping("/{waterId}")
    @PreAuthorize("hasAuthority('sys:feeWater:delete')")
    public ResultVo deleteFeeWater(@PathVariable("waterId") Long waterId){
        //如果已经缴费，不能删除
        QueryWrapper<FeeWater> query = new QueryWrapper<>();
        query.lambda().eq(FeeWater::getWaterId,waterId).eq(FeeWater::getPayWaterStatus,"1");
        FeeWater one = feeWaterService.getOne(query);
        if(one != null){
            return ResultUtils.error("已缴费，不能删除");
        }
        //删除操作
        boolean b = feeWaterService.removeById(waterId);
        if(b){
            return ResultUtils.success("删除成功");
        }
        return ResultUtils.error("删除失败");
    }

    //缴费
    @PostMapping("/payFeeWater")
    @PreAuthorize("hasAuthority('sys:feeWater:pay')")
    public ResultVo payFeeWater(@RequestBody FeeWater feeWater){
        feeWater.setPayWaterTime(new Date());
        boolean b = feeWaterService.updateById(feeWater);
        if(b){
            return ResultUtils.success("缴费成功");
        }
        return ResultUtils.error("缴费失败");
    }
}
