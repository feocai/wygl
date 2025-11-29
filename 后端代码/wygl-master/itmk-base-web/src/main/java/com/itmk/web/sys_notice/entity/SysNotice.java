package com.itmk.web.sys_notice.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Classname SysNotice
 * @Description
 * @Date 2024/3/18 14:34
 * @Created by wangxiaojie
 */
@Data
@TableName("sys_notice")
public class SysNotice implements Serializable {

    @TableId(type = IdType.AUTO)
    private Long noticeId;//主键
    private Long userId;//用户id
    private String title;//标题
    private String noticeContent;//内容
    @JsonFormat(pattern = "yyy-MM-dd HH:mm:ss")
    private Date createTime;//创建时间


}
