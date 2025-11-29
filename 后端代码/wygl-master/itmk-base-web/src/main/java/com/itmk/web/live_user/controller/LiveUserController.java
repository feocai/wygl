package com.itmk.web.live_user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itmk.utils.ResultUtils;
import com.itmk.utils.ResultVo;
import com.itmk.web.fee_park.entity.FeePark;
import com.itmk.web.fee_park.service.FeeParkService;
import com.itmk.web.fee_power.entity.FeePower;
import com.itmk.web.fee_power.service.FeePowerService;
import com.itmk.web.fee_water.entity.FeeWater;
import com.itmk.web.fee_water.service.FeeWaterService;
import com.itmk.web.live_user.entity.AssignHouseParm;
import com.itmk.web.live_user.entity.AssignParkParm;
import com.itmk.web.live_user.entity.LiveUser;
import com.itmk.web.live_user.entity.LiveUserParam;
import com.itmk.web.live_user.service.LiveUserService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.xml.transform.Result;
import java.util.List;

/**
 * @Classname LiveUserController
 * @Description
 * @Date 2024/3/5 13:56
 * @Created by wangxiaojie
 */
@RestController
@RequestMapping("/api/liveUser")
public class LiveUserController {

    @Autowired
    private LiveUserService liveUserService;
    @Autowired
    private FeeWaterService feeWaterService;
    @Autowired
    private FeePowerService feePowerService;
    @Autowired
    private FeeParkService feeParkService;
    @Autowired
    private PasswordEncoder passwordEncoder;
    //分页查询
    @GetMapping("/list")
    public ResultVo getList(LiveUserParam parm){
        IPage<LiveUser>  list = liveUserService.getList(parm);
        return ResultUtils.success("查询成功",list);
    }

    //新增
    @PostMapping
    @PreAuthorize("hasAuthority('sys:liveUser:add')")
    public ResultVo save(@RequestBody LiveUser liveUser){
        //查询用户名是否被占用
        QueryWrapper<LiveUser> query = new QueryWrapper<>();
        query.lambda().eq(LiveUser::getLoginName,liveUser.getUsername());
        LiveUser one = liveUserService.getOne(query);
        if(one != null){
            return ResultUtils.error("登录名被占用");
        }
        //密码需要加密
//        liveUser.setPassword(DigestUtils.md5DigestAsHex(liveUser.getPassword().getBytes()));
        liveUser.setPassword(passwordEncoder.encode(liveUser.getPassword()));
        boolean save = liveUserService.saveLiveUser(liveUser);
        if(save){
            return ResultUtils.success("新增成功");
        }
        return ResultUtils.error("新增失败");
    }
    //编辑
    @PutMapping
    @PreAuthorize("hasAuthority('sys:liveUser:edit')")
    public ResultVo edit(@RequestBody LiveUser liveUser){
        //编辑判断登录名是否被占用
        //查询用户名是否被占用
        QueryWrapper<LiveUser> query = new QueryWrapper<>();
        query.lambda().eq(LiveUser::getUsername,liveUser.getUsername());
        LiveUser one = liveUserService.getOne(query);
        if(one != null && !one.getUserId().equals(liveUser.getUserId())){
            return ResultUtils.error("登录名被占用");
        }
            liveUserService.editLiveUser(liveUser);
            return ResultUtils.success("编辑成功");

    }
    //编辑回显
    @GetMapping("/getUserById")
    public ResultVo getUserById(LiveUser liveUser){
        LiveUser user = liveUserService.getUser(liveUser.getUserId());
        return ResultUtils.success("查询成功",user);
    }

    //分配房屋
    @PostMapping("/assignHouse")
    @PreAuthorize("hasAuthority('sys:liveUser:assignHome')")
    public ResultVo assignHouse(@RequestBody AssignHouseParm parm){
        liveUserService.assignHouse(parm);
        return ResultUtils.success("分配房屋成功");
    }
    //分配车位
    @PostMapping("/assignPark")
    @PreAuthorize("hasAuthority('sys:liveUser:assignCar')")
    public ResultVo assignPark(@RequestBody AssignParkParm parm){
        liveUserService.assignPark(parm);
        return ResultUtils.success("分配车位成功");
    }
    //删除
    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAuthority('sys:liveUser:delete')")
    public ResultVo delete(@PathVariable("userId") Long userId){
        boolean b = liveUserService.removeById(userId);
        if(b){
            return ResultUtils.success("删除成功");
        }
        return ResultUtils.error("删除失败");
    }
    /**
     * 退房：
     * 	1.查询电费、水费是否已经交清；
     *  2.更新租户和房屋关系表状态为解绑；
     *  3.更新房屋表的使用状态为未使用；
     */
    @PostMapping("/returnHouse")
    @PreAuthorize("hasAuthority('sys:liveUser:returnHome')")
    public ResultVo returnHouse(@RequestBody AssignHouseParm parm){
        //1.查询电费、水费是否交清
        //构造查询条件
        QueryWrapper<FeeWater> queryWater = new QueryWrapper<>();
        queryWater.lambda().eq(FeeWater::getHouseId,parm.getHouseId())
                .eq(FeeWater::getUserId,parm.getUserId())
                .eq(FeeWater::getPayWaterStatus,"0");
        List<FeeWater> waterList = feeWaterService.list(queryWater);
        if(waterList != null && waterList.size() > 0){
            return  ResultUtils.error("请交完水费再退房");
        }
        //查询电费
        QueryWrapper<FeePower> queryPower = new QueryWrapper<>();
        queryPower.lambda().eq(FeePower::getHouseId,parm.getHouseId())
                .eq(FeePower::getUserId,parm.getUserId())
                .eq(FeePower::getPayPowerStatus,"0");
        List<FeePower> powerList = feePowerService.list(queryPower);
        if(powerList != null && powerList.size() > 0){
            return ResultUtils.error("请交完电费再退房");
        }
        liveUserService.returnHouse(parm);
        return ResultUtils.success("退房成功");
    }

    /**
     * 退车位：
     * 	1.查询车位费是否已经交清；
     *     2.更新租户和车位的关系为解绑；
     *     3.更新车位的使用状态为未使用；
     */
    @PostMapping("/returnPark")
    @PreAuthorize("hasAuthority('sys:liveUser:returnCar')")
    public ResultVo returnPark(@RequestBody AssignParkParm parm){
        //查询车位费是否已经交清；
        QueryWrapper<FeePark> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(FeePark::getParkId,parm.getParkId())
                .eq(FeePark::getUserId,parm.getUserId())
                .eq(FeePark::getPayParkStatus,"0");
        List<FeePark> list = feeParkService.list(queryWrapper);
        if(list != null && list.size() > 0){
            return ResultUtils.error("请交完车位费再退车位");
        }
        liveUserService.returnPark(parm);
        return ResultUtils.success("退车位成功");
    }

}
