package com.fh.shop.api.cart.vo;

import java.io.Serializable;

public class CartShop implements Serializable {
    private Long   shopId;
    private String shopName;
    private String shopImg;
    private Long shopTotalCount;
    private String shopTotalPrice;
    private String shopPrice;

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

    public String getShopImg() {
        return shopImg;
    }

    public void setShopImg(String shopImg) {
        this.shopImg = shopImg;
    }

    public Long getShopTotalCount() {
        return shopTotalCount;
    }

    public void setShopTotalCount(Long shopTotalCount) {
        this.shopTotalCount = shopTotalCount;
    }

    public String getShopTotalPrice() {
        return shopTotalPrice;
    }

    public void setShopTotalPrice(String shopTotalPrice) {
        this.shopTotalPrice = shopTotalPrice;
    }

    public String getShopPrice() {
        return shopPrice;
    }

    public void setShopPrice(String shopPrice) {
        this.shopPrice = shopPrice;
    }
}
