package com.ljx.goods.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljx.goods.mapper.userMapper;
import com.ljx.goods.pojo.user;
import com.ljx.goods.service.userService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class userServiceImpl implements userService {
    @Autowired
    userMapper userMapper;

    //用户注册
    @Override
    public Integer addByUser(String username,String password) {
        if("".equals(username) && "".equals(password)){
            return 0;
        }else{
            QueryWrapper<user> userQueryWrapper = new QueryWrapper<>();
            userQueryWrapper.eq("username",username).or().eq("password",password);
            if(!userMapper.selectList(userQueryWrapper).isEmpty()){
                return 0;
            }
            user user = new user();
            user.setUsername(username);
            user.setPassword(password);
            int insert = userMapper.insert(user);
            return insert;
        }
    }

    //用户查询
    @Override
    public user UserByUsername(String username) {
        QueryWrapper<user> userQueryWrapper = new QueryWrapper<>();
        userQueryWrapper.eq("username",username);
        return userMapper.selectOne(userQueryWrapper);
    }

    //根据用户id查询
    @Override
    public user UserById(Integer id) {
        return userMapper.selectById(id);
    }

    //用户登录
    @Override
    public Boolean loginByUser(String username, String password) {
        QueryWrapper<user> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username).eq("password",password);
        user user = userMapper.selectOne(wrapper);
        if(user !=null){
            return true;
        }
        return false;
    }

    //用户信息修改
    @Override
    public Boolean updateByUserName(user user) {
        UpdateWrapper<user> wrapper = new UpdateWrapper<>();
        wrapper.eq("id",user.getId());
        Integer integer = userMapper.update(user,wrapper);
        if(integer>0){
            return true;
        }
        return false;
    }
}
