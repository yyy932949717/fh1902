package com.fh.shop.api.order.po;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@TableName("t_order")
public class Orderes implements Serializable {
    @TableId(type = IdType.INPUT)
    private String id; //订单编号
    private Long vipId;//当前下单的会员id
    private Integer payType;//支付方式  1 微信支付  2  货到付款
    private BigDecimal orderTotalPrice; //订单的总金额
    private Long orderTotalCount; //订单中商品的总个数
    private Date orderCreateTime;//订单的创建时间
    private Date orderPayTime;//订单的支付时间
    private Integer orderStatus; //状态 1 支付  2未支付  3  已发货 4 确认订单  5    已完成评价
    private String orderStatusInfo;//订单的状态描述
    private Date orderCloseTime;//订单交易关闭的时间
    private Date orderDeliverTime;//订单发货时间
    private Date orderWinTime;//订单交易成功时间
    private Date orderAppraiseTime;//订单完成评价时间
    private String addressee; //收货人地址
    private String addresseePhone; //收货人电话
    private String addresseeName; //收货人姓名
    private String zipCode;//邮编
    private BigDecimal postage;//邮费
    private Integer isBill;//发票是否打印

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Long getVipId() {
        return vipId;
    }

    public void setVipId(Long vipId) {
        this.vipId = vipId;
    }

    public Integer getPayType() {
        return payType;
    }

    public void setPayType(Integer payType) {
        this.payType = payType;
    }

    public BigDecimal getOrderTotalPrice() {
        return orderTotalPrice;
    }

    public void setOrderTotalPrice(BigDecimal orderTotalPrice) {
        this.orderTotalPrice = orderTotalPrice;
    }

    public Long getOrderTotalCount() {
        return orderTotalCount;
    }

    public void setOrderTotalCount(Long orderTotalCount) {
        this.orderTotalCount = orderTotalCount;
    }

    public Date getOrderCreateTime() {
        return orderCreateTime;
    }

    public void setOrderCreateTime(Date orderCreateTime) {
        this.orderCreateTime = orderCreateTime;
    }

    public Date getOrderPayTime() {
        return orderPayTime;
    }

    public void setOrderPayTime(Date orderPayTime) {
        this.orderPayTime = orderPayTime;
    }

    public Integer getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(Integer orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getOrderStatusInfo() {
        return orderStatusInfo;
    }

    public void setOrderStatusInfo(String orderStatusInfo) {
        this.orderStatusInfo = orderStatusInfo;
    }

    public Date getOrderCloseTime() {
        return orderCloseTime;
    }

    public void setOrderCloseTime(Date orderCloseTime) {
        this.orderCloseTime = orderCloseTime;
    }

    public Date getOrderDeliverTime() {
        return orderDeliverTime;
    }

    public void setOrderDeliverTime(Date orderDeliverTime) {
        this.orderDeliverTime = orderDeliverTime;
    }

    public Date getOrderWinTime() {
        return orderWinTime;
    }

    public void setOrderWinTime(Date orderWinTime) {
        this.orderWinTime = orderWinTime;
    }

    public Date getOrderAppraiseTime() {
        return orderAppraiseTime;
    }

    public void setOrderAppraiseTime(Date orderAppraiseTime) {
        this.orderAppraiseTime = orderAppraiseTime;
    }

    public String getAddressee() {
        return addressee;
    }

    public void setAddressee(String addressee) {
        this.addressee = addressee;
    }

    public String getAddresseePhone() {
        return addresseePhone;
    }

    public void setAddresseePhone(String addresseePhone) {
        this.addresseePhone = addresseePhone;
    }

    public String getAddresseeName() {
        return addresseeName;
    }

    public void setAddresseeName(String addresseeName) {
        this.addresseeName = addresseeName;
    }

    public String getZipCode() {
        return zipCode;
    }

    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    public BigDecimal getPostage() {
        return postage;
    }

    public void setPostage(BigDecimal postage) {
        this.postage = postage;
    }

    public Integer getIsBill() {
        return isBill;
    }

    public void setIsBill(Integer isBill) {
        this.isBill = isBill;
    }
}
