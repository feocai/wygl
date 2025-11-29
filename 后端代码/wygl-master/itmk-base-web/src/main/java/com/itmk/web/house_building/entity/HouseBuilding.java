package com.itmk.web.house_building.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @Classname HouseBuilding
 * @Description 栋数管理实体类
 * @Date 2024/2/26 9:25
 * @Created by wangxiaojie
 */
@Data
@TableName("house_building")
public class HouseBuilding implements Serializable {
    @TableId(type= IdType.AUTO)
    private Long buildId;
    //0：普通房，1：电梯房
    private String type;
    //栋数名称
    private String name;
    //序号
    private Long orderNum;
}
