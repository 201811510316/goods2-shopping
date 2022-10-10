package com.ljx.goods.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.ljx.goods.mapper.goodsMapper;
import com.ljx.goods.mapper.goodsOrderMapper;
import com.ljx.goods.mapper.orderItemMapper;
import com.ljx.goods.pojo.*;
import com.ljx.goods.service.goodsOrderService;
import com.ljx.goods.service.shoppingCartService;
import com.ljx.goods.util.orderCode;
import com.ljx.goods.util.responstiy.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class goodsOrderServiceImpl implements goodsOrderService {

    @Autowired
    goodsOrderMapper orderMapper;

    @Autowired
    goodsMapper goodsMapper;

    @Autowired
    shoppingCartService shoppingCartService;

    @Autowired
    orderItemMapper orderItemMapper;

    //生成订单
    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public String saveOrder(user user)throws RuntimeException {
        Integer userId = user.getId();
        List<shoppingCart> shoppingCarts = shoppingCartService.selectUserCart(userId);

        //判断列表中的商品是否存在、是否是上架状态、库存是否足够
        for(shoppingCart scl:shoppingCarts){
            Integer goodsId = scl.getGoodsId();

            QueryWrapper<goods> wrapper1 = new QueryWrapper<>();
            wrapper1.eq("goods_id",goodsId);
            goods oneByDetail = goodsMapper.selectOne(wrapper1);
            if(oneByDetail ==null || oneByDetail.getState().equals(0)){
                throw new RuntimeException("订单生成失败，商品已不存在！");
            }
            //判断库存是否足够
            if(scl.getCount()>oneByDetail.getGoodsStock()){
                throw new RuntimeException("订单生成失败，商品不够！！！");
            }
        }
        //把购物车获得的商品数据存储到orderItem表
        List<orderItem> orderItemList = new ArrayList<>();
        for(shoppingCart scls:shoppingCarts){
            orderItem orderItem = new orderItem();
            orderItem.setGoodsId(scls.getGoodsId());
            orderItem.setGoodsName(scls.getGoodsName());
            orderItem.setTupian(scls.getTupian());
            orderItem.setCount(scls.getCount());
            orderItem.setGoodsPrice(scls.getGoodsPrice());
            orderItemList.add(orderItem);
        }
        //清空购物车内容
        shoppingCartService.dropCart();

        //获取当前订单的总价
        Integer totalPrice = 0;
        for(orderItem Items:orderItemList){
            totalPrice+=Items.getGoodsPrice()*Items.getCount();
        }

        //生成订单编号
        String orderNo = orderCode.getOrderCode(userId);

        //生成订单
        goodsOrder order = new goodsOrder();
        order.setOrderNo(orderNo);
        order.setUserId(userId);
        order.setPayStatus(0);
        order.setTelephone(user.getTelephone());
        order.setTotalPrice(totalPrice);
        order.setCreateTime(new Date());
        order.setUserAddress(user.getAddress());
        order.setOrderState(0);
        orderMapper.insert(order);

        for(orderItem Item:orderItemList){
            Item.setOrderNo(orderNo);
            orderItemMapper.insert(Item);
        }
        //返回订单号
        return orderNo;
    }

    //获取订单详情
    @Override
    public goodsOrder getOrderByOrderNo(String orderNo) {
        QueryWrapper<goodsOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderNo);
        goodsOrder order = orderMapper.selectOne(wrapper);
        return order;
    }

    //查看订单列表
    @Override
    public List<goodsOrder> getMyOrders(Integer userId) {
        QueryWrapper<goodsOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        List<goodsOrder> orders = orderMapper.selectList(wrapper);
        return orders;
    }

    //取消订单
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Boolean cancelOrder(String orderNo) {
        QueryWrapper<goodsOrder> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderNo);
        goodsOrder goodsOrder = orderMapper.selectOne(wrapper);
        //如果已支付就不能取消订单
        if(goodsOrder.getPayStatus()==0 || goodsOrder.getPayTime()==null){
            UpdateWrapper<orderItem> wrapper1 = new UpdateWrapper<>();
            wrapper1.eq("order_no",orderNo);
            Integer integer1 = orderItemMapper.delete(wrapper1);
            if(integer1>0){
                UpdateWrapper<goodsOrder> wrapper2 = new UpdateWrapper<>();
                wrapper2.eq("order_no",orderNo);
                Integer integer = orderMapper.delete(wrapper2);
                if(integer>0){
                    return true;
                }
            }
        }
        return false;
    }

    //支付订单（修改订单支付状态）
    @Transactional(rollbackFor = Exception.class)
    @Override
    public Integer updateOrderPay(String orderNo) {
        QueryWrapper<goodsOrder> goodsOrderQueryWrapper = new QueryWrapper<>();
        goodsOrderQueryWrapper.eq("order_no",orderNo);
        goodsOrder goodsOrder = orderMapper.selectOne(goodsOrderQueryWrapper);
        if(goodsOrder.getPayStatus()==1 || goodsOrder.getPayTime()!=null){
            return 0;
        }else if(goodsOrder.getTelephone().isEmpty() || goodsOrder.getUserAddress().isEmpty()){
            return 0;
        }else{
//            QueryWrapper<orderItem> wrapper = new QueryWrapper<>();
//            wrapper.eq("order_no",orderNo);
//            List<orderItem> orderItems = orderItemMapper.selectList(wrapper);
//            for(orderItem orderItem:orderItems){
//                //获取原先商品
//                QueryWrapper<goods> wrapper1 = new QueryWrapper<>();
//                wrapper1.eq("goods_id",orderItem.getGoodsId());
//                goods goods = goodsMapper.selectOne(wrapper1);
//                //扣库存---计算新库存
//                int stock=goods.getGoodsStock()-orderItem.getCount();
//                goods.setGoodsStock(stock);
//                //加销量---计算新销量
//                int sales=goods.getGoodsSales()+orderItem.getCount();
//                goods.setGoodsSales(sales);
//                //保存新商品
//                UpdateWrapper<goods> goodsUpdateWrapper = new UpdateWrapper<>();
//                goodsUpdateWrapper.eq("goods_id",goods.getGoodsId());
//                goodsMapper.update(goods,goodsUpdateWrapper);
//            }
            //修改状态和时间
            goodsOrder.setPayStatus(1);
            goodsOrder.setPayTime(new Date());
            int order_no = orderMapper.update(goodsOrder, new UpdateWrapper<goodsOrder>().eq("order_no", orderNo));
            return order_no;
        }
    }

    //修改订单的用户的地址或电话
    @Override
    public Integer updateByOrderUser(goodsOrder goodsOrder) {
        //判断该订单是否支付，没有支付可以进行修改，有支付就不让其修改
        if(goodsOrder.getPayStatus().equals(0)){
            //要修改对象
            QueryWrapper<goodsOrder> wrapper1 = new QueryWrapper<>();
            wrapper1.eq("order_No", goodsOrder.getOrderNo());
            goodsOrder goodsOrder1 = orderMapper.selectOne(wrapper1);
            //修改
            goodsOrder1.setUserAddress(goodsOrder.getUserAddress());
            goodsOrder1.setTelephone(goodsOrder.getTelephone());
            //修改条件
            UpdateWrapper<goodsOrder> wrapper = new UpdateWrapper<>();
            wrapper.eq("order_No",goodsOrder.getOrderNo());
            int update = orderMapper.update(goodsOrder1, wrapper);
            return update;
        }else{
            return 0;
        }
    }


}
