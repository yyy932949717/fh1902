package com.fh.shop.api.order.po;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;

@TableName("t_order_item")
public class OrderItem implements Serializable {
    private String orderId;//订单id 外键
    private Long shopId;//商品id  外键
    private String shopName;//商品名
    private BigDecimal shopPrice;//单个商品价格
    private Long shopCount;//用户已选中商品的数量
    private BigDecimal shopSubTotalPrice;//商品的小计   价格
    private String shopImg;//商品图片
    private Long vipId;//会员id    冗余参数/属性

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Long getShopId() {
        return shopId;
    }

    public void setShopId(Long shopId) {
        this.shopId = shopId;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public BigDecimal getShopPrice() {
        return shopPrice;
    }

    public void setShopPrice(BigDecimal shopPrice) {
        this.shopPrice = shopPrice;
    }

    public Long getShopCount() {
        return shopCount;
    }

    public void setShopCount(Long shopCount) {
        this.shopCount = shopCount;
    }

    public BigDecimal getShopSubTotalPrice() {
        return shopSubTotalPrice;
    }

    public void setShopSubTotalPrice(BigDecimal shopSubTotalPrice) {
        this.shopSubTotalPrice = shopSubTotalPrice;
    }

    public String getShopImg() {
        return shopImg;
    }

    public void setShopImg(String shopImg) {
        this.shopImg = shopImg;
    }

    public Long getVipId() {
        return vipId;
    }

    public void setVipId(Long vipId) {
        this.vipId = vipId;
    }
}
