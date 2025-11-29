package com.itmk.web.house_unit.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itmk.web.house_unit.entity.HouseUnit;
import com.itmk.web.house_unit.entity.HouseUnitParm;

/**
 * @Classname HouseUnitService
 * @Description 房屋单元service层
 * @Date 2024/2/27 10:55
 * @Created by wangxiaojie
 */
public interface HouseUnitService extends IService<HouseUnit> {

    //分页查询
    IPage<HouseUnit> getList(HouseUnitParm houseUnitParm);
}
