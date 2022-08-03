package com.ljx.goods.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.ljx.goods.pojo.goodsClassification;
import com.ljx.goods.util.goodsByFenLeiResult;

public interface goodsClassificationService extends IService<goodsClassification> {

    goodsByFenLeiResult categoryByAll();
}
