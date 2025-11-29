package com.itmk.web.fee_park.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Classname FeePark
 * @Description
 * @Date 2024/3/9 15:28
 * @Created by wangxiaojie
 */
@Data
@TableName("fee_park")
public class FeePark implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long parkFeeId;
    //租户id
    private Long userId;
    //车位id
    private Long parkId;
    //所属月份
    private String payParkMonth;
    //缴费金额
    private BigDecimal payParkMoney;
    //缴费状态
    private String payParkStatus;//缴费状态 0：未缴费，1：已缴费
    //缴费时间
    private Date payParkTime;

    //数据库没有的字段需要排除
    @TableField(exist = false)
    private String userName;
    @TableField(exist = false)
    private String phone;
    @TableField(exist = false)
    private String loginName;
    @TableField(exist = false)
    private String parkType;//车位类型 0:地上，1：地下
    @TableField(exist = false)
    private String parkName;//车位类型 0:地上，1：地下


}
