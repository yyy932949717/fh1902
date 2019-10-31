package com.fh.shop.api.shop.vo;

import java.io.Serializable;
import java.math.BigDecimal;

public class ShopVo implements Serializable {
    private Long id;
    private String shopName;
    private String price;
    private String imgUrl;
    private String time;
    private Integer isUp;
    private Integer ishot;
    private Long    sales;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getShopName() {
        return shopName;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public Integer getIsUp() {
        return isUp;
    }

    public void setIsUp(Integer isUp) {
        this.isUp = isUp;
    }

    public Integer getIshot() {
        return ishot;
    }

    public void setIshot(Integer ishot) {
        this.ishot = ishot;
    }

    public Long getSales() {
        return sales;
    }

    public void setSales(Long sales) {
        this.sales = sales;
    }

    public ShopVo(Long id, String shopName, String price, String imgUrl, String time, Integer isUp, Integer ishot, Long sales) {
        this.id = id;
        this.shopName = shopName;
        this.price = price;
        this.imgUrl = imgUrl;
        this.time = time;
        this.isUp = isUp;
        this.ishot = ishot;
        this.sales = sales;
    }

    public ShopVo() {
    }
}
