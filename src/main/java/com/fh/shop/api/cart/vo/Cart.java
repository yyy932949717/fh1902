package com.fh.shop.api.cart.vo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Cart implements Serializable {
    private Long totalCont;
    private String totalPrice;
    private List<CartShop> cartShopList=new ArrayList<>();

    public Long getTotalCont() {
        return totalCont;
    }

    public void setTotalCont(Long totalCont) {
        this.totalCont = totalCont;
    }

    public String getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(String totalPrice) {
        this.totalPrice = totalPrice;
    }

    public List<CartShop> getCartShopList() {
        return cartShopList;
    }

    public void setCartShopList(List<CartShop> cartShopList) {
        this.cartShopList = cartShopList;
    }
}
