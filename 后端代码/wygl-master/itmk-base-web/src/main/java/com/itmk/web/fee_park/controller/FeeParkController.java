package com.itmk.web.fee_park.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itmk.utils.ResultUtils;
import com.itmk.utils.ResultVo;
import com.itmk.web.fee_park.entity.FeePark;
import com.itmk.web.fee_park.entity.FeeParkParm;
import com.itmk.web.fee_park.service.FeeParkService;
import com.itmk.web.fee_power.entity.FeePower;
import com.itmk.web.live_park.entity.LivePark;
import com.itmk.web.live_park.service.LiveParkService;
import org.apache.ibatis.annotations.ResultType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.Date;

/**
 * @Classname FeeParkController
 * @Description
 * @Date 2024/3/9 15:55
 * @Created by wangxiaojie
 */
@RestController
@RequestMapping("/api/feePark")
public class FeeParkController {

    @Autowired
    private FeeParkService feeParkService;
    @Autowired
    private LiveParkService liveParkService;

    //我的停车费
    @GetMapping("/getMyParkFee")
    public ResultVo getMyParkFee(FeeParkParm parm){
        //构造查询条件
        QueryWrapper<FeePark> query = new QueryWrapper<>();
        query.lambda().eq(FeePark::getUserId,parm.getUserId());
        //构造分页对象
        IPage<FeePark> page = new Page<>();
        page.setCurrent(parm.getCurrentPage());
        page.setSize(parm.getPageSize());
        IPage<FeePark> list = feeParkService.page(page, query);
        return ResultUtils.success("查询成功",list);
    }


    //分页查询
    @GetMapping("/getList")
    public ResultVo getList(FeeParkParm parm){
        IPage<FeePark> list = feeParkService.getList(parm);
        return ResultUtils.success("查询成功",list);
    }

    //新增
    @PostMapping
    @PreAuthorize("hasAuthority('sys:feePark:add')")
    public ResultVo saveFeePark(@RequestBody FeePark feePark){
        //通过车位id查询使用该车位的用户id
        QueryWrapper<LivePark> query = new QueryWrapper<>();
        query.lambda().eq(LivePark::getParkId,feePark.getParkId())
                      .eq(LivePark::getLiveStatus,"0");
        LivePark one = liveParkService.getOne(query);
        //如果为空说明该车位没有人使用
        if(one == null){
            return ResultUtils.error("该车位没有人使用，不能添加停车费");
        }
        feePark.setUserId(one.getUserId());
        boolean save = feeParkService.save(feePark);
        if(save){
            return ResultUtils.success("新增成功");
        }
        return ResultUtils.error("新增失败");
    }
    //编辑
    @PutMapping
    @PreAuthorize("hasAuthority('sys:feePark:edit')")
    public ResultVo editFeePark(@RequestBody FeePark feePark){
        //通过车位id查询使用该车位的用户id
        QueryWrapper<LivePark> query = new QueryWrapper<>();
        query.lambda().eq(LivePark::getParkId,feePark.getParkId())
                .eq(LivePark::getLiveStatus,"0");
        LivePark one = liveParkService.getOne(query);
        //如果为空说明该车位没有人使用
        if(one == null){
            return ResultUtils.success("该车位没有人使用，不能添加停车费");
        }
        feePark.setUserId(one.getUserId());
        boolean b = feeParkService.updateById(feePark);
        if(b){
            return ResultUtils.success("编辑成功");
        }
        return ResultUtils.error("编辑失败");
    }
    //删除
    @DeleteMapping("/{parkFeeId}")
    @PreAuthorize("hasAuthority('sys:feePark:delete')")
    public ResultVo deleteFeePark(@PathVariable("parkFeeId") Long parkFeeId){
        //如果是已缴费，不能删除
        QueryWrapper<FeePark> query = new QueryWrapper<>();
        query.lambda().eq(FeePark::getParkFeeId,parkFeeId)
                .eq(FeePark::getPayParkStatus,"1");
        FeePark one = feeParkService.getOne(query);
        if(one != null){
            return ResultUtils.error("已缴费，不能删除");
        }
        boolean b = feeParkService.removeById(parkFeeId);
        if(b){
            return ResultUtils.success("删除成功");
        }
        return ResultUtils.error("删除失败");
    }
    //缴费
    @PostMapping("/payFeePark")
    @PreAuthorize("hasAuthority('sys:feePark:pay')")
    public ResultVo payFeePark(@RequestBody FeePark feePark){
        feePark.setPayParkTime(new Date());
        boolean b = feeParkService.updateById(feePark);
        if(b){
            return ResultUtils.success("缴费成功");
        }
        return ResultUtils.error("缴费失败");
    }
}
