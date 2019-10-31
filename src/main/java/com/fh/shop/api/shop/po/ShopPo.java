package com.fh.shop.api.shop.po;

import com.baomidou.mybatisplus.annotation.TableName;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
@TableName("t_shop")
public class ShopPo implements Serializable {
    private Long id;
    private String shopName;
    private BigDecimal price;
    private String imgUrl;
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss")
    private Date time;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
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

    public ShopPo() {
    }

    public ShopPo(Long id, String shopName, BigDecimal price, String imgUrl, Date time, Integer isUp, Integer ishot, Long sales) {
        this.id = id;
        this.shopName = shopName;
        this.price = price;
        this.imgUrl = imgUrl;
        this.time = time;
        this.isUp = isUp;
        this.ishot = ishot;
        this.sales = sales;
    }
}
