package com.itmk.web.house_list.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @Classname HouseList
 * @Description 房屋列表实体类
 * @Date 2024/2/28 9:33
 * @Created by wangxiaojie
 */
@Data
@TableName("house_list")
public class HouseList implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long houseId; //主键
    private Long unitId; //单元id
    private String houseNum; //房屋编号
    private String houseArea; //使用面积
    private String status; //使用状态 0：未使用， 1：已使用
    @TableField(exist = false)
    private Long buildId;
    @TableField(exist = false)
    private String name; //栋数名称
    @TableField(exist = false)
    private String unitName; //单元名称
    @TableField(exist = false)
    private String type; //单元名称

}
