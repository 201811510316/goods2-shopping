package com.ljx.goods.util;

import com.ljx.goods.pojo.goods;
import com.ljx.goods.pojo.goodsImg;
import com.ljx.goods.pojo.goodsRemark;
import com.ljx.goods.pojo.goodsSlide;

import java.util.List;

/**
 * 商品详情信息封装
 */
public class goodsByIdResult {
    //获取详情图片
    private goodsImg ImgList;

    //获取轮播图图片
    private List<goodsSlide> slideList;

    //获取备注
    private goodsRemark remark;

    //获取某一个商品详细信息
    private goods goodsById;

    public goodsImg getImgList() {
        return ImgList;
    }

    public void setImgList(goodsImg imgList) {
        ImgList = imgList;
    }

    public List<goodsSlide> getSlideList() {
        return slideList;
    }

    public void setSlideList(List<goodsSlide> slideList) {
        this.slideList = slideList;
    }

    public goodsRemark getRemark() {
        return remark;
    }

    public void setRemark(goodsRemark remark) {
        this.remark = remark;
    }

    public goods getGoodsById() {
        return goodsById;
    }

    public void setGoodsById(goods goodsById) {
        this.goodsById = goodsById;
    }

    @Override
    public String toString() {
        return "goodsByIdResult{" +
                "goodsById=" + goodsById +
                ", ImgList=" + ImgList +
                ", slideList=" + slideList +
                ", remark=" + remark +
                '}';
    }
}
