package com.itmk.web.live_user.entity;

import lombok.Data;

import java.io.Serializable;

/**
 * @Classname AssignHouseParm
 * @Description
 * @Date 2024/3/8 9:53
 * @Created by wangxiaojie
 */
@Data
public class AssignHouseParm implements Serializable {
    private Long userId;
    private Long houseId;

}
