package com.itmk.web.house_list.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Classname HouseListParm
 * @Description
 * @Date 2024/2/28 9:44
 * @Created by wangxiaojie
 */
@Data
public class HouseListParm implements Serializable {
    private String buildName; //栋数名称
    private String unitName;  //单元名称
    private String houseNum;  //房屋编号
    private Long currentPage; //当前页
    private Long pageSize; //每页多少条
    private String status; //使用状态 0：未使用， 1：已使用
}
