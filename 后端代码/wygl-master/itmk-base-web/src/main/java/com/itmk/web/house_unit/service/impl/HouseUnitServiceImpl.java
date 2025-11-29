package com.itmk.web.house_unit.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itmk.web.house_unit.entity.HouseUnit;
import com.itmk.web.house_unit.entity.HouseUnitParm;
import com.itmk.web.house_unit.mapper.HouseUnitMapper;
import com.itmk.web.house_unit.service.HouseUnitService;
import org.springframework.stereotype.Service;

/**
 * @Classname HouseUnitServiceImpl
 * @Description 房屋单元service实现层
 * @Date 2024/2/27 10:56
 * @Created by wangxiaojie
 */
@Service
public class HouseUnitServiceImpl extends ServiceImpl<HouseUnitMapper, HouseUnit> implements HouseUnitService {

    //分页查询
    @Override
    public IPage<HouseUnit> getList(HouseUnitParm houseUnitParm) {
        //构造分页对象
        IPage<HouseUnit> page = new Page<>();
        page.setSize(houseUnitParm.getPageSize());
        page.setCurrent(houseUnitParm.getCurrentPage());
        return this.baseMapper.getList(page,houseUnitParm.getUnitName(),houseUnitParm.getBuildName());
    }
}
