package com.fh.shop.api.payLog.controller;

import com.fh.shop.api.annotation.Annotation;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.payLog.biz.IPayLogService;
import com.fh.shop.api.vip.vo.VipVo;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.io.Serializable;

@RestController
@RequestMapping("/payLogs")
public class PayLogController {

    @Resource(name = "payLogService")
    private IPayLogService payLogService;


    @GetMapping("/createNative")
    @Annotation
    public ServerResponse createNative(VipVo vo){

        return payLogService.createNative(vo.getId());
    }

    @GetMapping("/payZt")
    @Annotation
    public Serializable  payZt(VipVo vo){
        return  payLogService.queryWxzf(vo.getId());
    }

}
