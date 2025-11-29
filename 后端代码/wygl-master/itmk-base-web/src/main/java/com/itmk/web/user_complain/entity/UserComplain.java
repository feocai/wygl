package com.itmk.web.user_complain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Classname UserComplain
 * @Description
 * @Date 2024/3/16 9:24
 * @Created by wangxiaojie
 */
@Data
@TableName("user_complaint")
public class UserComplain implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long complaintId;//主键
    private Long userId;//业主id
    private String title;//投诉标题
    private String complaintContent;//投诉内容
    private Date createTime;//投诉时间
    private String status;//处理状态 0:未处理，1：已处理
}
