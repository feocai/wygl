package com.itmk.web.fee_water.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itmk.web.fee_water.entity.FeeWater;
import com.itmk.web.fee_water.entity.FeeWaterParm;

/**
 * @Classname FeeWaterService
 * @Description
 * @Date 2024/3/9 14:59
 * @Created by wangxiaojie
 */
public interface FeeWaterService extends IService<FeeWater> {

    //分页查询
    IPage<FeeWater> getList(FeeWaterParm parm);

}
