package com.itmk.web.fee_power.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itmk.web.fee_power.entity.FeePower;
import com.itmk.web.fee_power.entity.FeePowerParm;
import com.itmk.web.fee_power.mapper.FeePowerMapper;
import com.itmk.web.fee_power.service.FeePowerService;
import com.itmk.web.live_house.entity.LiveHouse;
import com.itmk.web.live_house.mapper.LiveHouseMapper;
import com.itmk.web.live_house.service.LiveHouseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @Classname FeePowerServiceImpl
 * @Description
 * @Date 2024/3/9 9:38
 * @Created by wangxiaojie
 */
@Service
public class FeePowerServiceImpl extends ServiceImpl<FeePowerMapper, FeePower> implements FeePowerService{


    //分页查询
    @Override
    public IPage<FeePower> getList(FeePowerParm parm) {
        //构造分页参数
        IPage<FeePower> page = new Page<>();
        page.setSize(parm.getPageSize());
        page.setCurrent(parm.getCurrentPage());
        return this.baseMapper.getList(page,parm.getUserName(),parm.getHouseNum());
    }

    //新增电费
    @Override
    public void saveFeePower(FeePower feePower) {
        //保存电费到数据库
        this.baseMapper.insert(feePower);
    }

    //编辑电费
    @Override
    public void updateFeePower(FeePower feePower) {
        //保存电费到数据库
        this.baseMapper.updateById(feePower);
    }
}
