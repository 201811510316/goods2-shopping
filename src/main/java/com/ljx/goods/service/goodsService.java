package com.ljx.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ljx.goods.pojo.goods;
import com.ljx.goods.pojo.goodsAd;
import com.ljx.goods.util.goodsByFenLeiResult;
import com.ljx.goods.util.goodsByIdResult;
import com.ljx.goods.util.goodsContentResult;

import java.util.List;

public interface goodsService extends IService<goods> {

    //查询所有商品信息
    goodsContentResult findAll();

    //分类查询商品信息
    goodsByFenLeiResult findAllByType(Integer categoryId);

    //查询单个商品信息 详情页
    goodsByIdResult findOneByDetail(Integer goodsId);

    //查询首页横幅图片
    List<goodsAd> getGoodsAd();

    //搜索商品
    List<goods> MoHuByGoods(String keyword);

    //查询所有热门商品信息
    List<goods> goodsHotAllList();

    //查询所有最新商品信息
    List<goods> goodsNewAllList();

    //查询所有普通商品信息
    List<goods> goodsAllList();
}
