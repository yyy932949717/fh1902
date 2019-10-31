package com.fh.shop.api.order.controller;

import com.fh.shop.api.annotation.Annotation;
import com.fh.shop.api.annotation.Idempotent;
import com.fh.shop.api.common.OrderParam;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.order.biz.IOrderService;
import com.fh.shop.api.util.DateUtil;
import com.fh.shop.api.vip.vo.VipVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.xml.crypto.Data;
import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;

@RestController
@RequestMapping("/orders")
public class OrderController {

    @Resource(name = "orderService")
    private IOrderService orderService;

    @PostMapping("/addOrder")
    @Annotation
    @Idempotent
    public ServerResponse addOrder(OrderParam orderParam, VipVo vo){
        return orderService.addOrder(orderParam,vo.getId());
    }


//
//    @RequestMapping("/native")
//      public Map<String, String> natives(HttpServletRequest request) throws Exception{
//              // 生成订单
//        TreeMap<String, String> packageParams = new TreeMap<String, String>();
//        // 企业号或公众号id  商户号
//        packageParams.put("appid", "");
//        // 企业收款账号
//        packageParams.put("mch_id", "");
//        // 随机字符串
//        String nonce_str = UUID.randomUUID().toString();
//        packageParams.put("nonce_str", nonce_str);
//        //商品名称
//        packageParams.put("body", "yyy测试");
//        // 附加数据
//        packageParams.put("attach", "");
//        // 订单号
//        String out_trade_no = TenpayUtil.getRandomStr();
//
//                Date date = new Date();
//                String s = String.valueOf(DateUtil.fromData(date, DateUtil.YYYY_MM_DD_HH_MM_SS));
//        packageParams.put("out_trade_no", out_trade_no);
//
//        // 支付总金额(微信官方的支付单位是分)
//        String totalFee=TenpayUtil.getMoney("0.01");
//        packageParams.put("total_fee", totalFee);
//
//        // 生成订单的机器IP
//        String ip =request.getRemoteAddr();
//        packageParams.put("spbill_create_ip", ip);
//
//        // 回调URL
//        packageParams.put("notify_url", we.getNotifyurl());
//
//        // 设置支付方式
//        packageParams.put("trade_type", "NATIVE");
//
//        // 生成数字签名
//        String sign = MD5Util.createSign(packageParams, we.getAppid(), we.getPartnerkey());
//        //out_trade_no  totalFee  ip
//        jdbc.update("insert into t_weixin(id,out_trade_no,totalFee,ip,flag,create_date,create_user)values(?,?,?,?,?,now(),?)",new Object[]{UUID.randomUUID().toString().replaceAll("-",""),out_trade_no,totalFee,ip,"1","张崇俊"});
//        // 拼接xml数据
//        String xml = "<xml>" + "<appid>" + we.getAppid() + "</appid>"
//        + "<mch_id>" + we.getPartner() + "</mch_id>" + "<nonce_str>"
//        + nonce_str + "</nonce_str>" + "<sign>" + sign + "</sign>"
//        + "<body><![CDATA[测试]]></body>" + "<out_trade_no>"
//        + out_trade_no + "</out_trade_no>" + "<attach>" + ""
//        + "</attach>" + "<total_fee>" + totalFee + "</total_fee>"
//        + "<spbill_create_ip>" + ip+ "</spbill_create_ip>"
//        + "<notify_url>" + we.getNotifyurl()+ "</notify_url>"
//        + "<trade_type>" + "NATIVE"+ "</trade_type>" + "</xml>";
//        //通过HttpClient对象向微信支付后台发送xml数据
//        String code_url="";
//        Map<String, String> maps = new TreeMap<String,String>();
//        try {
//        String createOrederURL="https://api.mch.weixin.qq.com/pay/unifiedorder";
//        HttpClient httpClient=new HttpClient(createOrederURL);
//        httpClient.setHttps(true);
//        httpClient.setXmlParam(xml);
//        httpClient.post();
//        String xmlStr=httpClient.getContent(); //从微信支付后台获取订单信息
//        System.out.println(xmlStr);
//
//        //解析xml数据得到用于支付的url地址
//        Map<String, String> map =TenpayUtil.doXMLParse(xmlStr);
//        maps.put("code_url", map.get("code_url"));
//
//        maps.put("out_trade_no", out_trade_no);
//        } catch (Exception e) {
//        e.printStackTrace();
//        }
//        //返回订单信息到native.jsp
//        return maps;
//
//
//
//
//
//
}
