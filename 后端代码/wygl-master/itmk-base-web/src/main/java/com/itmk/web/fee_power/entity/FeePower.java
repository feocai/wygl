package com.itmk.web.fee_power.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * @Classname FeePower
 * @Description 电费管理实体类
 * @Date 2024/3/9 9:07
 * @Created by wangxiaojie
 */
@Data
@TableName("fee_power")
public class FeePower implements Serializable {
    @TableId(type= IdType.AUTO)
    private Long powerId; //主键
    private Long houseId; //房屋id
    private Long userId; //业主id
    private String payPowerMonth; //缴费年月
    private BigDecimal payPowerMoney; //缴费金额
    private String powerNum; //表显
    private String payPowerStatus; //缴费状态
    private Date payPowerTime; //缴费时间

    @TableField(exist = false)
    private String phone;
    @TableField(exist = false)
    private String unitName;
    @TableField(exist = false)
    private String houseNum;
    @TableField(exist = false)
    private String name;
    @TableField(exist = false)
    private String loginName;
    @TableField(exist = false)
    private Long unitId;
    @TableField(exist = false)
    private Long buildId;

}
