package com;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@MapperScan("com.fh.shop.api.*.mapper")
@EnableScheduling//开启定时器
@EnableSwagger2

public class ShopApiV3Application {

    public static void main(String[] args) {
        SpringApplication.run(ShopApiV3Application.class, args);
    }

}
