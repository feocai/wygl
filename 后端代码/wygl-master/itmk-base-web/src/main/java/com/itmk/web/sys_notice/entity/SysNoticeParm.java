package com.itmk.web.sys_notice.entity;

import lombok.Data;

/**
 * @Classname SysNoticeParm
 * @Description
 * @Date 2024/3/18 14:47
 * @Created by wangxiaojie
 */
@Data
public class SysNoticeParm {
    private Long pageSize;//页容量
    private Long currentPage;//当前页
    private String title;//标题
}
