package com.fh.shop.api.common;

import java.io.Serializable;

public class ServerResponse implements Serializable {
    private  Integer code;
    private  String msg;
    private  Object data;

    private ServerResponse() {
    }

    private ServerResponse(Integer code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public  static ServerResponse success(){
        return new ServerResponse(200,"ok",null);
    }

    public  static ServerResponse success(Object data){
        return new ServerResponse(200,"ok",data);
    }

    public  static ServerResponse error(){
        return new ServerResponse(-1,"error",null);
    }
    public  static ServerResponse error(int code,String data){
        return new ServerResponse(code,"error",data);
    }

    public  static ServerResponse error(ResponseEnum responseEnum){
        return new ServerResponse(responseEnum.getCode(),responseEnum.getMsg(),null);
    }
    public  static ServerResponse login(ResponseEnum responseEnum){
        return new ServerResponse(responseEnum.getCode(),responseEnum.getMsg(),null);
    }
    public  static ServerResponse updatePasswrod(ResponseEnum responseEnum){
        return new ServerResponse(responseEnum.getCode(),responseEnum.getMsg(),null);
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    public Object getData() {
        return data;
    }
}
