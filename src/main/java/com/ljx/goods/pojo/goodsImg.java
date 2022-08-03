package com.ljx.goods.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

public class goodsImg {

    //商品详细图片id
    @TableId(value="id",type= IdType.AUTO)
    private Integer id;

    //商品id（外键）
    private Integer goodsId;

    //商品详细图片地址
    private String img;

    public goodsImg(Integer id, Integer goodsId, String img) {
        this.id = id;
        this.goodsId = goodsId;
        this.img = img;
    }

    public goodsImg() {
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }

    @Override
    public String toString() {
        return "goodsImg{" +
                "id=" + id +
                ", goodsId=" + goodsId +
                ", img='" + img + '\'' +
                '}';
    }
}
