package com.fh.shop.api.common;



import java.io.Serializable;

public class OrderParam implements Serializable {
    private Integer payType;

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }
}
