package com.itmk.web.live_user.entity;

import lombok.Data;

/**
 * @Classname LiveUserParam
 * @Description
 * @Date 2024/3/5 14:00
 * @Created by wangxiaojie
 */
@Data
public class LiveUserParam {
    private Long pageSize;//页容量
    private Long currentPage;//当前页
    private String loginName;//姓名
    private String phone;//电话

}
