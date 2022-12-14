package com.ljx.goods.controller;

import com.auth0.jwt.JWT;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ljx.goods.annatation.LoginToken;
import com.ljx.goods.mapper.goodsMapper;
import com.ljx.goods.pojo.goods;
import com.ljx.goods.pojo.shoppingCart;
import com.ljx.goods.pojo.user;
import com.ljx.goods.service.shoppingCartService;
import com.ljx.goods.service.userService;
import com.ljx.goods.util.token.cookieUtils;
import com.ljx.goods.util.responstiy.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 购物车管理
 */
@RestController
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
    userService userService;

    @Autowired
    shoppingCartService shoppingCartService;

    @Autowired
    goodsMapper goodsMapper;


    //添加商品到购物车
    @LoginToken
    @PostMapping(value = "/addcart")
    public CommonResult addByCart(@RequestParam("id")Integer id, @RequestHeader("token")String token,HttpServletRequest request){
        //拿到当前用户信息
//        String token = cookieUtils.getCookieValue(request, "token");
        String username = JWT.decode(token).getAudience().get(0);
        user user = userService.UserByUsername(username);

        //拿到对应的商品信息
        QueryWrapper<goods> goodsQueryWrapper = new QueryWrapper<>();
        goodsQueryWrapper.eq("goods_id",id);
        goods goods = goodsMapper.selectOne(goodsQueryWrapper);

        //生成shoppingCart
        shoppingCart shoppingCart = new shoppingCart();
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
    @LoginToken
    @GetMapping(value = "/getcart")
    public CommonResult getByCart(@RequestHeader("token")String token,HttpServletRequest request){
//        String token = cookieUtils.getCookieValue(request, "token");
        String username = JWT.decode(token).getAudience().get(0);
        user user = userService.UserByUsername(username);

        List<shoppingCart> shoppingCarts = shoppingCartService.selectUserCart(user.getId());
        if(!shoppingCarts.isEmpty()){
            return new CommonResult(200,"获取成功",shoppingCarts);
        }else{
            return new CommonResult(200,"当前购物车为空，快去购物吧！");
        }
    }

    //修改购物车中指定商品数量
    @LoginToken
    @PutMapping(value = "/updatecount")
    public CommonResult updateByCount(@RequestBody shoppingCart shoppingCart){
        CommonResult commonResult = shoppingCartService.updateCart(shoppingCart);
        if (commonResult.getCode()==200){
            return new CommonResult(200,"修改成功");
        }
        return new CommonResult(404,commonResult.getMessage());
    }

    //删除购物车中指定商品
    @LoginToken
    @DeleteMapping(value = "/deletecart")
    public CommonResult deleteByCart(@RequestParam("goodsId")Integer goodsId){
        CommonResult commonResult = shoppingCartService.deleteCart(goodsId);
        if (commonResult.getCode()==200){
            return new CommonResult(200,"删除成功");
        }
        return new CommonResult(404,commonResult.getMessage());
    }


//    //添加商品到购物车
//    @RequestMapping(value = "/addcart",method = RequestMethod.POST)
//    @ResponseBody
//    public CommonResult addByCart(@RequestParam("id")Integer id, HttpSession session){
//        //拿到当前用户信息
//        user user = (user)session.getAttribute("user");
//        if(user==null){
//            return CommonResult.failed("你还没登录！！！");
//        }
//        //拿到对应的商品信息
//        QueryWrapper<goods> goodsQueryWrapper = new QueryWrapper<>();
//        goodsQueryWrapper.eq("goods_id",id);
//        goods goods = goodsMapper.selectOne(goodsQueryWrapper);
//
//        //生成shoppingCart
//        shoppingCart shoppingCart = new shoppingCart();
//        shoppingCart.setUserId(user.getId());
//        shoppingCart.setGoodsId(goods.getGoodsId());
//        shoppingCart.setCount(1);
//        shoppingCart.setGoodsName(goods.getGoodsName());
//        shoppingCart.setTupian(goods.getTupian());
//        shoppingCart.setGoodsPrice(goods.getGoodsPrice());
//        //将shoppingCart保存
//        CommonResult commonResult = shoppingCartService.goodsAddCart(shoppingCart);
//        if(commonResult.getCode()==200){
//            return new CommonResult(200,"添加成功");
//        }
//        return new CommonResult(404,"添加失败");
//    }
//
//    //获取当前购物车列表
//    @RequestMapping(value = "/getcart",method = RequestMethod.GET)
//    @ResponseBody
//    public CommonResult getByCart(HttpSession session)throws Exception{
//        user user = (user)session.getAttribute("user");
//        if(user==null){
//            throw new Exception("你还没有登录");
//        }
//        List<shoppingCart> shoppingCarts = shoppingCartService.selectUserCart(user.getId());
//        if(!shoppingCarts.isEmpty()){
//            return new CommonResult(200,"获取成功",shoppingCarts);
//        }else{
//            return new CommonResult(200,"当前购物车为空，快去购物吧！");
//        }
//    }


}
