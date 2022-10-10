package com.ljx.goods.controller;

import com.auth0.jwt.JWT;
import com.ljx.goods.annatation.LoginToken;
import com.ljx.goods.pojo.goodsOrder;
import com.ljx.goods.pojo.orderItem;
import com.ljx.goods.pojo.shoppingCart;
import com.ljx.goods.pojo.user;
import com.ljx.goods.service.goodsOrderService;
import com.ljx.goods.service.orderItemService;
import com.ljx.goods.service.shoppingCartService;
import com.ljx.goods.service.userService;
import com.ljx.goods.util.token.cookieUtils;
import com.ljx.goods.util.responstiy.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 订单管理
 */
@RestController
@RequestMapping("/order")
public class PortalOrderController {
    /**
     * 根据购物车信息生成订单
     * 用户取消订单
     * 用户根据订单编号查看订单详情
     * 用户根据用户id查看全部订单详情
     * 用户根据订单编号查看订单商品详情
     */
    @Autowired
    userService userService;

    @Autowired
    goodsOrderService orderService;

    @Autowired
    orderItemService orderItemService;

    @Autowired
    shoppingCartService shoppingCartService;


    //生成订单 --订单编号
    @LoginToken
    @GetMapping("/create")
    public CommonResult<String> orderCreate(@RequestHeader("token")String token,HttpServletRequest request){
//        String token = cookieUtils.getCookieValue(request, "token");
        String username = JWT.decode(token).getAudience().get(0);
        user user = userService.UserByUsername(username);

        List<shoppingCart> shoppingCarts = shoppingCartService.selectUserCart(user.getId());
        if(!shoppingCarts.isEmpty()){
            String orderNo = orderService.saveOrder(user);
            CommonResult<String> result = new CommonResult<>(200,"订单创建成功,订单号为："+orderNo,orderNo);
            return result;
        }
        return CommonResult.failed("购物车为空，不能结账");
    }

    //根据订单编号查看订单详情（查看order表）
    @LoginToken
    @GetMapping("/orders/{orderNo}")
    public CommonResult<goodsOrder> selectOrderNo(@PathVariable("orderNo")String orderNo){
        goodsOrder orderByOrderNo = orderService.getOrderByOrderNo(orderNo);
        CommonResult<goodsOrder> result = new CommonResult<>(200, "查询成功", orderByOrderNo);
        return result;
    }

    //根据用户id查看全部订单详情（查看order表）
    @LoginToken
    @GetMapping("/userIdOrder")
    public CommonResult<List<goodsOrder>> userIdOrder(@RequestHeader("token")String token,HttpServletRequest request)throws Exception{
//        String token = cookieUtils.getCookieValue(request, "token");
        String username = JWT.decode(token).getAudience().get(0);
        user user = userService.UserByUsername(username);

        Integer userId = user.getId();
        List<goodsOrder> myOrders = orderService.getMyOrders(userId);
        if(myOrders.isEmpty()){
            throw new Exception("当前没有订单哦！");
        }
        return new CommonResult<List<goodsOrder>>(200,"查询成功",myOrders);
    }

    //用户取消订单
    @LoginToken
    @DeleteMapping("/deleteOrder/{orderNo}")
    public CommonResult<String> deleteOrder(@PathVariable("orderNo")String orderNo){
        Boolean aBoolean = orderService.cancelOrder(orderNo);
        if(aBoolean){
            CommonResult<String> result = new CommonResult<>(200, "操作成功，订单已取消");
            return result;
        }
        return CommonResult.failed("操作失败");
    }

    //根据订单编号查看订单商品详情（查看orderItem表）
    @LoginToken
    @GetMapping("/orderItem/{orderNo}")
    public CommonResult<List<orderItem>> selectOrderItem(@PathVariable("orderNo")String orderNo){
        List<orderItem> orderItems = orderItemService.getOrderItemByOrderNo(orderNo);
        CommonResult<List<orderItem>> result = new CommonResult<>(200, "查询成功", orderItems);
        return result;
    }

    //支付订单
    @LoginToken
    @GetMapping(value = "/orders/pay")
    public CommonResult<String> updateByOrder(@RequestParam("orderNo")String orderNo){
        Integer integer = orderService.updateOrderPay(orderNo);
        if(integer>0){
            CommonResult<String> result = new CommonResult<>(200, "支付成功");
            return result;
        }else{
            CommonResult<String> result = new CommonResult<>(404, "支付失败");
            return result;
        }
    }

