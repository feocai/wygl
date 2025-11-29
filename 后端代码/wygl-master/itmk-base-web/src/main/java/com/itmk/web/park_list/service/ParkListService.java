package com.itmk.web.park_list.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itmk.web.park_list.entity.ParkList;
import com.itmk.web.park_list.entity.ParkListParm;

/**
 * @Classname ParkListService
 * @Description
 * @Date 2024/2/29 17:00
 * @Created by wangxiaojie
 */
public interface ParkListService extends IService<ParkList> {
    //分页查询
    IPage<ParkList> getList(ParkListParm parm);
}
