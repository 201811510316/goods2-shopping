package com.ljx.goods.service;

import com.ljx.goods.pojo.user;

public interface userService extends IService<user> {
    //用户添加（注册）
    Integer addByUser(String username,String password);

    //用户信息展示（查询用户信息）
    user UserByUsername(String username);

    //根据用户id查询
    user UserById(Integer id);

    //用户登录
    Boolean loginByUser(String username,String password);

    //用户修改信息
    Boolean updateByUserName(user user);
}
