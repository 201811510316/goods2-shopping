package com.ljx.goods.service.Impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.ljx.goods.mapper.goodsMapper;
import com.ljx.goods.mapper.shoppingCartMapper;
import com.ljx.goods.pojo.goods;
import com.ljx.goods.pojo.shoppingCart;
import com.ljx.goods.service.shoppingCartService;
import com.ljx.goods.util.useless.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class shoppingCartServiceImpl implements shoppingCartService {

    @Autowired
    shoppingCartMapper shoppingCartMapper;

    @Autowired
    goodsMapper goodsMapper;

    //将商品添加到购物车中
    @Override
    public CommonResult goodsAddCart(shoppingCart shoppingCart) {
        QueryWrapper<shoppingCart> wrapper = new QueryWrapper<>();
        wrapper.eq("goods_id",shoppingCart.getGoodsId()).eq("user_id",shoppingCart.getUserId());
        shoppingCart selectByCart = shoppingCartMapper.selectOne(wrapper);
        //判断购物车中是否已存在该商品，有的话数量加1
        if(selectByCart !=null){
            selectByCart.setCount(selectByCart.getCount()+1);
            return updateCart(selectByCart);
        }
        QueryWrapper<goods> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("goods_id",shoppingCart.getGoodsId());
        goods oneByDetail = goodsMapper.selectOne(wrapper1);
        //判断商品库存
        if(oneByDetail.getGoodsStock() ==null){
            return CommonResult.failed("该商品买完了");
        }
        //判断商品购买数量是否超过最大值
        if(shoppingCart.getCount()>15){
            return CommonResult.failed("超出单个商品的最大购买数量！");
        }
        //判断商品购买数量是否大于商品库存
        if(oneByDetail.getGoodsStock()-shoppingCart.getCount()<0){
            return CommonResult.failed("商品数量只有["+oneByDetail.getGoodsStock()+"]");
        }
        QueryWrapper<shoppingCart> wrapper2 = new QueryWrapper<>();
        wrapper2.eq("user_id",shoppingCart.getUserId());
        if(shoppingCartMapper.selectCount(wrapper2)>30){
            return CommonResult.failed("超出购物车最大容量！");
        }
        if(shoppingCartMapper.insert(shoppingCart)>0){
            return new CommonResult(200,"添加成功");
        }
        return CommonResult.failed("添加失败");
    }

    //查看用户的购物车
    @Override
    public List<shoppingCart> selectUserCart(Integer userId) {
        QueryWrapper<shoppingCart> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        List<shoppingCart> shoppingCarts = shoppingCartMapper.selectList(wrapper);
        return shoppingCarts;
    }

    //删除购物车中某一个商品
    @Override
    public CommonResult deleteCart(Integer goodsId) {
        QueryWrapper<shoppingCart> wrapper = new QueryWrapper<>();
        wrapper.eq("goods_id",goodsId);
        Integer integer = shoppingCartMapper.delete(wrapper);
        if(integer>0){
            return new CommonResult(200,"删除成功");
        }
        return CommonResult.failed("删除失败");
    }

    //清空购物车
    @Override
    public Integer dropCart() {
        Integer integer = shoppingCartMapper.delete(null);
        return integer;
    }

    //修改购物车中的商品数量
    @Override
    public CommonResult updateCart(shoppingCart shoppingCart) {
        QueryWrapper<shoppingCart> wrapper = new QueryWrapper<>();
        wrapper.eq("id",shoppingCart.getId());
        shoppingCart shoppingCart1 = shoppingCartMapper.selectOne(wrapper);
        if(shoppingCart1 ==null){
            return CommonResult.failed("未查询到记录！");
        }
        if(shoppingCart.getCount()>15){
            return CommonResult.failed("超出单个商品的最大购买数量！");
        }
        if(shoppingCart.getCount()==0){
            return deleteCart(shoppingCart.getGoodsId());
        }
        shoppingCart1.setCount(shoppingCart.getCount());
        if(shoppingCartMapper.update(shoppingCart1,new UpdateWrapper<shoppingCart>().eq("id",shoppingCart1.getId()))>0){
            return new CommonResult(200,"修改成功");
        }
        return CommonResult.failed("修改失败");
    }
}
