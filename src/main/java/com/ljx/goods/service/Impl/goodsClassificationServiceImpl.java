package com.ljx.goods.service.Impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljx.goods.mapper.goodsClassificationMapper;
import com.ljx.goods.pojo.goodsClassification;
import com.ljx.goods.service.goodsClassificationService;
import com.ljx.goods.util.goodsByFenLeiResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class goodsClassificationServiceImpl extends ServiceImpl<goodsClassificationMapper, goodsClassification> implements goodsClassificationService {

    @Autowired
    goodsClassificationMapper goodsClassificationMapper;

    //展示全部分类情况
    @Override
    public goodsByFenLeiResult categoryByAll() {
        List<goodsClassification> goodsClassifications = goodsClassificationMapper.selectList(null);
        goodsByFenLeiResult goodsByFenLeiResult = new goodsByFenLeiResult();
        goodsByFenLeiResult.setCategory(goodsClassifications);
        return goodsByFenLeiResult;
    }
}
