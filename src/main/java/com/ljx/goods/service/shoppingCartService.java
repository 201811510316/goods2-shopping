package com.ljx.goods.service;

import com.ljx.goods.pojo.shoppingCart;
import com.ljx.goods.util.responstiy.CommonResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface shoppingCartService {

    //将商品添加到购物车中
    CommonResult goodsAddCart(shoppingCart shoppingCart);


    //查看用户的购物车
    List<shoppingCart> selectUserCart(@Param("userId")Integer userId);


    //删除购物车中某一个商品
    CommonResult deleteCart(@Param("goodsId")Integer goodsId);


    //清空购物车
    Integer dropCart();


    //修改购物车中的商品数量
    CommonResult updateCart(shoppingCart shoppingCart);



}
