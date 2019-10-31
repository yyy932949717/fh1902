package com.fh.shop.api.vip.controller;

import com.fh.shop.api.annotation.Annotation;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.shop.biz.IShopService;
import com.fh.shop.api.shop.po.ShopPo;
import com.fh.shop.api.util.Email;
import com.fh.shop.api.util.KeyUtil;
import com.fh.shop.api.util.RedisUtil;
import com.fh.shop.api.util.duan;
import com.fh.shop.api.vip.biz.IVipService;
import com.fh.shop.api.vip.po.Vip;
import com.fh.shop.api.vip.vo.VipVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/vip")
@CrossOrigin("*")
@Component
public class VipController {
    @Resource(name = "vipService")
    private IVipService vipService;

    @Autowired
    private HttpServletRequest request;
    @Resource(name = "shopService")
    private IShopService shopService;


    @Scheduled(cron = "0/50 * * * * ?")
    public void checkProductCount() throws Exception {
        List<ShopPo> list=shopService.queryShopSales();
        String str="<br>";
        for (ShopPo shopPo : list) {
            str+="商品名称:"+shopPo.getShopName()+"—商品价格："+shopPo.getPrice()+"—商品库存："+shopPo.getSales()+"<br>";
        }
        str+="<br>from：于一洋";
        Email.email("932949717@qq.com","商品库存欠缺提醒！",str);
    }




    @PostMapping("/login")
    public ServerResponse login(Vip vip){
        return vipService.login(vip);
    }

    @PostMapping("/phoneLogin")
    public ServerResponse phoneLogin(Vip vip){
        return vipService.phoneLogin(vip);
    }

    @Annotation
    @GetMapping("out")
    public ServerResponse out(VipVo vipInfo){
        RedisUtil.del(KeyUtil.buidVipKey(vipInfo.getName(),vipInfo.getUuid()));
        return ServerResponse.success();
    }

    @Annotation
    @GetMapping("/initVipInfo")
    public ServerResponse initVipInfo(VipVo vo){
        return ServerResponse.success(vo);
    }

    @PostMapping
    public ServerResponse add(Vip vip){
        return vipService.addVip(vip);
    }

    @GetMapping(value = "/{phone}")
    public ServerResponse getCode(@PathVariable String phone) {
        return vipService.getCode(phone);
    }

    @GetMapping("verifyName")
    public ServerResponse verifyName(String name){
        return vipService.verify(name);
    }

    @GetMapping("verifyPhone")
    public ServerResponse verifyPhone(String phone){
        return vipService.verifyPhone(phone);
    }

    @GetMapping("verifyEmail")
    public ServerResponse verifyEmail(String email){
        return vipService.verifyEmail(email);
    }

    }
