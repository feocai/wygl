package com.itmk.web.user.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.itmk.config.jwt.JwtUtils;
import com.itmk.utils.ResultUtils;
import com.itmk.utils.ResultVo;
import com.itmk.web.live_user.entity.LiveUser;
import com.itmk.web.live_user.service.LiveUserService;
import com.itmk.web.menu.entity.MakeMenuTree;
import com.itmk.web.menu.entity.Menu;
import com.itmk.web.menu.entity.RouterVO;
import com.itmk.web.menu.service.MenuService;
import com.itmk.web.user.entity.*;
import com.itmk.web.user.service.UserService;
import com.itmk.web.user_role.entity.UserRole;
import io.jsonwebtoken.Claims;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.SecurityContextLogoutHandler;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.transform.Result;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Classname UserController
 * @Description 员工管理控制器
 * @Date 2024/2/21 18:14
 * @Created by wangxiaojie
 */
@RestController
@RequestMapping("/api/user")
public class UserController {

    @Autowired
    private UserService userService;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private LiveUserService liveUserService;
    @Autowired
    private MenuService menuService;

    //重置密码
    @PostMapping("/resetPassword")
    public ResultVo resetPassword(@RequestBody ChangePassword user,HttpServletRequest request){
        //获取token
        String token = request.getHeader("token");
        //获取用类型
        Claims claims = jwtUtils.getClaimsFromToken(token);
        //用户类型
        Object userType = claims.get("userType");
        if("0".equals(userType)){//0:业主
            LiveUser liveUser = liveUserService.getById(user.getUserId());
            //原来的密码
            String dataOldPassword = liveUser.getPassword();
            boolean matches = passwordEncoder.matches(user.getOldPassword(), dataOldPassword);
            if(!matches){
                return ResultUtils.error("旧密码错误");
            }
            LiveUser liveUser1 = new LiveUser();
            liveUser1.setUserId(user.getUserId());
            liveUser1.setPassword(passwordEncoder.encode(user.getNewPassword()));
            boolean b = liveUserService.updateById(liveUser1);
            if(b){
                return ResultUtils.success("密码修改成功");
            }
            return ResultUtils.error("密码修改失败");
        }else{//0:物主
            User user1 = userService.getById(user.getUserId());
            //原来的密码
            String dataOldPassword = user1.getPassword();
            boolean matches = passwordEncoder.matches(user.getOldPassword(), dataOldPassword);
            if(!matches){
                return ResultUtils.error("旧密码错误");
            }
            User liveUserInfo = new User();
            liveUserInfo.setUserId(user.getUserId());
            liveUserInfo.setPassword(passwordEncoder.encode(user.getNewPassword()));
            boolean b = userService.updateById(liveUserInfo);
            if(b){
                return ResultUtils.success("密码修改成功");

            }
            return ResultUtils.error("密码修改失败");
        }
    }
    //退出登录
    @PostMapping("/loginOut")
    public ResultVo loginOut(HttpServletRequest request, HttpServletResponse response){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if(authentication != null){
            new SecurityContextLogoutHandler().logout(request,response,authentication);
        }
        return ResultUtils.success("退出登录成功");
    }
    //获取用户信息
    @GetMapping("/getInfo")
    public ResultVo getInfo(User user, HttpServletRequest request){
        //从头部获取token
        String token = request.getHeader("token");
        Claims claims = jwtUtils.getClaimsFromToken(token);
        Object userType = claims.get("userType");
        if(userType.equals("0")){ //0：业主
            LiveUser liveUser = liveUserService.getById(user.getUserId());
            UserInfo userInfo = new UserInfo();
            userInfo.setId(liveUser.getUserId());
            userInfo.setName(liveUser.getUsername());
            userInfo.setAvatar("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
            //查询业主的权限信息
            List<Menu> menuList = menuService.getMenuByUserIdForLiveUser(liveUser.getUserId());
            //获取权限字段
            List<String> collect = menuList.stream().filter(item -> item != null).map(item -> item.getMenuCode()).filter(item -> item != null).collect(Collectors.toList());
            //转成数组
            String[] strings = collect.toArray(new String[collect.size()]);
            userInfo.setRoles(strings);
            return ResultUtils.success("获取用户信息成功",userInfo);
        }else{ //物主
            //根据用户id查询,区分查的是哪一个
            User user1 = userService.getById(user.getUserId());
            UserInfo userInfo = new UserInfo();
            userInfo.setId(user1.getUserId());
            userInfo.setName(user1.getUsername());
            userInfo.setAvatar("https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
            //根据用户id查询权限字段
            //查询用户权限信息
            List<Menu> menuList = menuService.getMenuByUserId(user.getUserId());
            //获取权限字段
            List<String> collect = menuList.stream().filter(item -> item != null).map(item -> item.getMenuCode()).filter(item -> item != null).collect(Collectors.toList());
            //转成数组
            String[] strings = collect.toArray(new String[collect.size()]);
            userInfo.setRoles(strings);
            return ResultUtils.success("获取用户信息成功",userInfo);
        }
    }


    //    //用户登录
//    @PostMapping("/login")
//    public ResultVo login(@RequestBody LoginParm parm){
//        if(StringUtils.isEmpty(parm.getUsername()) || StringUtils.isEmpty(parm.getPassword()) || StringUtils.isEmpty(parm.getUserType())){
//            return ResultUtils.error("用户名、密码或用户类型不能为空");
//        }
//        //加密前端传来的密码
//        String password = DigestUtils.md5DigestAsHex(parm.getPassword().getBytes());
//        //构造查询条件
//        QueryWrapper<User> query = new QueryWrapper<>();
//        query.lambda().eq(User::getLoginName,parm.getUsername())
//                .eq(User::getPassword,password);
//
//        /** 现在用户类型还没有使用到
//         * todo
//         */
//        User user = userService.getOne(query);
//        if(user == null){
//            return  ResultUtils.error("用户名、密码或用户类型错误");
//        }
//        //生成token返回前端
//        String token = jwtUtils.generateToken(user.getUsername());
//        //获取token过期时间
//        Long time = jwtUtils.getExpireTime(token);
//        //封装返回数据
//        LoginResult result = new LoginResult();
//        result.setUserId(user.getUserId());
//         result.setToken(token);
//         result.setExpireTime(time);
//        return ResultUtils.success("登录成功",result);
//    }
    //用户登录
    @PostMapping("/login")
    public ResultVo login(@RequestBody LoginParm parm) {
        if (StringUtils.isEmpty(parm.getUsername()) || StringUtils.isEmpty(parm.getPassword()) || StringUtils.isEmpty(parm.getUserType())) {
            return ResultUtils.error("用户名、密码或用户类型不能为空");
        }
        //加密后的密码
        String encode = passwordEncoder.encode(parm.getPassword());
        //spring security需要的token
        UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(parm.getUsername()+":"+parm.getUserType(),parm.getPassword());
        //把用户的相关信息交给spring security进行相关的认证
        Authentication authenticate = authenticationManager.authenticate(usernamePasswordAuthenticationToken);
        SecurityContextHolder.getContext().setAuthentication(authenticate);
        //用户信息
        if("0".equals(parm.getUserType())){ //业主
            LiveUser liveUser = (LiveUser) authenticate.getPrincipal();
            //生成token返回给前端
            String liveToken = jwtUtils.generateToken(liveUser.getUsername(),parm.getUserType());
            //获取token过期的时间
            Long time = jwtUtils.getExpireTime(liveToken);
            LoginResult result = new LoginResult();
            result.setUserId(liveUser.getUserId());
            result.setToken(liveToken);
            result.setExpireTime(time);
            return ResultUtils.success("登录成功",result);
        }else if("1".equals(parm.getUserType())){ //物主
            User user = (User) authenticate.getPrincipal();
            //生成token返回给前端
            String token = jwtUtils.generateToken(user.getUsername(),parm.getUserType());
            //获取token过期的时间
            Long time = jwtUtils.getExpireTime(token);
            LoginResult result = new LoginResult();
            result.setUserId(user.getUserId());
            result.setToken(token);
            result.setExpireTime(time);
            return ResultUtils.success("登录成功",result);
        }else{
            return ResultUtils.error("您选择的用户类型不存在!");
        }
    }

    /**
     * 新增员工
     *
     * @param user
     * @return
     */
    @PostMapping
    @PreAuthorize("hasAuthority('sys:user:add')")
    public ResultVo addUser(@RequestBody User user) {
        //判断登录名唯一性
        if (StringUtils.isNotEmpty(user.getUsername())) {
            QueryWrapper<User> query = new QueryWrapper<>();
            query.lambda().eq(User::getUsername, user.getUsername());
            User one = userService.getOne(query);
            if (one != null) {
                return ResultUtils.error("用户名已经被占用!", 500);
            }
        }
        //如果密码存在，加密
        if (StringUtils.isNotEmpty(user.getPassword())) {
//            user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
            user.setPassword(passwordEncoder.encode(user.getPassword()));

        }
        boolean save = userService.save(user);
        if (save) {
            return ResultUtils.success("新增员工成功");
        }
        return ResultUtils.error("新增员工失败");
    }

    /**
     * 编辑员工
     *
     * @param user
     * @return
     */
    @PutMapping
    @PreAuthorize("hasAuthority('sys:user:edit')")
    public ResultVo editUser(@RequestBody User user) {
        //判断登录名唯一性
        if (StringUtils.isNotEmpty(user.getUsername())) {
            QueryWrapper<User> query = new QueryWrapper<>();
            query.lambda().eq(User::getUsername, user.getUsername());
            User one = userService.getOne(query);
            if (one != null) {
                return ResultUtils.error("用户名已经被占用!", 500);
            }
        }
        //如果密码存在，MD5加密
        if (StringUtils.isNotEmpty(user.getPassword())) {
//            user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes()));
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        boolean b = userService.updateById(user);
        if (b) {
            return ResultUtils.success("编辑员工成功");
        }
        return ResultUtils.error("编辑员工失败");
    }

    /**
     * 删除员工
     *
     * @param userId
     * @return
     */
    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAuthority('sys:user:delete')")
    public ResultVo deleteUser(@PathVariable Long userId) {
        boolean removeById = userService.removeById(userId);
        if (removeById) {
            return ResultUtils.success("删除员工成功");
        }
        return ResultUtils.error("删除员工失败");
    }

    /**
     * 查询员工列表
     *
     * @param userParm
     * @return
     */
    @GetMapping("/list")
    @PreAuthorize("hasAuthority('sys:sysUserList')")
    public ResultVo list(UserParm userParm) {
        IPage<User> list = userService.list(userParm);
        //前端不展示密码
        list.getRecords().stream().forEach(item -> item.setPassword(""));
        return ResultUtils.success("查询成功", list);
    }

    //根据用户id查询角色
    @GetMapping("/getRolebyUserId")
    public ResultVo getRolebyUserId(UserRole userRole) {
        UserRole roleByUserId = userService.getRoleByUserId(userRole);
        return ResultUtils.success("查询成功", roleByUserId);
    }

    //保存用户角色
    @PostMapping("/saveRole")
    @PreAuthorize("hasAuthority('sys:user:assignRole')")
    public ResultVo saveRole(@RequestBody UserRole userRole) {
        userService.saveRole(userRole);
        return ResultUtils.success("角色分配成功");
    }

    //获取菜单列表
    @GetMapping("/getMenuList")
    public ResultVo getMenuList(HttpServletRequest request){
        //从头部获取token
        String token = request.getHeader("token");
        Claims claims = jwtUtils.getClaimsFromToken(token);
        String username = jwtUtils.getUsernameFromToken(token);
        Object userType = claims.get("userType");
        if(userType.equals("0")) { //0：业主
            //查询业主的权限信息
            LiveUser liveUser = liveUserService.loadUser(username);
            List<Menu> permissionList = menuService.getMenuByUserIdForLiveUser(liveUser.getUserId());
            //只需要获取目录和菜单
            List<Menu> collect = permissionList.stream().filter(item -> item != null && !item.getType().equals("2")).collect(Collectors.toList());
            List<RouterVO> routerVOList = MakeMenuTree.makeRouter(collect, 0L);
            return ResultUtils.success("获取成功",routerVOList);
        }else{
            User user = userService.loadUser(username);
            List<Menu> permissionList = menuService.getMenuByUserId(user.getUserId());
            //只需要获取目录和菜单
            List<Menu> collect = permissionList.stream().filter(item -> item != null && !item.getType().equals("2")).collect(Collectors.toList());
            List<RouterVO> routerVOList = MakeMenuTree.makeRouter(collect, 0L);
            return ResultUtils.success("获取成功",routerVOList);
        }
    }

}
