package com.itmk.web.live_user.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.io.Serializable;
import java.util.Collection;

/**
 * @Classname LiveUser
 * @Description
 * @Date 2024/3/5 13:44
 * @Created by wangxiaojie
 */
@Data
@TableName("live_user")
public class LiveUser implements Serializable , UserDetails {
    @TableId(type= IdType.AUTO)
    private Long userId;
    private String username;//登录名
    private String phone;//联系电话
    private String sex;//性别 0：男，1：女
    private String loginName;//姓名
    private String password;//登录密码
    private String status;//账户状态 0:启用，1：禁用


    @TableField(exist = false)
    private Long roleId;//角色id不属于业主表，需要排除
    @TableField(exist = false)
    private String houseNum;//不属于业主表，所以需要排除
    @TableField(exist = false)
    private String houseArea;//不属于业主表，所以需要排除
    @TableField(exist = false)
    private String unitName;//不属于业主表，所以需要排除
    @TableField(exist = false)
    private String name;//不属于业主表，所以需要排除
    @TableField(exist = false)
    private String parkName;//不属于业主表，所以需要排除
    @TableField(exist = false)
    private Long houseId; //不属于业主表，所以需要排除
    @TableField(exist = false)
    private Long parkId;//不属于业主表，所以需要排除
    @TableField(exist = false)
    private String useType;//不属于业主表，所以需要排除
    @TableField(exist = false)
    private String liveStatus;//不属于业主表，所以需要排除

    //下面的字段，属于spring security的UserDetails的字段
    //帐户是否过期(1 未过期，0已过期)
    private boolean isAccountNonExpired = true;
    //帐户是否被锁定(1 未锁定，0已锁定)
    private boolean isAccountNonLocked = true;
    //密码是否过期(1 未过期，0已过期)
    private boolean isCredentialsNonExpired = true;
    //帐户是否可用(1 可用，0 删除用户)
    private boolean isEnabled = true;
    //由于authorities不是数据库里面的字段，所以要排除他，不然mybatis-plus找不到该字段会报错
    @TableField(exist = false)
    Collection<? extends GrantedAuthority> authorities;
}
