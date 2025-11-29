package com.itmk.web.house_unit.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @Classname HouseUnit
 * @Description 房屋单元实体类
 * @Date 2024/2/27 10:53
 * @Created by wangxiaojie
 */
@Data
@TableName("house_unit")
public class HouseUnit implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long unitId;
    private Long buildId;
    private String unitName;
    @TableField(exist = false)
    private String name;
}
