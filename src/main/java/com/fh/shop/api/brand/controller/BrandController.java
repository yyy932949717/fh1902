package com.fh.shop.api.brand.controller;

import com.fh.shop.api.annotation.Annotation;
import com.fh.shop.api.brand.biz.IBrandService;
import com.fh.shop.api.common.ServerResponse;
import org.springframework.http.converter.json.MappingJacksonValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
@RequestMapping("brand")
@RestController
public class BrandController {
    @Resource(name = "brandService")
    private IBrandService iBrandService;


    @Annotation
    @GetMapping("list")
    public ServerResponse list(){

        return iBrandService.list();

    }
}