    //修改订单的用户的地址或电话
    @LoginToken
    @PutMapping("/updateByUser")
    public CommonResult<String> updateByUser(@RequestBody goodsOrder goodsOrder ){
        Integer integer = orderService.updateByOrderUser(goodsOrder);
        if(integer>0){
            CommonResult<String> result = new CommonResult<>(200, "修改成功");
            return result;
        }else{
            CommonResult<String> result = new CommonResult<>(404, "修改失败");
            return result;
        }
    }


//    //生成订单 --订单编号
//    @RequestMapping("/create")
//    public CommonResult orderCreate(HttpSession session){
//        user user = (user)session.getAttribute("user");
//        List<shoppingCart> shoppingCarts = shoppingCartService.selectUserCart(user.getId());
//        if(!shoppingCarts.isEmpty()){
//            CommonResult commonResult = orderService.saveOrder(user);
//            CommonResult result = new CommonResult(200, commonResult.getMessage(), commonResult.getData());
//            return result;
//        }
//        return CommonResult.failed("购物车为空，不能结账");
//    }
//
//    //根据订单编号查看订单详情（查看order表）
//    @RequestMapping("/orders/{orderNo}")
//    public CommonResult selectOrderNo(@PathVariable("orderNo")String orderNo){
//        goodsOrder orderByOrderNo = orderService.getOrderByOrderNo(orderNo);
//        CommonResult<goodsOrder> result = new CommonResult(200, "查询成功", orderByOrderNo);
//        return result;
//    }
//
//    //根据用户id查看全部订单详情（查看order表）
//    @RequestMapping("/userIdOrder")
//    public CommonResult userIdOrder(HttpSession session){
//        user user = (user)session.getAttribute("user");
//        if(user==null){
//            return CommonResult.failed("你还没登录！！！");
//        }
//        Integer userId = user.getId();
//        CommonResult myOrders = orderService.getMyOrders(userId);
//        if(myOrders.getCode()==200){
//            if(myOrders.getData()==null){
//                CommonResult result = new CommonResult(200,"当前没有订单哦！");
//                return result;
//            }else{
//                CommonResult result = new CommonResult(200, myOrders.getMessage(), myOrders.getData());
//                return result;
//            }
//        }
//        return CommonResult.failed(myOrders.getMessage());
//    }
//
//    //用户取消订单
//    @RequestMapping("/deleteOrder/{orderNo}")
//    public CommonResult deleteOrder(@PathVariable("orderNo")String orderNo){
//        Boolean aBoolean = orderService.cancelOrder(orderNo);
//        if(aBoolean){
//            CommonResult result = new CommonResult(200, "操作成功，订单已取消");
//            return result;
//        }
//        return CommonResult.failed("操作失败");
//    }
//
//    //根据订单编号查看订单商品详情（查看orderItem表）
//    @RequestMapping("/orderItem/{orderNo}")
//    public CommonResult selectOrderItem(@PathVariable("orderNo")String orderNo){
//        List<orderItem> orderItems = orderItemService.getOrderItemByOrderNo(orderNo);
//        CommonResult result = new CommonResult(200, "查询成功", orderItems);
//        return result;
//    }
//
//    //支付订单（修改）
//    @RequestMapping(value = "/orders/pay")
//    public CommonResult updateByOrder(@RequestParam("orderNo")String orderNo){
//        Integer integer = orderService.updateOrderPay(orderNo);
//        if(integer>0){
//            CommonResult result = new CommonResult(200, "支付成功");
//            return result;
//        }else{
//            CommonResult result = new CommonResult(404, "支付失败");
//            return result;
//        }
//    }
//
//    //修改订单的用户的地址或电话
//    @RequestMapping("/updateByUser")
//    public CommonResult updateByUser(@RequestBody goodsOrder goodsOrder ){
//        Integer integer = orderService.updateByOrderUser(goodsOrder);
//        if(integer>0){
//            CommonResult result = new CommonResult(200, "修改成功");
//            return result;
//        }else{
//            CommonResult result = new CommonResult(404, "修改失败");
//            return result;
//        }
//    }

}
