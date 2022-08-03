package com.ljx.goods.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

public class goods {
    //商品id
    @TableId(value="goods_id",type= IdType.AUTO)
    private Integer goodsId;

    //商品名称
    private String goodsName;

    //商品价格
    private Integer goodsPrice;

    //商品销量
    private Integer goodsSales;

    //商品库存
    private Integer goodsStock;

    //商品详细信息(描述)
    private String detail;

    //商品分类id（外键）
    private Integer categoryId;

    //商品规格
    private Integer defaultSize;

    //商品状态(0为下架,1为上架中,2为新品)
    private Integer state;

    //商品主要图片
    private String tupian;

    //热门商品（1表示热门商品，0表示普通商品）
    private Integer goodsHot;

    //最新商品（0表示旧商品，1表示最新商品）
    private Integer goodsNew;

    public goods() {
    }

    public goods(Integer goodsId, String goodsName, Integer goodsPrice, Integer goodsSales, Integer goodsStock, String detail, Integer categoryId, Integer defaultSize, Integer state, String tupian, Integer goodsHot, Integer goodsNew) {
        this.goodsId = goodsId;
        this.goodsName = goodsName;
        this.goodsPrice = goodsPrice;
        this.goodsSales = goodsSales;
        this.goodsStock = goodsStock;
        this.detail = detail;
        this.categoryId = categoryId;
        this.defaultSize = defaultSize;
        this.state = state;
        this.tupian = tupian;
        this.goodsHot = goodsHot;
        this.goodsNew = goodsNew;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public Integer getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(Integer goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public Integer getGoodsSales() {
        return goodsSales;
    }

    public void setGoodsSales(Integer goodsSales) {
        this.goodsSales = goodsSales;
    }

    public Integer getGoodsStock() {
        return goodsStock;
    }

    public void setGoodsStock(Integer goodsStock) {
        this.goodsStock = goodsStock;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getDefaultSize() {
        return defaultSize;
    }

    public void setDefaultSize(Integer defaultSize) {
        this.defaultSize = defaultSize;
    }

    public Integer getState() {
        return state;
    }

    public void setState(Integer state) {
        this.state = state;
    }

    public String getTupian() {
        return tupian;
    }

    public void setTupian(String tupian) {
        this.tupian = tupian;
    }

    public Integer getGoodsHot() {
        return goodsHot;
    }

    public void setGoodsHot(Integer goodsHot) {
        this.goodsHot = goodsHot;
    }

    public Integer getGoodsNew() {
        return goodsNew;
    }

    public void setGoodsNew(Integer goodsNew) {
        this.goodsNew = goodsNew;
    }

    @Override
    public String toString() {
        return "goods{" +
                "goodsId=" + goodsId +
                ", goodsName='" + goodsName + '\'' +
                ", goodsPrice=" + goodsPrice +
                ", goodsSales=" + goodsSales +
                ", goodsStock=" + goodsStock +
                ", detail='" + detail + '\'' +
                ", categoryId=" + categoryId +
                ", defaultSize=" + defaultSize +
                ", state=" + state +
                ", tupian='" + tupian + '\'' +
                ", goodsHot=" + goodsHot +
                ", goodsNew=" + goodsNew +
                '}';
    }
}
