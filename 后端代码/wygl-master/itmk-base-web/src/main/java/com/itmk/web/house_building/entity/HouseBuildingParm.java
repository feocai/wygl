package com.itmk.web.house_building.entity;

import lombok.Data;

/**
 * @Classname HouseBuildingParm
 * @Description
 * @Date 2024/2/26 10:13
 * @Created by wangxiaojie
 */
@Data
public class HouseBuildingParm {
    //栋数名称
    private String name;
    //栋数类型  0：普通房，1：电梯房
    private String type;
    //页容量
    private Long pageSize;
    //当前页
    private Long currentPage;

}
