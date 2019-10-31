package com.fh.shop.api.util;

public class KeyUtil {
    public static String buildCodeKey(String data){
        return "code:"+data;
    }

    public static String MENU_TYPE_LIST(String data){
        return "MENU_TYPE_LIST:"+data;
    }
    public static String USER_URL(String data){
        return "USER_URL:"+data;
    }

    public static String MENU_LIST(String data){
        return "MENU_LIST:"+data;
    }

    public static String LOGIN_USER_(String data){
        return "LOGIN_USER_:"+data;
    }

    public static String buildPhoneKey(String data){
        return "SMS:"+data;
    }

    public static String buidVipKey(String name,String uuid){
        return  "vip:"+name+":"+uuid;
    }

    public static String buildVipCart(Long id){
        return "vipCart:"+id+":";
    }

    public static String buildPayLogKey(Long data){
        return "vipPayLog:"+data;
    }
}
