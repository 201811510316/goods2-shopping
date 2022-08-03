package com.ljx.goods.pojo;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;

public class goodsClassification {

    //主键id
    @TableId(value="id",type= IdType.AUTO)
    private Integer id;

    //商品分类id
    private Integer categoryId;

    //商品分类名称
    private String categoryName;

    public goodsClassification() {
    }

    public goodsClassification(Integer id, Integer categoryId, String categoryName) {
        this.id = id;
        this.categoryId = categoryId;
        this.categoryName = categoryName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }

    @Override
    public String toString() {
        return "goodsClassification{" +
                "id=" + id +
                ", categoryId=" + categoryId +
                ", categoryName='" + categoryName + '\'' +
                '}';
    }
}
