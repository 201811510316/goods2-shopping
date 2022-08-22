package com.ljx.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ljx.goods.pojo.orderItem;

import java.util.List;

public interface orderItemService extends IService<orderItem> {

    //查询操作
    List<orderItem> getOrderItemByOrderNo(String orderNo);
}
