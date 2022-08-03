package com.ljx.goods.controller;

import com.ljx.goods.pojo.goodsOrder;
import com.ljx.goods.pojo.orderItem;
import com.ljx.goods.pojo.shoppingCart;
import com.ljx.goods.pojo.user;
import com.ljx.goods.service.goodsOrderService;
import com.ljx.goods.service.orderItemService;
import com.ljx.goods.service.shoppingCartService;
import com.ljx.goods.util.useless.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 订单管理
 */
@Controller
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
    goodsOrderService orderService;

    @Autowired
    orderItemService orderItemService;

    @Autowired
    shoppingCartService shoppingCartService;

    //生成订单 --订单编号
    @RequestMapping("/create")
    @ResponseBody
    public CommonResult orderCreate(HttpSession session){
        user user = (user)session.getAttribute("user");
        List<shoppingCart> shoppingCarts = shoppingCartService.selectUserCart(user.getId());
        if(!shoppingCarts.isEmpty()){
            CommonResult commonResult = orderService.saveOrder(user);
            CommonResult result = new CommonResult(200, commonResult.getMessage(), commonResult.getData());
            return result;
        }
        return CommonResult.failed("购物车为空，不能结账");
    }

    //根据订单编号查看订单详情（查看order表）
    @RequestMapping("/orders/{orderNo}")
    @ResponseBody
    public CommonResult selectOrderNo(@PathVariable("orderNo")String orderNo){
        goodsOrder orderByOrderNo = orderService.getOrderByOrderNo(orderNo);
        CommonResult<goodsOrder> result = new CommonResult(200, "查询成功", orderByOrderNo);
        return result;
    }

    //根据用户id查看全部订单详情（查看order表）
    @RequestMapping("/userIdOrder")
    @ResponseBody
    public CommonResult userIdOrder(HttpSession session){
        user user = (user)session.getAttribute("user");
        Integer userId = user.getId();
        CommonResult myOrders = orderService.getMyOrders(userId);
        if(myOrders.getCode()==200){
            CommonResult result = new CommonResult(200, myOrders.getMessage(), myOrders.getData());
            return result;
        }
        return CommonResult.failed(myOrders.getMessage());
    }

    //用户取消订单
    @RequestMapping("/deleteOrder/{orderNo}")
    @ResponseBody
    public CommonResult deleteOrder(@PathVariable("orderNo")String orderNo){
        Boolean aBoolean = orderService.cancelOrder(orderNo);
        if(aBoolean){
            CommonResult result = new CommonResult(200, "操作成功，订单已取消");
            return result;
        }
        return CommonResult.failed("操作失败");
    }

    //根据订单编号查看订单商品详情（查看orderItem表）
    @RequestMapping("/orderItem/{orderNo}")
    @ResponseBody
    public CommonResult selectOrderItem(@PathVariable("orderNo")String orderNo){
        List<orderItem> orderItems = orderItemService.getOrderItemByOrderNo(orderNo);
        CommonResult result = new CommonResult(200, "查询成功", orderItems);
        return result;
    }

}
