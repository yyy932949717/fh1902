package com.fh.shop.api.order.biz;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.toolkit.IdWorker;
import com.fh.shop.api.cart.vo.Cart;
import com.fh.shop.api.cart.vo.CartShop;
import com.fh.shop.api.common.OrderParam;
import com.fh.shop.api.common.ResponseEnum;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.common.SytemConst;
import com.fh.shop.api.order.mapper.IOrderItemMapper;
import com.fh.shop.api.order.mapper.IOrderMapper;
import com.fh.shop.api.order.po.OrderItem;
import com.fh.shop.api.order.po.Orderes;
import com.fh.shop.api.payLog.mapper.IPayLogMapper;
import com.fh.shop.api.payLog.po.PayLog;
import com.fh.shop.api.shop.mapper.IShopMapper;
import com.fh.shop.api.shop.po.ShopPo;
import com.fh.shop.api.util.BigDecimalUtil;
import com.fh.shop.api.util.IdUtil;
import com.fh.shop.api.util.KeyUtil;
import com.fh.shop.api.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

@Service("orderService")
public class IOrderServiceImpl implements IOrderService {
    @Autowired
    private IOrderItemMapper orderItemMapper;

    @Autowired
    private IOrderMapper orderMapper;

    @Autowired
    private IShopMapper shopMapper;

    @Autowired
    private IPayLogMapper payLogMapper;


    @Override
    public ServerResponse addOrder(OrderParam orderParam, Long vipId) {
        //从redis中取出用户对应的购物车信息
        String cartJson = RedisUtil.hget(SytemConst.VIP_CART, KeyUtil.buildVipCart(vipId));
        if(StringUtils.isEmpty(cartJson)){
            return ServerResponse.error(ResponseEnum.VIPCART_IS_NULL);
        }

        Cart cart = JSONObject.parseObject(cartJson, Cart.class);
        //雪花算法
       // String timeId = IdWorker.getTimeId();
        String timeId = IdUtil.createId();

        //装库存不够的商品
        List<CartShop> noList=new ArrayList<>();

        List<CartShop> cartShopList = cart.getCartShopList();
        for (Iterator<CartShop> iterator = cartShopList.iterator(); iterator.hasNext();) {

            final CartShop cartShop = iterator.next();
            //插入订单详细
            OrderItem orderItem=new OrderItem();
            orderItem.setShopId(cartShop.getShopId());
            orderItem.setShopImg(cartShop.getShopImg());
            orderItem.setShopName(cartShop.getShopName());
            orderItem.setShopPrice(new BigDecimal(cartShop.getShopPrice()));
            orderItem.setOrderId(timeId);
            orderItem.setShopCount(cartShop.getShopTotalCount());
            orderItem.setShopSubTotalPrice(new BigDecimal(cartShop.getShopTotalPrice()));
            orderItem.setVipId(vipId);

            Long shopId = cartShop.getShopId();
            Long shopTotalCount = cartShop.getShopTotalCount();

            ShopPo shopPo = shopMapper.selectById(shopId);
            if(shopPo.getSales()>=shopTotalCount){
                //库存够
                Long updateSale = shopMapper.updateSale(shopId, shopTotalCount);
                if(updateSale==0){
                    //库存不够
                    noList.add(cartShop);
                    iterator.remove();
                }else{
                    //插入明细
                    orderItemMapper.insert(orderItem);
                }
            }else{
                //库存不够
                noList.add(cartShop);
                iterator.remove();
            }
        }
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

        if (cartShopList.size()==0){
            return ServerResponse.error(ResponseEnum.OREDER_ERROR);
        }

        //插入订单
        Orderes orderes=new Orderes();
        orderes.setId(timeId);
        orderes.setOrderTotalPrice(new BigDecimal(cart.getTotalPrice()));
        orderes.setOrderTotalCount(cart.getTotalCont());
        orderes.setVipId(vipId);
        orderes.setOrderCreateTime(new Date());
        orderes.setOrderStatus(1);
        orderes.setPayType(orderParam.getPayType());
        orderMapper.insert(orderes);

        //插入支付日志
        String outTradeNo = IdUtil.createId();

        PayLog payLog=new PayLog();
        payLog.setOutTradeNo(outTradeNo);
        payLog.setCreateTime(new Date());
        payLog.setOrderId(orderes.getId());
        payLog.setPayMoney(orderes.getOrderTotalPrice());
        payLog.setPayStatus(SytemConst.PAY_STATUS);
        payLog.setPayType(orderes.getPayType());
        payLog.setVipId(orderes.getVipId());

        payLogMapper.insert(payLog);
        //将支付订单存入到redis中
        String toJSONString = JSONObject.toJSONString(payLog);
        RedisUtil.set(KeyUtil.buildPayLogKey(vipId),toJSONString);

        //清空购物车
        RedisUtil.hdel(SytemConst.VIP_CART,KeyUtil.buildVipCart(vipId));

        return ServerResponse.success(noList);
    }
}
