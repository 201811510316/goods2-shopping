package com.ljx.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ljx.goods.pojo.goodsOrder;
import com.ljx.goods.pojo.user;
import com.ljx.goods.util.useless.CommonResult;

public interface goodsOrderService extends IService<goodsOrder> {

    //当购物车结算时，生成订单（保存订单）
    public CommonResult saveOrder(user user);

    //获取订单详情
    goodsOrder getOrderByOrderNo(String orderNo);

    //我的订单列表
    CommonResult getMyOrders(Integer userId);

    //手动取消订单
    Boolean cancelOrder(String orderNo);

}
