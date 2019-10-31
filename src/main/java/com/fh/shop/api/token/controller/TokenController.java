package com.fh.shop.api.token.controller;

import com.fh.shop.api.annotation.Annotation;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.token.biz.TokenService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;


@RestController
@RequestMapping("/token")
public class TokenController {
    @Resource(name = "tokenService")
    private TokenService tokenService;

    @Annotation
    @GetMapping
    public ServerResponse gertToken(){
        return tokenService.gertToken();
    }
}
