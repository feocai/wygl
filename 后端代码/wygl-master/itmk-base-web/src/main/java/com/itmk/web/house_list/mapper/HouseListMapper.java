package com.itmk.web.house_list.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.itmk.web.house_list.entity.HouseList;
import org.apache.ibatis.annotations.Param;

/**
 * @Classname HouseListMapper
 * @Description 房屋列表mapper层
 * @Date 2024/2/28 9:34
 * @Created by wangxiaojie
 */
public interface HouseListMapper extends BaseMapper<HouseList> {
    /**
     * 分页查询
     * @param page 分页参数
     * @param buildName 栋数名称
     * @param unitName 单元名称
     * @param houseNum 房屋编号
     * @param status   使用状态
     * @return
     */
    IPage<HouseList> getList(IPage<HouseList> page, @Param("buildName") String buildName,@Param("unitName") String unitName,@Param("houseNum") String houseNum,@Param("status") String status);
}
