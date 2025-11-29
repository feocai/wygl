package com.itmk.web.menu.entity;

import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @Classname MakeMenuTree
 * @Description 构造树形工具
 * @Date 2024/2/23 15:22
 * @Created by wangxiaojie
 */
public class MakeMenuTree {

    public static List<Menu> maketree(List<Menu> menuList,Long pid){
        //存放组装之后的树
        List<Menu> list = new ArrayList<>();
        //组装树数据
        Optional.ofNullable(menuList).orElse(new ArrayList<>())
                .stream()
                .filter(item -> item != null && item.getParentId() == pid)
                .forEach(dom ->{ //查找每一条数据的下级
                    Menu menu = new Menu();
                    //把过滤之后的数据存放到新的menu里面
                    BeanUtils.copyProperties(dom,menu);
                    //找出当前数据的下级
                    List<Menu> children = maketree(menuList, dom.getMenuId());
                    menu.setChildren(children);
                    list.add(menu);
                });

        return list;
    }
    //生成路由的数据格式
    public static List<RouterVO> makeRouter(List<Menu> menuList, Long pid){
        List<RouterVO> list = new ArrayList<>();
        Optional.ofNullable(menuList).orElse(new ArrayList<>())
                .stream()
                .filter(item -> item != null && item.getParentId() == pid)
                .forEach(item -> {
                    RouterVO router = new RouterVO();
                    router.setName(item.getName());
                    router.setPath(item.getPath());
                    if(item.getParentId() == 0L){
                        router.setComponent("Layout");
                        router.setAlwaysShow(true);
                    }else{
                        router.setComponent(item.getUrl());
                        router.setAlwaysShow(false);
                    }
                    //设置meta
                    router.setMeta(router.new Meta(
                            item.getMenuLabel(),
                            item.getIcon(),
                            item.getMenuCode().split(",")
                    ));
                    //设置children,每一项的下级
                    List<RouterVO> children = makeRouter(menuList, item.getMenuId());
                    router.setChildren(children);
                    list.add(router);
                });
        return list;
    }

}
