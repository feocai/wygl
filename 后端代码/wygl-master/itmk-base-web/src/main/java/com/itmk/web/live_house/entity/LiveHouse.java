package com.itmk.web.live_house.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @Classname LiveHouse
 * @Description 租户与房屋表
 * @Date 2024/3/3 16:09
 * @Created by wangxiaojie
 */
@Data
@TableName("live_house")
public class LiveHouse implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long liveHouseId; //主键
    private Long userId; //业主id
    private Long houseId; //房屋id
    private String useType; //使用状态 0:使用中，1：解绑
}
