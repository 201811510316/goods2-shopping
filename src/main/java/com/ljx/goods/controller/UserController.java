package com.ljx.goods.controller;

import com.auth0.jwt.JWT;
import com.ljx.goods.annatation.LoginToken;
import com.ljx.goods.pojo.user;
import com.ljx.goods.service.userService;
import com.ljx.goods.util.token.cookieUtils;
import com.ljx.goods.util.token.tokenUtils;
import com.ljx.goods.util.responstiy.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * 用户登录--注册
 */
@RestController
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
    public CommonResult<String> addByUser(@RequestParam("username")String username, @RequestParam("password")String password){
        Integer integer = userService.addByUser(username,password);
        if(integer>0){
            CommonResult<String> result = new CommonResult<>(200, "注册成功");
            return result;
        }else{
            CommonResult<String> result = new CommonResult<>(404, "注册失败");
            return result;
        }
    }

    //登录
    @PostMapping("/loginByUser")
    public CommonResult<String> loginByUser(@RequestParam("username")String username,@RequestParam("password")String password,HttpServletRequest request, HttpServletResponse response){
        Boolean aBoolean = userService.loginByUser(username, password);
        if(aBoolean){
            user user = userService.UserByUsername(username);
            String token = tokenUtils.signByToken(user);
//            cookieUtils.setCookie(request,response,"token",token,1800*60,null,true);
            CommonResult<String> result = new CommonResult<>(200, "登录成功",token);
            return result;
        }else{
            CommonResult<String> result = new CommonResult<>(404, "登录失败");
            return result;
        }
    }

    //用户信息查看
    @LoginToken
    @GetMapping("/user")
    public CommonResult<user> UserById(@RequestHeader("token")String token,HttpServletRequest request){
//        String token = cookieUtils.getCookieValue(request, "token");
        String username = JWT.decode(token).getAudience().get(0);
        user user = userService.UserByUsername(username);
        CommonResult<user> result = new CommonResult<>(200, "查询成功", user);
        return result;
    }

    //用户信息修改
    @LoginToken
    @PutMapping(value = "/updateUser")
    public CommonResult<String> updateUser(@RequestBody user user,HttpServletRequest request, HttpServletResponse response){
        Boolean aBoolean = userService.updateByUserName(user);
        if(aBoolean){
            String token = tokenUtils.signByToken(user);
//            cookieUtils.setCookie(request,response,"token",token,1800*60,null,true);
            CommonResult<String> result = new CommonResult<>(200, "修改成功",token);
            return result;
        }
        return CommonResult.failed("修改失败");
    }

//    //用户注册
//    @PostMapping("/addByUser")
//    public CommonResult<user> addByUser(@RequestParam("username")String username, @RequestParam("password")String password, HttpSession session){
//        Integer integer = userService.addByUser(username,password);
//        if(integer>0){
//            user user = userService.UserByUsername(username);
//            session.setAttribute("user",user);
//            CommonResult<user> result = new CommonResult<>(200, "注册成功");
//            return result;
//        }else{
//            CommonResult<user> result = new CommonResult<>(404, "注册失败");
//            return result;
//        }
//    }
//
//    //用户信息查看
//    @GetMapping("/user")
//    public CommonResult<user> UserById(HttpSession session)throws Exception{
//        user user1 = (user)session.getAttribute("user");
//        if(user1==null){
//           throw new Exception("你还没有登录，无法执行此操作！！！");
//        }
//        user user = userService.UserByUsername(user1.getUsername());
//        CommonResult<user> result = new CommonResult<>(200, "查询成功", user);
//        return result;
//    }
//
//    //用户信息修改
//    @RequestMapping(value = "/updateUser", method = RequestMethod.PUT)
//    public CommonResult<user> updateUser(@RequestBody user user,HttpSession session){
//        Boolean aBoolean = userService.updateByUserName(user);
//        if(aBoolean){
//            session.setAttribute("user",user);
//            CommonResult result = new CommonResult(200, "修改成功");
//            return result;
//        }
//        return CommonResult.failed("修改失败");
//    }
//
//    //登录
//    @PostMapping("/loginByUser")
//    public CommonResult loginByUser(@RequestParam("username")String username,@RequestParam("password")String password,HttpSession session){
//        Boolean aBoolean = userService.loginByUser(username, password);
//        if(aBoolean){
//            user user = userService.UserByUsername(username);
//            session.setAttribute("user",user);
////            String token = tokenUtils.signByToken(user);
//            CommonResult result = new CommonResult<>(200, "登录成功");
//            return result;
//        }else{
//            CommonResult result = new CommonResult<>(404, "登录失败");
//            return result;
//        }
//    }

    //登出
    @GetMapping("/logout")
    public CommonResult logout(HttpSession session){
        session.removeAttribute("user");
        CommonResult result = new CommonResult(200, "退出当前账号");
        return result;
    }

}
