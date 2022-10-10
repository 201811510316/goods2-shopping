package com.ljx.goods.common;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.ljx.goods.annatation.LoginToken;
import com.ljx.goods.annatation.PassToken;
import com.ljx.goods.pojo.user;
import com.ljx.goods.service.userService;
import com.ljx.goods.util.token.cookieUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.PostConstruct;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

//配置拦截器的拦截规则
@Component
public class MyInterceptor implements HandlerInterceptor {

    @Autowired
    userService userService;

    private static MyInterceptor myInterceptor;
    public void setUserService(userService userService){
        this.userService = userService;
    }
    @PostConstruct
    public void init(){
        myInterceptor = this;
        myInterceptor.userService = this.userService;
    }

    //配置拦截规则，不在拦截范围（在白名单上）的请求路径不会被拦截；
    //当在拦截范围的请求没有@loginToken注解，直接放行，因为这请求不需要判断用户是否在线
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
//        String token = cookieUtils.getCookieValue(request, "token");
        //如果不是映射到方法直接通过
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        //检查是否有passtoken注解，有则跳过认证
        if(method.isAnnotationPresent(PassToken.class)){
            PassToken passToken = method.getAnnotation(PassToken.class);
            if(passToken.required()){
                return true;
            }
        }
        //检查有没有需要用户权限的注解,有的话就进行认证
        if(method.isAnnotationPresent(LoginToken.class)){
            LoginToken loginToken = method.getAnnotation(LoginToken.class);
            if(loginToken.required()){
                //执行认证
                if(token == null){
                    throw new RuntimeException("无token,请重新登录");
                }
                //获取token中的userid
                String username;
                try {
                    //getAudience()获取token中withAudience的内容
                    //getClaim()获取token中的withClaim的值
                    username= JWT.decode(token).getAudience().get(0);
                } catch (Exception e) {
                    throw new RuntimeException("获取不到token中的用户名");
                }
                user user = myInterceptor.userService.UserByUsername(username);
                if(user==null){
                    throw new RuntimeException("用户不存在，请重新登录");
                }
                //验证token
                JWTVerifier jwtVerifier = JWT.require(Algorithm.HMAC256(user.getPassword())).build();
                try {
                    jwtVerifier.verify(token);
                } catch (JWTVerificationException e) {
                    throw new RuntimeException("token已过期，请重新登录");
                }
                return true;
            }
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
