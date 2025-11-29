package com.itmk.web.menu.entity;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Classname RouterVO
 * @Description 路由需要的格式
 * @Date 2024/3/19 15:11
 * @Created by wangxiaojie
 */
@Data
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class RouterVO {

    private String path;

    private String component;

    private boolean alwaysShow;

    private String name;

    private Meta meta;

    @Data
    @AllArgsConstructor
    public class Meta {
        private String title;
        private String icon;
        private Object[] roles;
    }
    private List<RouterVO> children =new ArrayList<>();

}
