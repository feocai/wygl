package com.itmk.web.house_building.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itmk.web.house_building.entity.HouseBuilding;
import com.itmk.web.house_building.entity.HouseBuildingParm;

/**
 * @Classname HouseBuild
 * @Description
 * @Date 2024/2/26 9:27
 * @Created by wangxiaojie
 */
public interface HouseBuildingService extends IService<HouseBuilding> {

    //分页插叙
    IPage<HouseBuilding> getList(HouseBuildingParm houseBuildingParm);

}
