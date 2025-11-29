package com.itmk.web.live_park.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;

/**
 * @Classname LivePark
 * @Description 租户和车位关系表
 * @Date 2024/3/3 15:59
 * @Created by wangxiaojie
 */
@Data
@TableName("live_park")
public class LivePark implements Serializable {

    @TableId(type= IdType.AUTO)
    private Long liveParkId;//主键
    private Long userId;//业主id
    private Long parkId;//车位id
    private String liveStatus;//使用状态 0:使用中，1：解绑或退车位
}
