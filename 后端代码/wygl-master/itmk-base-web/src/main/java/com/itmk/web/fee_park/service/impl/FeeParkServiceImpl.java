package com.itmk.web.fee_park.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itmk.web.fee_park.entity.FeePark;
import com.itmk.web.fee_park.entity.FeeParkParm;
import com.itmk.web.fee_park.mapper.FeeParkMapper;
import com.itmk.web.fee_park.service.FeeParkService;
import org.springframework.stereotype.Service;

/**
 * @Classname FeeParkServiceImpl
 * @Description
 * @Date 2024/3/9 15:54
 * @Created by wangxiaojie
 */
@Service
public class FeeParkServiceImpl extends ServiceImpl<FeeParkMapper, FeePark> implements FeeParkService {
    //分页查询
    @Override
    public IPage<FeePark> getList(FeeParkParm parm) {
        //构造分页参数
        IPage<FeePark> page = new Page<>();
        page.setSize(parm.getPageSize());
        page.setCurrent(parm.getCurrentPage());
        return this.baseMapper.getList(page,parm.getUserName(),parm.getParkName());
    }
}
