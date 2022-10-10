package com.ljx.goods.service;

import com.ljx.goods.pojo.goodsOrder;
import com.ljx.goods.pojo.user;
import com.ljx.goods.util.responstiy.CommonResult;

import java.util.List;

public interface goodsOrderService {

    //当购物车结算时，生成订单（保存订单）
    String saveOrder(user user);

    //获取订单详情
    goodsOrder getOrderByOrderNo(String orderNo);

    //我的订单列表
    List<goodsOrder> getMyOrders(Integer userId);

    //手动取消订单
    Boolean cancelOrder(String orderNo);

    //支付订单（修改订单支付状态）
    Integer updateOrderPay(String orderNo);

    //修改订单的用户的地址或电话
    Integer updateByOrderUser(goodsOrder goodsOrder);

}
