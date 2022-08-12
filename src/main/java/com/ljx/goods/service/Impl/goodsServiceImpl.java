package com.ljx.goods.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljx.goods.mapper.*;
import com.ljx.goods.pojo.*;
import com.ljx.goods.service.goodsService;
import com.ljx.goods.util.goodsByFenLeiResult;
import com.ljx.goods.util.goodsByIdResult;
import com.ljx.goods.util.goodsContentResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class goodsServiceImpl extends ServiceImpl<goodsMapper, goods> implements goodsService {

    @Autowired
    goodsMapper goodsMapper;

    @Autowired
    goodsImgMapper goodsImgMapper;

    @Autowired
    goodsRemarkMapper goodsRemarkMapper;

    @Autowired
    goodsSlideMapper goodsSlideMapper;

    @Autowired
    goodsAdMapper goodsAdMapper;

    //查询商品首页信息
    @Override
    public goodsContentResult findAll() {
        Page<goods> goodsPage = new Page<>(1, 4);
        goodsContentResult goodsContentResult = new goodsContentResult();

        QueryWrapper<goods> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("goods_hot",1).ne("state",0);
        List<goods> goodsHots = goodsMapper.selectPage(goodsPage, wrapper1).getRecords();

        QueryWrapper<goods> wrapper2 = new QueryWrapper<>();
        wrapper2.eq("goods_new",1).ne("state",0);
        List<goods> goodsNews = goodsMapper.selectPage(goodsPage, wrapper2).getRecords();

        QueryWrapper<goods> wrapper3 = new QueryWrapper<>();
        wrapper3.eq("goods_hot",0).eq("goods_new",0).ne("state",0);
        List<goods> goods = goodsMapper.selectPage(goodsPage, wrapper3).getRecords();

        goodsContentResult.setFindAllGoods(goods);
        goodsContentResult.setFindAllHots(goodsHots);
        goodsContentResult.setFindAllNews(goodsNews);

        return goodsContentResult;
    }

    //根据分类类别查询商品信息
    @Override
    public goodsByFenLeiResult findAllByType(Integer categoryId) {
        QueryWrapper<goods> goodsQueryWrapper = new QueryWrapper<>();
        goodsQueryWrapper.eq("category_id",categoryId).ne("state",0);
        List<goods> goods = goodsMapper.selectList(goodsQueryWrapper);

        goodsByFenLeiResult goodsByFenLeiResult = new goodsByFenLeiResult();
        goodsByFenLeiResult.setFenLeiGoods(goods);
        return goodsByFenLeiResult;
    }

    //查询单个商品信息 详情页
    @Override
    public goodsByIdResult findOneByDetail(Integer goodsId) {
        //商品详细信息
        QueryWrapper<goods> wrapper = new QueryWrapper<>();
        wrapper.eq("goods_id",goodsId).ne("state",0);
        goods goods = goodsMapper.selectOne(wrapper);
        //商品详细图片
        QueryWrapper<goodsImg> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("goods_id",goodsId);
        goodsImg goodsImg = goodsImgMapper.selectOne(wrapper1);
        //商品备注
        QueryWrapper<goodsRemark> wrapper2 = new QueryWrapper<>();
        wrapper2.eq("goods_id",goodsId);
        goodsRemark goodsRemark = goodsRemarkMapper.selectOne(wrapper2);
        //商品轮播图
        QueryWrapper<goodsSlide> wrapper3 = new QueryWrapper<>();
        wrapper3.eq("goods_id",goodsId);
        List<goodsSlide> goodsSlides = goodsSlideMapper.selectList(wrapper3);

        goodsByIdResult goodsByIdResult = new goodsByIdResult();
        goodsByIdResult.setGoodsById(goods);
        goodsByIdResult.setImgList(goodsImg);
        goodsByIdResult.setRemark(goodsRemark);
        goodsByIdResult.setSlideList(goodsSlides);
        return goodsByIdResult;
    }

    //查询首页横幅图片
    @Override
    public List<goodsAd> getGoodsAd() {
        List<goodsAd> goodsAds = goodsAdMapper.selectList(null);
        return goodsAds;
    }

    //搜索商品
    @Override
    public List<goods> MoHuByGoods(String keyword) {
        QueryWrapper<goods> goodsQueryWrapper = new QueryWrapper<>();
        goodsQueryWrapper.like("goods_name",keyword).ne("state",0);
        List<goods> goods = goodsMapper.selectList(goodsQueryWrapper);
        return goods;
    }

    //查询所有热门商品信息
    @Override
    public List<goods> goodsHotAllList() {
        Page<goods> goodsPage = new Page<>(1, 4);
        QueryWrapper<goods> goodsQueryWrapper = new QueryWrapper<>();
        goodsQueryWrapper.eq("goods_hot",1).ne("state",0);
//        List<goods> goods = goodsMapper.selectList(goodsQueryWrapper);
        List<goods> goods = goodsMapper.selectPage(goodsPage, goodsQueryWrapper).getRecords();
        return goods;
    }

    //查询所有最新商品信息
    @Override
    public List<goods> goodsNewAllList() {
        Page<goods> goodsPage = new Page<>(1, 4);
        QueryWrapper<goods> goodsQueryWrapper = new QueryWrapper<>();
        goodsQueryWrapper.eq("goods_new",1).ne("state",0);
//        List<goods> goods = goodsMapper.selectList(goodsQueryWrapper);
        List<goods> goods = goodsMapper.selectPage(goodsPage, goodsQueryWrapper).getRecords();
        return goods;
    }

    //查询所有普通商品信息
    @Override
    public List<goods> goodsAllList(Integer items,Integer total) {
        Page<goods> goodsPage = new Page<>(items, total);
        QueryWrapper<goods> goodsQueryWrapper = new QueryWrapper<>();
        goodsQueryWrapper.eq("goods_new",0).eq("goods_hot",0).ne("state",0);
//        List<goods> goods = goodsMapper.selectList(goodsQueryWrapper);
        List<goods> goods = goodsMapper.selectPage(goodsPage, goodsQueryWrapper).getRecords();
        return goods;
    }
}
