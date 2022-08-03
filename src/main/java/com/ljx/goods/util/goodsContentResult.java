package com.ljx.goods.util;

import com.ljx.goods.pojo.goods;

import java.util.List;

/**
 * 首页内容返回信息封装
 */
public class goodsContentResult {
    //获取普通商品
    private List<goods> findAllGoods;

    //获取热门商品
    private List<goods> findAllHots;

    //获取最新商品
    private List<goods> findAllNews;

    public List<goods> getFindAllHots() {
        return findAllHots;
    }

    public void setFindAllHots(List<goods> findAllHots) {
        this.findAllHots = findAllHots;
    }

    public List<goods> getFindAllNews() {
        return findAllNews;
    }

    public void setFindAllNews(List<goods> findAllNews) {
        this.findAllNews = findAllNews;
    }

    public List<goods> getFindAllGoods() {
        return findAllGoods;
    }

    public void setFindAllGoods(List<goods> findAllGoods) {
        this.findAllGoods = findAllGoods;
    }

}
