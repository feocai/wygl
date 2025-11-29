package com.itmk.web.park_list.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.itmk.web.park_list.entity.ParkList;
import com.itmk.web.park_list.entity.ParkListParm;
import com.itmk.web.park_list.mapper.ParkListMapper;
import com.itmk.web.park_list.service.ParkListService;
//import com.sun.xml.internal.messaging.saaj.packaging.mime.util.QEncoderStream;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

/**
 * @Classname ParkListServiceImpl
 * @Description
 * @Date 2024/2/29 17:00
 * @Created by wangxiaojie
 */
@Service
public class ParkListServiceImpl extends ServiceImpl<ParkListMapper, ParkList> implements ParkListService {


    //分页查询
    @Override
    public IPage<ParkList> getList(ParkListParm parm) {
        //构造分页参数
        QueryWrapper<ParkList> query = new QueryWrapper<>();
       if(StringUtils.isNotEmpty(parm.getParkName())){
           query.lambda().like(ParkList::getParkName,parm.getParkName());
       }
       if(StringUtils.isNotEmpty(parm.getParkType())){
           query.lambda().eq(ParkList::getParkType,parm.getParkType());
       }
       if(StringUtils.isNotEmpty(parm.getParkStatus())){
           query.lambda().eq(ParkList::getParkStatus,parm.getParkStatus());
       }
        //构造分页参数
        IPage<ParkList> page = new Page<>();
        page.setSize(parm.getPageSize());
        page.setCurrent(page.getCurrent());
        return this.baseMapper.selectPage(page,query);
    }
}
