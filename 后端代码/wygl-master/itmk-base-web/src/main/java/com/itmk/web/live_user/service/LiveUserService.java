package com.itmk.web.live_user.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itmk.web.live_user.entity.AssignHouseParm;
import com.itmk.web.live_user.entity.AssignParkParm;
import com.itmk.web.live_user.entity.LiveUser;
import com.itmk.web.live_user.entity.LiveUserParam;
import org.apache.ibatis.annotations.Param;

/**
 * @Classname LiveUserService
 * @Description
 * @Date 2024/3/5 13:55
 * @Created by wangxiaojie
 */
public interface LiveUserService extends IService<LiveUser> {

    //分页查询
    IPage<LiveUser> getList(LiveUserParam parm);
    //新增
    boolean saveLiveUser(LiveUser liveUser);
    //编辑业主
    void editLiveUser(LiveUser liveUser);
    //通过业主id查询业主信息
    LiveUser getUser(Long userId);
    //分配房屋保存
    void assignHouse(AssignHouseParm parm);
    //分配车位
    void assignPark(AssignParkParm parm);
    //退房
    void returnHouse(AssignHouseParm parm);
    //退车位
    void returnPark(AssignParkParm parm);
    //根据用户名查询用户信息
    LiveUser loadUser(String username);
}
