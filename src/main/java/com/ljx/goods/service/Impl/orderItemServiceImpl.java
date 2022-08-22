package com.ljx.goods.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljx.goods.mapper.orderItemMapper;
import com.ljx.goods.pojo.orderItem;
import com.ljx.goods.service.orderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class orderItemServiceImpl extends ServiceImpl<orderItemMapper, orderItem> implements orderItemService {

    @Autowired
    orderItemMapper orderItemMapper;

    //查询订单商品信息
    @Override
    public List<orderItem> getOrderItemByOrderNo(String orderNo) {
        QueryWrapper<orderItem> wrapper = new QueryWrapper<>();
        wrapper.eq("order_no",orderNo);
        List<orderItem> orderItems = orderItemMapper.selectList(wrapper);
        return orderItems;
    }
}
