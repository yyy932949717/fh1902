package com.fh.shop.api.util;

import java.util.HashMap;
import java.util.Map;


public class PhoneCodeUtil {
    //网易云信的url
    private static  final String  SMS_URL="https://api.netease.im/sms/sendcode.action";
    //网易云信分配的账号
    private static  final String  APP_KEY="aa67fbc150181c60b9aad523118cec8c";
    //秘钥
    private static  final String  APP_SECRET="bea1c3175d04";
    //随机数
    private static final String NONCE="123456";
    //验证码长度
    private static final String PHONE_CODE="5";



    public static String sendPhoneCode(String phone){
        Map<String,String> headerMap=new HashMap();
        headerMap.put("AppKey",APP_KEY);
        headerMap.put("Nonce",NONCE);
        //当前时间
        String curTime = System.currentTimeMillis() + "";
        headerMap.put("CurTime",curTime);
        //checkSum 算法
        String checkSum = CheckSumBuilder.getCheckSum(APP_SECRET, NONCE, curTime);
        headerMap.put("CheckSum",checkSum);
        headerMap.put("Content-Type","application/x-www-form-urlencoded;charset=utf-8");

        Map<String,String> pMap=new HashMap<>();
        pMap.put("mobile",phone);
        pMap.put("codeLen",PHONE_CODE);

        String strJson = Httpclient.postHttpclient(SMS_URL, headerMap, pMap);
        return strJson;
    }
}
