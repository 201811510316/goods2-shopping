package com.ljx.goods.controller;

import com.ljx.goods.pojo.goods;
import com.ljx.goods.pojo.shoppingCart;
import com.ljx.goods.pojo.user;
import com.ljx.goods.service.shoppingCartService;
import com.ljx.goods.util.useless.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 购物车管理
 */
@Controller
@RequestMapping("/cart")
public class CartItemController {
    /**
     * 添加商品到购物车
     * 获取当前购物车列表
     * 修改购物车中指定商品数量
     * 删除购物车中指定商品
     * 清空当前购物车
     */

    @Autowired
    shoppingCartService shoppingCartService;

    //添加商品到购物车
    @RequestMapping("/addcart")
    @ResponseBody
    public CommonResult addByCart(@RequestBody goods goods, HttpSession session){
        user user = (user)session.getAttribute("user");
        shoppingCart shoppingCart = new shoppingCart();
        //生成shoppingCart
        shoppingCart.setUserId(user.getId());
        shoppingCart.setGoodsId(goods.getGoodsId());
        shoppingCart.setCount(1);
        shoppingCart.setGoodsName(goods.getGoodsName());
        shoppingCart.setTupian(goods.getTupian());
        shoppingCart.setGoodsPrice(goods.getGoodsPrice());
        //将shoppingCart保存
        CommonResult commonResult = shoppingCartService.goodsAddCart(shoppingCart);
        if(commonResult.getCode()==200){
            return new CommonResult(200,"添加成功");
        }
        return new CommonResult(404,"添加失败");
    }

    //获取当前购物车列表
    @RequestMapping("/getcart")
    @ResponseBody
    public CommonResult getByCart(HttpSession session){
        user user = (user)session.getAttribute("user");
        List<shoppingCart> shoppingCarts = shoppingCartService.selectUserCart(user.getId());
        if(!shoppingCarts.isEmpty()){
            return new CommonResult(200,"获取成功",shoppingCarts);
        }else{
            return new CommonResult(200,"当前购物车为空，快去购物吧！");
        }

    }

    //修改购物车中指定商品数量
    @RequestMapping("/updatecount")
    @ResponseBody
    public CommonResult updateByCount(@RequestBody shoppingCart shoppingCart){
//        user user = (user)session.getAttribute("user");
//        shoppingCart.setUserId(user.getId());

        CommonResult commonResult = shoppingCartService.updateCart(shoppingCart);
        if (commonResult.getCode()==200){
            return new CommonResult(200,"修改成功");
        }
        return new CommonResult(404,commonResult.getMessage());
    }

    //删除购物车中指定商品
    @RequestMapping("/deletecart")
    @ResponseBody
    public CommonResult deleteByCart(@RequestBody shoppingCart shoppingCart){
        Integer goodsId = shoppingCart.getGoodsId();
        CommonResult commonResult = shoppingCartService.deleteCart(goodsId);
        if (commonResult.getCode()==200){
            return new CommonResult(200,"删除成功");
        }
        return new CommonResult(404,commonResult.getMessage());
    }


    //购物车点击去结算,生成订单
    @RequestMapping("/cartorder")
    @ResponseBody
    public CommonResult cartByOrder(HttpSession session){
        int priceTotal=0;
        user user = (user)session.getAttribute("user");
        List<shoppingCart> shoppingCarts = shoppingCartService.selectUserCart(user.getId());

        if(CollectionUtils.isEmpty(shoppingCarts)){
            return new CommonResult(404,"购物车为空，不能结账");
        }else{
            for(shoppingCart sc:shoppingCarts){
                priceTotal+=sc.getCount()*sc.getGoodsPrice();
            }
            if(priceTotal<0){
                return new CommonResult(404,"出错了！");
            }
        }
        session.setAttribute("priceTotal",priceTotal);
        return new CommonResult(200,"ok",shoppingCarts);
    }


}
