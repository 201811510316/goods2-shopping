package com.ljx.goods.common;

import com.ljx.goods.util.responstiy.CommonResult;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

//全局异常处理
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(value = RuntimeException.class)
    public CommonResult<String> RuntimeHandlerException(HttpServletRequest request,RuntimeException e){
        System.out.println("异常为："+e.getMessage());
        return new CommonResult<>(400,e.getMessage());
    }

    @ExceptionHandler(value=Exception.class)
    public CommonResult<String> handlerException(HttpServletRequest request,Exception e){
        System.out.println("异常处理："+e.getMessage());
//        e.printStackTrace();
        return new CommonResult<>(404,e.getMessage());
    }
}
