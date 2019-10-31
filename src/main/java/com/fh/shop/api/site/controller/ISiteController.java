package com.fh.shop.api.site.controller;

import com.fh.shop.api.annotation.Annotation;
import com.fh.shop.api.annotation.Idempotent;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.site.biz.ISiteService;
import com.fh.shop.api.site.po.Site;
import com.fh.shop.api.vip.vo.VipVo;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/sites")
public class ISiteController {
    @Resource(name = "siteService")
    private ISiteService siteService;


    @PostMapping
    @Annotation
    public ServerResponse addSite(Site site, VipVo vo){
     return  siteService.addSite(site,vo.getId());
    }

    @DeleteMapping
    @Annotation
    public ServerResponse deleteSite(Long id){
        return siteService.deleteSite(id);
    }

    @GetMapping
    @Annotation
    public ServerResponse findSite(VipVo vo){
        return siteService.findSite(vo.getId());
    }
}
