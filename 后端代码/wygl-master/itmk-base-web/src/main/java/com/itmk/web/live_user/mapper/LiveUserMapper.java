package com.itmk.web.live_user.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.itmk.web.live_user.entity.LiveUser;
import org.apache.ibatis.annotations.Param;

/**
 * @Classname LiveUserMapper
 * @Description
 * @Date 2024/3/5 13:54
 * @Created by wangxiaojie
 */
public interface LiveUserMapper extends BaseMapper<LiveUser> {
    //分页查询
    IPage<LiveUser> getList(IPage<LiveUser> page, @Param("loginName") String loginName,@Param("phone") String phone);

    //通过业主id查询业主信息
    LiveUser getUser(@Param("userId") Long userId);
}
