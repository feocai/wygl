package com.itmk.web.user_repair.entity;

import lombok.Data;

/**
 * @Classname UserRepairParm
 * @Description
 * @Date 2024/3/17 20:40
 * @Created by wangxiaojie
 */
@Data
public class UserRepairParm {
    private Long pageSize;//页容量
    private Long currentPage;//当前页
    private String repairContent;//维修内容
    private Long userId; //业主id
}
