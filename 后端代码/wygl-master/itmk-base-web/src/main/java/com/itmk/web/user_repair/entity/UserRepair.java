package com.itmk.web.user_repair.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Classname UserRepair
 * @Description
 * @Date 2024/3/17 20:34
 * @Created by wangxiaojie
 */
@Data
@TableName("user_repair")
public class UserRepair implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long repairId; //主键
    private Long userId; //业主id
    private String phone; //联系电话
    private String repairAddress; //维修地址
    private String repairContent;//维修内容
    private Date conmmitTime; //保修时间
    private String status; // 维修状态 0：未维修，1：已维修

}
