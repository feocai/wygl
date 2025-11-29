package com.itmk.web.house_unit.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.itmk.web.house_unit.entity.HouseUnit;
import org.apache.ibatis.annotations.Param;

/**
 * @Classname HouseUnitMapper
 * @Description 房屋单元mapper层
 * @Date 2024/2/27 10:54
 * @Created by wangxiaojie
 */
public interface HouseUnitMapper extends BaseMapper<HouseUnit> {

    IPage<HouseUnit> getList(IPage<HouseUnit> page, @Param("unitName") String unitName , @Param("buildName") String buildName);
}
