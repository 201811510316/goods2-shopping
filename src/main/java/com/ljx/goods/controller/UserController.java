package com.ljx.goods.controller;

import com.ljx.goods.pojo.user;
import com.ljx.goods.service.userService;
import com.ljx.goods.util.useless.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * 用户登录--注册
 */
@Controller
public class UserController {
    /**
     * 用户登录-登出
     * 用户注册
     * 用户信息展示
     */

    @Autowired
    userService userService;

    //用户注册
    @PostMapping("/addByUser")
    @ResponseBody
    public CommonResult<user> addByUser(@RequestParam("username")String username, @RequestParam("password")String password, HttpSession session){
        Integer integer = userService.addByUser(username,password);
        if(integer>0){
            user user = userService.UserById(username);
            session.setAttribute("user",user);
            CommonResult<user> result = new CommonResult<>(200, "注册成功");
            return result;
        }else{
            CommonResult<user> result = new CommonResult<>(404, "注册失败");
            return result;
        }
    }

    //用户信息查看
    @GetMapping("/user")
    @ResponseBody
    public CommonResult<user> UserById(HttpSession session){
        user user1 = (user)session.getAttribute("user");
        if(user1==null){
            return CommonResult.failed("你还没登录！！！");
        }
        user user = userService.UserById(user1.getUsername());
        CommonResult<user> result = new CommonResult<>(200, "查询成功", user);
        return result;
    }

    //用户信息修改
    @RequestMapping(value = "/updateUser", method = RequestMethod.PUT)
    @ResponseBody
    public CommonResult<user> updateUser(@RequestBody user user,HttpSession session){
        Boolean aBoolean = userService.updateByUserName(user);
        if(aBoolean){
            session.setAttribute("user",user);
            CommonResult result = new CommonResult(200, "修改成功");
            return result;
        }
        return CommonResult.failed("修改失败");
    }

    //登录
    @PostMapping("/loginByUser")
    @ResponseBody
    public CommonResult<user> loginByUser(@RequestParam("username")String username,@RequestParam("password")String password,HttpSession session){
        Boolean aBoolean = userService.loginByUser(username, password);
        if(aBoolean){
            user user = userService.UserById(username);
            session.setAttribute("user",user);
            CommonResult<user> result = new CommonResult<>(200, "登录成功");
            return result;
        }else{
            CommonResult<user> result = new CommonResult<>(404, "登录失败");
            return result;
        }
    }

    //登出
    @GetMapping("/logout")
    @ResponseBody
    public CommonResult logout(HttpSession session){
        session.removeAttribute("user");
        CommonResult result = new CommonResult(200, "退出当前账号");
        return result;
    }

}
