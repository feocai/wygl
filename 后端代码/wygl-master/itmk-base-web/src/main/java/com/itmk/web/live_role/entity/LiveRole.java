package com.itmk.web.live_role.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @Classname LiveRole
 * @Description 业主角色关联表
 * @Date 2024/3/5 18:50
 * @Created by wangxiaojie
 */
@Data
@TableName("live_role")
public class LiveRole {
    @TableId(type= IdType.AUTO)
    private Long livRoleId;//主键
    private Long roleId;//角色id
    private Long userId;//用户id
}
