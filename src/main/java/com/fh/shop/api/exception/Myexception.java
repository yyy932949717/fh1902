package com.fh.shop.api.exception;

import com.fh.shop.api.common.ResponseEnum;

public class Myexception  extends RuntimeException{

    private ResponseEnum responseEnum;

    public  Myexception(ResponseEnum responseEnum){
        this.responseEnum=responseEnum;
    }

    public ResponseEnum getResponseEnum(){
        return this.responseEnum;
    }
}
