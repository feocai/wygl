### 物业管理系统

##### 介绍
`物业管理系统`是一个基于 SpringBoot、vue 的前后端分离的后台管理项目。给物业维修、收租、业主投诉、维修等问题带来很大的方便，从而提高物业的工作效率，
减少人工干预和重复劳动，节省时间和人力资源。该系统主要包括系统管理、房屋管理、业主管理、收费管理、车位管理、维修管理、投诉管理、公告管理等模块。

##### 使用技术
- 学会使用vue-admin-template开发后台系统;
- vue后台系统中登录、权限权限验证流程;
- vue动态菜单、动态路由、按钮权限实现原理，代码实现;
- 通用弹框、tree组件优化、页面优化、通用axios封装、restful api支持封装;
- 前后端分离中的token(JWT)验证;
- 学会使用Spring Boot开发前端后端分离项目;
- 学会Spring Security在前后端分离项目中的使用;

#### 组织结构

``` lua
itmk-base-parent
├── doc   -- 说明文档
├── itmk-base-common -- 工具类及通用代码
└── itmk-base-web 
    ├── config   -- jwt、security、mybatis等相关配置
    └── web      -- web系统接口
```

#### 环境搭建

| 工具          | 版本号      | 
| ------------- | -----------|
| jdk           | 1.8        |
| mysql         | 5.7        | 
| maven         | 3.6        |
| node          | 16.13     |


#### 后端技术

| 技术                    | 说明                | 官网                                           |
| ------------------------| ------------------- | ---------------------------------------------- |
| SpringBoot 2.4.4          | Web应用开发框架      | https://spring.io/projects/spring-boot         |
| SpringSecurity 2.4.4      | 认证和授权框架       | https://spring.io/projects/spring-security     |
| JWT 0.9.0                 | JWT登录支持         | https://github.com/jwtk/jjwt                   |
| Lombok 1.18.12            | Java语言增强库      | https://github.com/rzwitserloot/lombok         |
| mybatisPlus 3.4.2         | mybatis的增强      | https://mybatis.plus/guide/                    |


#### 前端技术

| 技术       | 说明                  | 官网                                   |
| ---------- | --------------------- | -------------------------------------- |
| Vue        2.6.10       | 前端框架              | https://vuejs.org/                     |
| Vue-router 3.0.6        | 路由框架              | https://router.vuejs.org/              |
| Vuex       3.1.0        | 全局状态管理框架      | https://vuex.vuejs.org/                |
| Element    2.13.2       | 前端UI框架            | https://element.eleme.io               |
| Axios      0.18.1       | 前端HTTP框架          | https://github.com/axios/axios         | 
| Js-cookie  2.2.0        | cookie管理工具        | https://github.com/js-cookie/js-cookie |
| nprogress  0.2.0        | 进度条控件            | https://github.com/rstacruz/nprogress  |
