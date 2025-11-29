package com.itmk.web.fee_power.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itmk.web.fee_power.entity.FeePower;
import com.itmk.web.fee_power.entity.FeePowerParm;

/**
 * @Classname FeePowerService
 * @Description
 * @Date 2024/3/9 9:37
 * @Created by wangxiaojie
 */
public interface FeePowerService extends IService<FeePower> {
    //分页查询
    IPage<FeePower> getList(FeePowerParm parm);

    //新增电费
    void saveFeePower(FeePower feePower);
    //编辑电费
    void updateFeePower(FeePower feePower);

}
