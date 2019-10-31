package com.fh.shop.api.area.controller;

import com.fh.shop.api.area.biz.IAreaService;
import com.fh.shop.api.common.ServerResponse;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/areas")
@CrossOrigin("*")
@Api("测试")

public class ResultAreaController {

    @Resource(name = "areaService")
    private IAreaService areaService;

    @GetMapping(value = "/{id}")
    public ServerResponse areaList(@PathVariable Long id){
       return areaService.areaList(id);
    }



}
