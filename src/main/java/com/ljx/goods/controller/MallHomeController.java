package com.ljx.goods.controller;

import com.ljx.goods.pojo.goodsAd;
import com.ljx.goods.service.goodsClassificationService;
import com.ljx.goods.service.goodsService;
import com.ljx.goods.util.goodsByFenLeiResult;
import com.ljx.goods.util.goodsContentResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 首页内容
 */

@RestController
public class MallHomeController {
    /**
     *  首页内容信息展示
     *  获取首页商品分类
     *  根据分类获取专题
     */

    @Autowired
    goodsService goodsService;

    @Autowired
    goodsClassificationService goodsClassificationService;

//    //首页内容信息展示
//    @RequestMapping(value = "/index",method = RequestMethod.GET)
//    public String  goodsAll(HttpServletRequest request){
//        goodsContentResult all = goodsService.findAll();
//        List<goodsAd> goodsAd = goodsService.getGoodsAd();
//        request.setAttribute("goods",all);
//        request.setAttribute("goodsAd",goodsAd);
//        return "index";
//    }

    //展示首页横幅图片
    @GetMapping("/ad")
    public Object banner(){
        Map<String,Object> resultObj = new HashMap<>();
        List<goodsAd> goodsAd = goodsService.getGoodsAd();
        if(goodsAd !=null){
            resultObj.put("code",200);
            resultObj.put("message","查询成功");
            resultObj.put("banner",goodsAd);
            return resultObj;
        }else{
            resultObj.put("code",404);
            resultObj.put("message","查询失败");
            return resultObj;
        }
    }

    //首页内容信息展示
    @GetMapping(value = "/getall")
    public Object goodsAll1(){
        Map<String,Object> resultObj = new HashMap<>();
        goodsContentResult all = goodsService.findAll();
        resultObj.put("code",200);
        resultObj.put("message","查询成功");
        resultObj.put("data",all);
        return resultObj;
    }

    //根据分类获取专题(商品)
    @GetMapping(value = "/fenlei")
    public Object goodsByFenLei(@RequestParam("id") Integer categoryId){
        Map<String,Object> resultObj = new HashMap<>();
        goodsByFenLeiResult allByType = goodsService.findAllByType(categoryId);
        resultObj.put("code",200);
        resultObj.put("message","查询成功");
        resultObj.put("data",allByType);
        return resultObj;
    }

    //获取分类信息--分类名称
    @GetMapping(value = "/categoryName")
    public Object goodsByCategoryName(){
        Map<String,Object> resultObj = new HashMap<>();
        goodsByFenLeiResult categoryByAll = goodsClassificationService.categoryByAll();
        resultObj.put("code",200);
        resultObj.put("message","查询成功");
        resultObj.put("data",categoryByAll);
        return resultObj;
    }

}
