package com.itmk.web.fee_water.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Classname FeeWater
 * @Description
 * @Date 2024/3/9 14:48
 * @Created by wangxiaojie
 */
@Data
@TableName("fee_water")
public class FeeWater implements Serializable {
    @TableId(type = IdType.AUTO)
    private Long waterId;//主键
    private Long houseId;//房屋id
    private Long userId; //用户id
    private String payWaterMonth;//缴费年月
    private BigDecimal payWaterMoney;//缴费金额
    private String waterNum;//表显
    private String payWaterStatus;//缴费状态 0：未缴费，1：已缴费
    private Date payWaterTime;//缴费时间

    @TableField(exist = false)
    private String name;//栋数名称
    @TableField(exist = false)
    private String phone;
    @TableField(exist = false)
    private Long buildId;//栋数id
    @TableField(exist = false)
    private String unitName;
    @TableField(exist = false)
    private Long unitId;
    @TableField(exist = false)
    private String houseNum;
    @TableField(exist = false)
    private String loginName;





}
