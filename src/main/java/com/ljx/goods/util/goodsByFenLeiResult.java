package com.ljx.goods.util;



import com.ljx.goods.pojo.goods;
import com.ljx.goods.pojo.goodsClassification;

import java.util.List;

/**
 * 商品分类封装
 */
public class goodsByFenLeiResult {

    //展示分类商品
    private List<goods> fenLeiGoods;

    //展示分类信息
    private List<goodsClassification> category;

    public List<goods> getFenLeiGoods() {
        return fenLeiGoods;
    }

    public void setFenLeiGoods(List<goods> fenLeiGoods) {
        this.fenLeiGoods = fenLeiGoods;
    }

    public List<goodsClassification> getCategory() {
        return category;
    }

    public void setCategory(List<goodsClassification> category) {
        this.category = category;
    }
}
