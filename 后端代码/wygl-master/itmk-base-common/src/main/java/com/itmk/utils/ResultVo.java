package com.itmk.utils;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Classname ResultVo
 * @Description
 * @Date 2024/2/21 17:23
 * @Created by wangxiaojie
 */
@Data //自动生成 get 和 set 方法
@AllArgsConstructor //生成构造器方法
public class ResultVo<T> {
    private String msg;
    private int code;
    private T data;
}
