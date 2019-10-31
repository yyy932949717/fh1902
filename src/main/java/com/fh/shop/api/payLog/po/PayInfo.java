package com.fh.shop.api.payLog.po;

import java.io.Serializable;

public class PayInfo implements Serializable {
    private String price;
    private String orderId;
    private String wxUrl;

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getWxUrl() {
        return wxUrl;
    }

    public void setWxUrl(String wxUrl) {
        this.wxUrl = wxUrl;
    }
}
