package com.itmk.web.fee_park.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.itmk.web.fee_park.entity.FeePark;
import org.apache.ibatis.annotations.Param;

/**
 * @Classname FeeParkMapper
 * @Description
 * @Date 2024/3/9 15:46
 * @Created by wangxiaojie
 */
public interface FeeParkMapper extends BaseMapper<FeePark> {
    //分页查询
    IPage<FeePark> getList(IPage<FeePark> page, @Param("userName") String userName,@Param("parkName") String parkName);

}
