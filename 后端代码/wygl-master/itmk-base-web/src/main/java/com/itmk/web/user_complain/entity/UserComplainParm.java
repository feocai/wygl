package com.itmk.web.user_complain.entity;

import lombok.Data;

/**
 * @Classname UserComplainParm
 * @Description
 * @Date 2024/3/16 9:34
 * @Created by wangxiaojie
 */
@Data
public class UserComplainParm {

    private Long pageSize;//页容量
    private Long currentPage;//当前页
    private String title;//投诉标题
    private String complaintContent;//投诉内容
    private Long userId;//业主id
}
