package com.resources;

import com.fh.shop.api.interceptor.IdempotentInterceptor;
import com.fh.shop.api.interceptor.Myinterceptor;
import com.fh.shop.api.vip.controller.VipArgumentResolve;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import java.util.List;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(myinterceptor()).addPathPatterns("/**");
        registry.addInterceptor(idempotentInterceptor()).addPathPatterns("/**");
    }

    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(vipArgumentResolve());
    }


    @Bean  public Myinterceptor myinterceptor(){
        return new Myinterceptor();
    }

    @Bean public IdempotentInterceptor idempotentInterceptor(){
        return new IdempotentInterceptor();
    }

    @Bean public VipArgumentResolve vipArgumentResolve(){
        return new VipArgumentResolve();
    }
}
