package com.itmk.web.user.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Classname LoginParm
 * @Description 登录请求参数
 * @Date 2024/2/24 9:42
 * @Created by wangxiaojie
 */
@Data
public class LoginParm  implements Serializable {
    private String username;
    private String password;
    private String userType;
}
