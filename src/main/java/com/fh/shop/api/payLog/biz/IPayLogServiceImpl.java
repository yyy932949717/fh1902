package com.fh.shop.api.payLog.biz;

import com.alibaba.fastjson.JSONObject;
import com.fh.shop.api.common.ResponseEnum;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.common.SytemConst;
import com.fh.shop.api.order.mapper.IOrderMapper;
import com.fh.shop.api.order.po.Orderes;
import com.fh.shop.api.payLog.mapper.IPayLogMapper;
import com.fh.shop.api.payLog.po.PayInfo;
import com.fh.shop.api.payLog.po.PayLog;
import com.fh.shop.api.util.*;
import com.github.wxpay.sdk.WXPay;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Service("payLogService")
public class IPayLogServiceImpl implements IPayLogService {

    @Autowired
    private IPayLogMapper payLogMapper;

    @Autowired
    private IOrderMapper orderMapper;

    @Override
    public ServerResponse createNative(Long id) {
        String payLogStr = RedisUtil.get(KeyUtil.buildPayLogKey(id));
        if(StringUtils.isEmpty(payLogStr)){
            return ServerResponse.error(ResponseEnum.PAYLOG_IS_NULL);
        }
        PayLog payLog = JSONObject.parseObject(payLogStr, PayLog.class);
        String outTradeNo = payLog.getOutTradeNo();
        BigDecimal payMoney1 = payLog.getPayMoney();
        int payMoney = BigDecimalUtil.multiply(payMoney1.toString(), "100").intValue();

        MyWxConfig config = new MyWxConfig();
        try {
            WXPay wxpay = new WXPay(config);
            Map<String, String> data = new HashMap<String, String>();
            data.put("body", "飞狐网购测试");
            data.put("out_trade_no", outTradeNo);
            data.put("fee_type", "CNY");
            data.put("total_fee", payMoney+""); //分
            data.put("notify_url", "http://www.example.com/wxpay/notify");
            data.put("trade_type", "NATIVE");  // 此处指定为扫码支付
            Date newDate = DateUtils.addMinutes(new Date(), 5);
            String DateStr = DateUtil.fromData(newDate, DateUtil.YYYYMMDDHHMMSS);
            data.put("time_expire", DateStr);  // 二维码失效时间

            Map<String, String> resp = wxpay.unifiedOrder(data);

            //统一下单
            String return_code = resp.get("return_code");
            String return_msg = resp.get("return_msg");
            if (!return_code.equalsIgnoreCase("SUCCESS")){
                System.out.println(return_msg);
                return ServerResponse.error(9999,"微信支付平台错误"+return_msg);
            }

            //resultCode是对的
            String result_code = resp.get("result_code");
            String err_code_des = resp.get("err_code_des");
            if (!result_code.equalsIgnoreCase("SUCCESS")){
                System.out.println(err_code_des);
                return ServerResponse.error(9999,"微信支付平台错误"+err_code_des);
            }

            String code_url = resp.get("code_url");
            PayInfo payInfo=new PayInfo();
            payInfo.setOrderId(outTradeNo);
            payInfo.setPrice(String.valueOf(payMoney1));
            payInfo.setWxUrl(code_url);
            return ServerResponse.success(payInfo);

        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error();
        }
    }

    @Override
    public Serializable queryWxzf(Long id) {
        String jsonStr = RedisUtil.get(KeyUtil.buildPayLogKey(id));
        if (StringUtils.isEmpty(jsonStr)){
            return ServerResponse.error(ResponseEnum.PAYLOG_IS_NULL);
        }
        PayLog payLog = JSONObject.parseObject(jsonStr, PayLog.class);
        String outTradeNo = payLog.getOutTradeNo();

        MyWxConfig wxConfig=new MyWxConfig();
        try {
            WXPay wxPay=new WXPay(wxConfig);
            Map<String,String> data=new HashMap<>();
            data.put("out_trade_no",outTradeNo);
            int count=0;
            while (true){
                Map<String, String> resp = wxPay.orderQuery(data);
                System.out.println(resp);

                String return_code = resp.get("return_code");
                String return_msg = resp.get("return_msg");
                if (!return_code.equalsIgnoreCase("SUCCESS")){
                    System.out.println(return_msg);
                    return ServerResponse.error(9999,"微信支付平台错误");
                }

                //resultCode是对的
                String result_code = resp.get("result_code");
                String err_code_des = resp.get("err_code_des");
                if (!result_code.equalsIgnoreCase("SUCCESS")){
                    System.out.println(err_code_des);
                    return ServerResponse.error(9999,"微信支付平台错误");
                }

                //resultCode是对的，resultCode也是对的
                String trade_state = resp.get("trade_state");
                //微信订单号
                String transaction_id = resp.get("transaction_id");

                if (trade_state.equalsIgnoreCase("SUCCESS")){
                    System.out.println("=======支付成功");

                    //支付成功，更新订单状态和支付时间
                    Orderes orderes=new Orderes();
                    orderes.setId(payLog.getOrderId());
                    Date date = new Date();
                    orderes.setOrderPayTime(date);
                    orderes.setOrderStatus(SytemConst.ORDER_PAY_STATUS_SUCCESS);
                    orderMapper.updateById(orderes);

                    //支付成功，更新支付日志状态和支付时间
                    PayLog pay=new PayLog();
                    pay.setOutTradeNo(outTradeNo);
                    pay.setPayTime(date);
                    pay.setPayStatus(SytemConst.PAY_STATUS_SUCCESS);
                    pay.setTransactionId(transaction_id);
                    payLogMapper.updateById(pay);

                    //清除redis中的支付日志
                    RedisUtil.del(KeyUtil.buildPayLogKey(id));
                    return ServerResponse.success();
                }

                count++;
                Thread.sleep(3000);
                if (count>=30){
                    System.out.println("======支付超时");
                    return ServerResponse.error(ResponseEnum.PAYCODE_IS_ERROR);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return ServerResponse.error();
        }

    }
}
