package com.ljx.goods.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

/**
 * 购物车
 */
public class shoppingCart {

    //购物车id
    @TableId(value="id",type= IdType.AUTO)
    private Integer id;

    //商品id
    private Integer goodsId;

    //商品数量
    private Integer count;

    //商品名称
    private String goodsName;

    //商品主要图片
    private String tupian;

    //商品价格
    private Integer goodsPrice;

    //用户id
    private Integer userId;

    public shoppingCart() {
    }

    public shoppingCart(Integer id, Integer goodsId, Integer count, String goodsName, String tupian, Integer goodsPrice, Integer userId) {
        this.id = id;
        this.goodsId = goodsId;
        this.count = count;
        this.goodsName = goodsName;
        this.tupian = tupian;
        this.goodsPrice = goodsPrice;
        this.userId = userId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getTupian() {
        return tupian;
    }

    public void setTupian(String tupian) {
        this.tupian = tupian;
    }

    public Integer getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(Integer goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "shoppingCart{" +
                "id=" + id +
                ", goodsId=" + goodsId +
                ", count=" + count +
                ", goodsName='" + goodsName + '\'' +
                ", tupian='" + tupian + '\'' +
                ", goodsPrice=" + goodsPrice +
                ", userId=" + userId +
                '}';
    }
}
