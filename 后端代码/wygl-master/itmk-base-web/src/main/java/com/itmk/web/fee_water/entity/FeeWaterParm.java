package com.itmk.web.fee_water.entity;

import lombok.Data;

/**
 * @Classname FeeWaterParm
 * @Description
 * @Date 2024/3/9 14:56
 * @Created by wangxiaojie
 */
@Data
public class FeeWaterParm {
    private String userName;//业主姓名
    private String houseNum;//房屋编号
    private Long currentPage;//当前页
    private Long pageSize;//页容量
    private Long userId;
}
