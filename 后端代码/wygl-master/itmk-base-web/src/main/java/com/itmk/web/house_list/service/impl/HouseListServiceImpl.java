package com.itmk.web.house_list.service.impl;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itmk.web.house_list.entity.HouseList;
import com.itmk.web.house_list.entity.HouseListParm;
import com.itmk.web.house_list.mapper.HouseListMapper;
import com.itmk.web.house_list.service.HouseListService;
import org.springframework.stereotype.Service;

/**
 * @Classname HouseListServiceImpl
 * @Description 房屋列表service实现层
 * @Date 2024/2/28 9:37
 * @Created by wangxiaojie
 */
@Service
public class HouseListServiceImpl extends ServiceImpl<HouseListMapper, HouseList> implements HouseListService {


    @Override
    public IPage<HouseList> getList(HouseListParm parm) {
        //构造分页参数
        IPage<HouseList> page = new Page<>();
        page.setCurrent(parm.getCurrentPage());
        page.setSize(parm.getPageSize());
        return this.baseMapper.getList(page,parm.getBuildName(),parm.getUnitName(),parm.getHouseNum(),parm.getStatus());
    }
}
