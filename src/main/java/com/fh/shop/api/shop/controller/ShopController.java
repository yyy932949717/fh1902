package com.fh.shop.api.shop.controller;

import com.fh.shop.api.shop.biz.IShopService;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RequestMapping("shop")
@RestController
public class ShopController {
@Resource(name = "shopService")
    private IShopService shopService;

@RequestMapping("list")
    public Object list(String callback){
    MappingJacksonValue  mappingJacksonValue=new MappingJacksonValue(shopService.list());
    mappingJacksonValue.setJsonpFunction(callback);
    return mappingJacksonValue;
}



}
