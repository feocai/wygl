package com.itmk.web.park_list.entity;

import lombok.Data;

/**
 * @Classname ParkListParm
 * @Description
 * @Date 2024/3/3 10:23
 * @Created by wangxiaojie
 */
@Data
public class ParkListParm {
    //车位名称
    private String parkName;
    //车位类型 0：地上，1：地下
    private String parkType;
    //使用状态 0：未使用，1：已使用
    private String parkStatus;
    //当前页
    private Long currentPage;
    //每页多少条
    private Long pageSize;


}
