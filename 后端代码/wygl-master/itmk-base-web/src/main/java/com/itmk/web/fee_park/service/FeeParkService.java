package com.itmk.web.fee_park.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.itmk.web.fee_park.entity.FeePark;
import com.itmk.web.fee_park.entity.FeeParkParm;

/**
 * @Classname FeeParkService
 * @Description
 * @Date 2024/3/9 15:48
 * @Created by wangxiaojie
 */
public interface FeeParkService extends IService<FeePark> {
    //分页查询
    IPage<FeePark> getList(FeeParkParm parm);
}
