package com.itmk.web.fee_water.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itmk.web.fee_water.entity.FeeWater;
import com.itmk.web.fee_water.entity.FeeWaterParm;
import com.itmk.web.fee_water.mapper.FeeWaterMapper;
import com.itmk.web.fee_water.service.FeeWaterService;
import org.springframework.stereotype.Service;

/**
 * @Classname FeeWaterServiceImpl
 * @Description
 * @Date 2024/3/9 15:00
 * @Created by wangxiaojie
 */
@Service
public class FeeWaterServiceImpl extends ServiceImpl<FeeWaterMapper, FeeWater> implements FeeWaterService {

    //分页查询
    @Override
    public IPage<FeeWater> getList(FeeWaterParm parm) {
        //构造分页参数
        IPage<FeeWater> page = new Page<>();
        page.setCurrent(parm.getCurrentPage());
        page.setSize(parm.getPageSize());
        return this.baseMapper.getList(page,parm.getUserName(),parm.getHouseNum());
    }
}
