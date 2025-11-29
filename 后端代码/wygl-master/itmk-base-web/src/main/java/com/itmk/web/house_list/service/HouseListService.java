package com.itmk.web.house_list.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itmk.web.house_list.entity.HouseList;
import com.itmk.web.house_list.entity.HouseListParm;
import org.apache.ibatis.annotations.Param;

/**
 * @Classname HouseListService
 * @Description 房屋列表service层
 * @Date 2024/2/28 9:36
 * @Created by wangxiaojie
 */
public interface HouseListService extends IService<HouseList> {

    IPage<HouseList> getList(HouseListParm houseListParm);

}
