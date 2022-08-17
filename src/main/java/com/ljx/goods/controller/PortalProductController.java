package com.ljx.goods.controller;

import com.ljx.goods.mapper.goodsMapper;
import com.ljx.goods.pojo.goods;
import com.ljx.goods.service.goodsService;
import com.ljx.goods.util.goodsByIdResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 前台商品管理
 */
@Controller
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
    @RequestMapping("/goods/{id}")
    @ResponseBody
    public Object goodsById(@PathVariable("id")Integer goodsId){
        Map resultObj = new HashMap();
        goodsByIdResult oneByDetail1 = goodsService.findOneByDetail(goodsId);
        resultObj.put("code",200);
        resultObj.put("message","查询成功");
        resultObj.put("data",oneByDetail1);
        return resultObj;
    }

    //搜索商品
    @RequestMapping("/mohu")
    @ResponseBody
    public Object goodsByBaserch(@RequestParam("word")String keyword){
        Map resultObj = new HashMap();
        List<goods> goods = goodsService.MoHuByGoods(keyword);
        resultObj.put("code",200);
        resultObj.put("message","查询完毕");
        resultObj.put("data",goods);
        return resultObj;
    }

    //获取全部热门商品
    @RequestMapping("/hot")
    @ResponseBody
    public Object goodsByHot(@RequestParam(value = "page",defaultValue = "1")Integer page,@RequestParam(value = "rows",defaultValue = "4")Integer rows){
        Map resultObj = new HashMap();
        List<goods> goodsHot = goodsService.goodsHotAllList(page, rows);
        resultObj.put("code",200);
        resultObj.put("message","查询成功");
        resultObj.put("hot",goodsHot);
        return resultObj;
    }

    //获取全部最新商品
    @RequestMapping("/new")
    @ResponseBody
    public Object goodsByNew(@RequestParam(value = "page",defaultValue = "1")Integer page,@RequestParam(value = "rows",defaultValue = "4")Integer rows){
        Map resultObj = new HashMap();
        List<goods> goodsNew = goodsService.goodsNewAllList(page, rows);
        resultObj.put("code",200);
        resultObj.put("message","查询成功");
        resultObj.put("new",goodsNew);
        return resultObj;
    }

    //获取全部普通商品
    @RequestMapping("/goods")
    @ResponseBody
    public Object goodsByGoods(@RequestParam(value = "page",defaultValue = "1")Integer page,@RequestParam(value = "rows",defaultValue = "4")Integer rows){
        Map resultObj = new HashMap();
        List<goods> goods = goodsService.goodsAllList(page, rows);
        resultObj.put("code",200);
        resultObj.put("message","查询成功");
        resultObj.put("goods",goods);
        return resultObj;
    }

}
