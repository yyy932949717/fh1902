package com.fh.shop.api.cart.controller;

import com.fh.shop.api.annotation.Annotation;
import com.fh.shop.api.cart.biz.ICartService;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.vip.vo.VipVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/carts")
public class CartController {
    @Resource(name = "cartService")
    private ICartService cartService;

    @Autowired
    private HttpServletRequest request;

    @PostMapping()
    @Annotation
    public ServerResponse addCartShop(Long id,Long count){
        VipVo vipInfo = (VipVo) request.getAttribute("vipInfo");
        Long vipId = vipInfo.getId();
        return cartService.addCartShop(id,count,vipId);
    }

    @GetMapping
    @Annotation
    public ServerResponse queryCart(Long id){
        VipVo vipInfo = (VipVo) request.getAttribute("vipInfo");
        return cartService.queryCart(vipInfo.getId());
    }

    @DeleteMapping
    @Annotation
    public ServerResponse deleteCartShop(Long shopId){
        VipVo vipInfo = (VipVo) request.getAttribute("vipInfo");
        return cartService.deleteCartShop(shopId,vipInfo.getId());
    }

}

