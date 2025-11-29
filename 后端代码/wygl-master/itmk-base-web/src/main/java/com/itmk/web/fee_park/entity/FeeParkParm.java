package com.itmk.web.fee_park.entity;

import lombok.Data;

/**
 * @Classname FeeParkParm
 * @Description
 * @Date 2024/3/9 15:45
 * @Created by wangxiaojie
 */
@Data
public class FeeParkParm {
    private String userName; //业主姓名
    private String parkName; //车位名称
    private Long currentPage; //当前页
    private Long pageSize;  //页容量
    private Long userId;
}
