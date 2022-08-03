package com.ljx.goods.controller;

import com.ljx.goods.pojo.goodsAd;
import com.ljx.goods.service.goodsClassificationService;
import com.ljx.goods.service.goodsService;
import com.ljx.goods.util.goodsByFenLeiResult;
import com.ljx.goods.util.goodsContentResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 首页内容
 */

@Controller
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

    //首页内容信息展示
    @RequestMapping(value = "/index",method = RequestMethod.GET)
    public String  goodsAll(HttpServletRequest request){
        goodsContentResult all = goodsService.findAll();
        List<goodsAd> goodsAd = goodsService.getGoodsAd();
        request.setAttribute("goods",all);
        request.setAttribute("goodsAd",goodsAd);
        return "index";
    }

    //展示首页横幅图片
    @RequestMapping("/ad")
    @ResponseBody
    public Object banner(){
        Map resultObj = new HashMap();
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
    @RequestMapping(value = "/getall",method = RequestMethod.GET)
    @ResponseBody
    public Object goodsAll1(){
        Map resultObj = new HashMap();
        goodsContentResult all = goodsService.findAll();
        resultObj.put("code",200);
        resultObj.put("message","查询成功");
        resultObj.put("data",all);
        return resultObj;
    }

    //根据分类获取专题(商品)
    @RequestMapping(value = "/fenlei",method = RequestMethod.GET)
    @ResponseBody
    public Object goodsByFenLei(@RequestParam("id") Integer categoryId){
        Map resultObj = new HashMap();
        goodsByFenLeiResult allByType = goodsService.findAllByType(categoryId);
        resultObj.put("code",200);
        resultObj.put("message","查询成功");
        resultObj.put("data",allByType);
        return resultObj;
    }

    //获取分类信息--分类名称
    @RequestMapping(value = "/categoryName",method = RequestMethod.GET)
    @ResponseBody
    public Object goodsByCategoryName(){
        Map resultObj = new HashMap();
        goodsByFenLeiResult categoryByAll = goodsClassificationService.categoryByAll();
        resultObj.put("code",200);
        resultObj.put("message","查询成功");
        resultObj.put("data",categoryByAll);
        return resultObj;
    }

}
