package com.itmk.web.fee_power.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.itmk.web.fee_power.entity.FeePower;
import org.apache.ibatis.annotations.Param;

/**
 * @Classname FeePowerMapper
 * @Description
 * @Date 2024/3/9 9:35
 * @Created by wangxiaojie
 */
public interface FeePowerMapper extends BaseMapper<FeePower> {

    //分页查询
    IPage<FeePower> getList(IPage<FeePower> page,@Param("userName") String userName, @Param("houseNum")String houseNum);
}
