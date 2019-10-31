package com.fh.shop.api.cart.biz;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.api.cart.vo.Cart;
import com.fh.shop.api.cart.vo.CartShop;
import com.fh.shop.api.common.ResponseEnum;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.common.SytemConst;
import com.fh.shop.api.shop.mapper.IShopMapper;
import com.fh.shop.api.shop.po.ShopPo;
import com.fh.shop.api.util.BigDecimalUtil;
import com.fh.shop.api.util.KeyUtil;
import com.fh.shop.api.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service("cartService")
public class ICartServiceImpl implements ICartService {

    @Autowired
    private IShopMapper shopMapper;

    @Override
    public ServerResponse addCartShop(Long id, Long count, Long vipId) {

        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("id",id);
        ShopPo shopPo = shopMapper.selectOne(queryWrapper);
        //判断商品是否存在
        if (null==shopPo){
            return ServerResponse.error(ResponseEnum.SHOP_IS_NO);
        }
        //判断商品上下架状态
        if(shopPo.getIsUp()==SytemConst.SHOP_ISUO_NO){
            return ServerResponse.error(ResponseEnum.SHOP_ISUP_IS_NO);
        }
        //从redis中取出相应vipId的购物车
        String cartJson = RedisUtil.hget(SytemConst.VIP_CART,KeyUtil.buildVipCart(vipId));

        //该会员没有购物车
        if (StringUtils.isEmpty(cartJson)){
            Cart cart=new Cart();//创建购物车
            CartShop cartShop = getCartShop(id, count, shopPo);
            //为商品集合赋值
            List<CartShop> cartShopList=new ArrayList<>();
            cartShopList.add(cartShop);
            setCartShop(cart, cartShopList,vipId);
            //更新购物车 存入redis
            String jsonString = JSONObject.toJSONString(cart);
            RedisUtil.hset(SytemConst.VIP_CART,KeyUtil.buildVipCart(vipId),jsonString);
            return ServerResponse.success();
        }


        //该用户已经有购物车
        Cart cartRedis = JSONObject.parseObject(cartJson, Cart.class);
        List<CartShop> cartShopList = cartRedis.getCartShopList();
        //判断购物车中是否有此商品
        CartShop cartShop = getCartShop(id, cartShopList);
        if(null!=cartShop){
            //存在就更新购物车
            long shopTotalCount = cartShop.getShopTotalCount() + count;
            //商品数量为0时删除
        deleteShopCart(cartShopList, cartShop, shopTotalCount);
        cartShop.setShopTotalCount(shopTotalCount);
        String shopTotalPrice = BigDecimalUtil.multiply(cartShop.getShopPrice(), cartShop.getShopTotalCount().toString()).toString();
        cartShop.setShopTotalPrice(shopTotalPrice);
        setCartShop(cartRedis, cartShopList,vipId);
        //更新购物车 存入redis
        String jsonString = JSONObject.toJSONString(cartRedis);
        RedisUtil.hset(SytemConst.VIP_CART,KeyUtil.buildVipCart(vipId),jsonString);
        return ServerResponse.success();
        }
        //不存在就添加此商品
            CartShop cartShop1 = getCartShop(id, count, shopPo);
            cartShopList.add(cartShop1);
            setCartShop(cartRedis, cartShopList,vipId);
        String jsonString = JSONObject.toJSONString(cartRedis);
        RedisUtil.hset(SytemConst.VIP_CART,KeyUtil.buildVipCart(vipId),jsonString);
        return ServerResponse.success();
        }

    public void deleteShopCart(List<CartShop> cartShopList, CartShop cartShop, long shopTotalCount) {
        if (shopTotalCount==0){
            for (CartShop shop : cartShopList) {
                if(cartShop.getShopId()==shop.getShopId()){
                    cartShopList.remove(shop);
                    break;
                }
            }
        }
    }

    @Override
    public ServerResponse queryCart(Long id) {
        String key = KeyUtil.buildVipCart(id);
        String json = RedisUtil.hget(SytemConst.VIP_CART, key);
        Cart cart = JSONObject.parseObject(json, Cart.class);
        if (cart==null){
            return ServerResponse.error(ResponseEnum.VIPCART_IS_NULL);
        }
            return ServerResponse.success(cart);


    }

    @Override
    public ServerResponse deleteCartShop(Long shopId, Long id) {
        //判断用户是否有购物车
        String redisCart = RedisUtil.hget(SytemConst.VIP_CART, KeyUtil.buildVipCart(id));
        if (StringUtils.isEmpty(redisCart)){
            return ServerResponse.error(ResponseEnum.VIPCART_IS_NULL);
        }
        //购物车中有商品
        Cart cart = JSONObject.parseObject(redisCart, Cart.class);
        List<CartShop> cartShopList = cart.getCartShopList();
        for (CartShop cartShop : cartShopList) {
            if (shopId==cartShop.getShopId()){
                cartShopList.remove(cartShop);
                break;
            }else{
                return ServerResponse.error(ResponseEnum.SHOP_IS_NO);
            }
        }

        if (cartShopList.size()==0){
            //购物车中的商品为空就删除购物车
            RedisUtil.hdel(SytemConst.VIP_CART,KeyUtil.buildVipCart(id));
            return ServerResponse.success();
        }
         setCartShop(cart,cartShopList,id);
        String string = JSONObject.toJSONString(cart);
        RedisUtil.hset(SytemConst.VIP_CART,KeyUtil.buildVipCart(id),string);
        return ServerResponse.success();
    }


    public void setCartShop(Cart cart, List<CartShop> cartShopList,long vipId) {
        //计算总个数
        Long totalCont=0L;
        BigDecimal totalPrice=new BigDecimal(0);
        for (CartShop shop : cartShopList) {
            totalCont+=shop.getShopTotalCount();
            totalPrice=BigDecimalUtil.add(totalPrice.toString(),shop.getShopTotalPrice());
        }
        //为购物车赋值
        cart.setTotalCont(totalCont);
        cart.setTotalPrice(totalPrice.toString());
        cart.setCartShopList(cartShopList);
    }

    public CartShop getCartShop(Long id, Long count, ShopPo shopPo) {
        CartShop cartShop=new CartShop();//为购物车赋值
        cartShop.setShopId(id);
        cartShop.setShopName(shopPo.getShopName());
        cartShop.setShopImg(shopPo.getImgUrl());
        cartShop.setShopPrice(shopPo.getPrice().toString());
        cartShop.setShopTotalCount(count);
        String shopTotalPrice = BigDecimalUtil.multiply(cartShop.getShopPrice(), String.valueOf(count)).toString();
        cartShop.setShopTotalPrice(shopTotalPrice);
        return cartShop;
    }

    public CartShop getCartShop(Long id,List<CartShop> cartShopList) {
        CartShop c1=null;
        for (CartShop cartShop : cartShopList) {
            if(cartShop.getShopId()==id){
                c1=cartShop;
                break;
            }
        }
        return c1;
    }
}
