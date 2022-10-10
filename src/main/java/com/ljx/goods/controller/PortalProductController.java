package com.ljx.goods.controller;

import com.ljx.goods.annatation.LoginToken;
import com.ljx.goods.mapper.goodsMapper;
import com.ljx.goods.pojo.goods;
import com.ljx.goods.service.goodsService;
import com.ljx.goods.util.goodsByIdResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 前台商品管理
 */
@RestController
@RequestMapping("/product")
public class PortalProductController {
    /**
     *  可以综合搜索、筛选商品
     *  获取商品详情
     */

    @Autowired
    goodsService goodsService;

    @Autowired
    goodsMapper goodsMapper;

    //获取商品详细信息
    @LoginToken
    @RequestMapping("/goods/{id}")
    public Object goodsById(@PathVariable("id")Integer goodsId){
        Map<String,Object> resultObj = new HashMap<>();
        goodsByIdResult oneByDetail1 = goodsService.findOneByDetail(goodsId);
        resultObj.put("code",200);
        resultObj.put("message","查询成功");
        resultObj.put("data",oneByDetail1);
        return resultObj;
    }

    //搜索商品
    @RequestMapping("/mohu")
    public Object goodsByBaserch(@RequestParam("word")String keyword){
        Map<String,Object> resultObj = new HashMap<>();
        List<goods> goods = goodsService.MoHuByGoods(keyword);
        resultObj.put("code",200);
        resultObj.put("message","查询完毕");
        resultObj.put("data",goods);
        return resultObj;
    }

    @RequestMapping("/hot")
    public Object goodsByHot(){
        Map<String,Object> resultObj = new HashMap<>();
        List<goods> goodsHot = goodsService.goodsHotAllList();
        resultObj.put("code",200);
        resultObj.put("message","查询成功");
        resultObj.put("hot",goodsHot);
        return resultObj;
    }

    @RequestMapping("/new")
    public Object goodsByNew(){
        Map<String,Object> resultObj = new HashMap<>();
        List<goods> goodsNew = goodsService.goodsNewAllList();
        resultObj.put("code",200);
        resultObj.put("message","查询成功");
        resultObj.put("new",goodsNew);
        return resultObj;
    }

    @RequestMapping("/goods")
    public Object goodsByGoods(){
        Map<String,Object> resultObj = new HashMap<>();
        List<goods> goods = goodsService.goodsAllList();
        resultObj.put("code",200);
        resultObj.put("message","查询成功");
        resultObj.put("goods",goods);
        return resultObj;
    }

}
