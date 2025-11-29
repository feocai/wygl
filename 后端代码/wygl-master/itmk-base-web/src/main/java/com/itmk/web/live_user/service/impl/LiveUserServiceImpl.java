package com.itmk.web.live_user.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itmk.web.house_list.entity.HouseList;
import com.itmk.web.house_list.mapper.HouseListMapper;
import com.itmk.web.live_house.entity.LiveHouse;
import com.itmk.web.live_house.mapper.LiveHouseMapper;
import com.itmk.web.live_park.entity.LivePark;
import com.itmk.web.live_park.mapper.LiveParkMapper;
import com.itmk.web.live_role.entity.LiveRole;
import com.itmk.web.live_role.mapper.LiveRoleMapper;
import com.itmk.web.live_user.entity.AssignHouseParm;
import com.itmk.web.live_user.entity.AssignParkParm;
import com.itmk.web.live_user.entity.LiveUser;
import com.itmk.web.live_user.entity.LiveUserParam;
import com.itmk.web.live_user.mapper.LiveUserMapper;
import com.itmk.web.live_user.service.LiveUserService;
import com.itmk.web.park_list.entity.ParkList;
import com.itmk.web.park_list.mapper.ParkListMapper;
import com.itmk.web.user.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @Classname LiveUserServiceImpl
 * @Description
 * @Date 2024/3/5 13:55
 * @Created by wangxiaojie
 */
@Service
public class LiveUserServiceImpl extends ServiceImpl<LiveUserMapper, LiveUser> implements LiveUserService {

    @Autowired
    private LiveRoleMapper liveRoleMapper;
    @Autowired
    private LiveHouseMapper liveHouseMapper;
    @Autowired
    private HouseListMapper houseListMapper;
    @Autowired
    private LiveParkMapper liveParkMapper;
    @Autowired
    private ParkListMapper parkListMapper;
    //分页查询
    @Override
    public IPage<LiveUser> getList(LiveUserParam parm) {
        //构造分页对象
        IPage<LiveUser> page = new Page<>();
        page.setCurrent(page.getCurrent());
        page.setSize(parm.getPageSize());
        return this.baseMapper.getList(page,parm.getLoginName(),parm.getPhone());
    }

    //新增
    @Override
    @Transactional
    public boolean saveLiveUser(LiveUser liveUser) {
        //1.保存业主信息
       int a = this.baseMapper.insert(liveUser);
        //维护用户角色信息
        LiveRole liveRole = new LiveRole();
        liveRole.setUserId(liveUser.getUserId());
        liveRole.setRoleId(liveUser.getRoleId());
        int b =  liveRoleMapper.insert(liveRole);
        if(a != 0 && b != 0){
            return true;
        }
        return false;
    }
    //编辑
    @Override
    @Transactional
    public void editLiveUser(LiveUser liveUser) {
        //1.更新业主的表
        this.baseMapper.updateById(liveUser);
        //2.角色关联表的数据删除
        QueryWrapper<LiveRole> query = new QueryWrapper<>();
        query.lambda().eq(LiveRole::getUserId,liveUser.getUserId());
        liveRoleMapper.delete(query);
        //3.插入新的角色
        LiveRole liveRole = new LiveRole();
        liveRole.setUserId(liveUser.getUserId());
        liveRole.setRoleId(liveUser.getRoleId());
        liveRoleMapper.insert(liveRole);
    }
    //通过业主id查询业主信息
    @Override
    public LiveUser getUser(Long userId) {
        return this.baseMapper.getUser(userId);
    }

    //分配房屋保存
    @Override
    @Transactional
    public void assignHouse(AssignHouseParm parm) {
        //1.保存到租户和房屋关系表
        LiveHouse liveHouse = new LiveHouse();
        liveHouse.setUserId(parm.getUserId());
        liveHouse.setHouseId(parm.getHouseId());
        liveHouse.setUseType("0");
        liveHouseMapper.insert(liveHouse);
        //2.更新房屋表的使用状态
        HouseList houseList = new HouseList();
        houseList.setHouseId(parm.getHouseId());
        houseList.setStatus("1"); //0：未使用， 1：已使用
        houseListMapper.updateById(houseList);
    }
    //分配车位
    @Override
    public void assignPark(AssignParkParm parm) {
        //1.保存到租户和车位关系表
        LivePark livePark = new LivePark();
        livePark.setParkId(parm.getParkId());
        livePark.setUserId(parm.getUserId());
        livePark.setLiveStatus("0");//使用状态 0:使用中，1：解绑或退车位
        liveParkMapper.insert(livePark);
        //2.更新车位表的使用状态
        ParkList parkList = new ParkList();
        parkList.setParkId(parm.getParkId());
        parkList.setParkStatus("1"); //使用状态 0：未使用，1：已使用
        parkListMapper.updateById(parkList);
    }
    //退房
    @Override
    @Transactional
    public void returnHouse(AssignHouseParm parm) {
        //1.更新租户和房屋关系表状态为解绑；
        LiveHouse liveHouse = new LiveHouse();
        liveHouse.setUseType("1");
        QueryWrapper<LiveHouse> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(LiveHouse::getHouseId,parm.getHouseId())
                .eq(LiveHouse::getUserId,parm.getUserId())
                .eq(LiveHouse::getUseType,"0");
        liveHouseMapper.update(liveHouse,queryWrapper);
        //2.更新房屋表的使用状态为未使用；
        HouseList houseList = new HouseList();
        houseList.setStatus("0");
        QueryWrapper<HouseList> query = new QueryWrapper<>();
        query.lambda().eq(HouseList::getHouseId,parm.getHouseId());
        houseListMapper.update(houseList,query);
    }

    @Override
    @Transactional
    public void returnPark(AssignParkParm parm) {
        //1.更新租户和车位的关系为解绑；
        LivePark livePark = new LivePark();
        livePark.setLiveStatus("1");
        QueryWrapper<LivePark> queryWrapper = new QueryWrapper<>();
        queryWrapper.lambda().eq(LivePark::getUserId,parm.getUserId())
                .eq(LivePark::getParkId,parm.getParkId())
                .eq(LivePark::getLiveStatus,"0");
        liveParkMapper.update(livePark,queryWrapper);
        //2.更新车位的使用状态为未使用；
        ParkList parkList = new ParkList();
        parkList.setParkStatus("0");
        QueryWrapper<ParkList> query = new QueryWrapper<>();
        query.lambda().eq(ParkList::getParkId,parm.getParkId());
        parkListMapper.update(parkList, query);
    }

    @Override
    public LiveUser loadUser(String username) {
        //构造查询条件
        QueryWrapper<LiveUser> query = new QueryWrapper<>();
        query.lambda().eq(LiveUser::getUsername,username);
        return this.baseMapper.selectOne(query);
    }
}
