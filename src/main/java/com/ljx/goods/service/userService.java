package com.ljx.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ljx.goods.pojo.user;

public interface userService extends IService<user> {
    //用户添加（注册）
    public Integer addByUser(String username,String password);

    //用户信息展示（查询用户信息）
    public user UserById(String username);

    //用户登录
    public Boolean loginByUser(String username,String password);

    //用户修改信息
    Boolean updateByUserName(user user);
}
