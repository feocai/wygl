package com.itmk.web.user.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Classname ChangePassword
 * @Description
 * @Date 2024/3/22 13:12
 * @Created by wangxiaojie
 */
@Data
public class ChangePassword implements Serializable {
    private Long userId;
    //姓名
    private String oldPassword;
    //登录密码
    private String newPassword;
}
