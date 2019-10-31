package com.fh.shop.api.brand.controller;

import com.fh.shop.api.brand.biz.IBrandService;
import com.fh.shop.api.brand.po.Brand;
import com.fh.shop.api.common.DataTable;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.search.BrandSearch;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RequestMapping("/brands")
@RestController
@CrossOrigin("*")
public class ResultBrandController {
    @Resource(name = "brandService")
    private IBrandService brandService;

//    @RequestMapping(method = RequestMethod.GET)
//    public ServerResponse list(){
//        return brandService.list();
//    }

    @RequestMapping(method = RequestMethod.POST)
    public ServerResponse add(Brand brand){
        return brandService.add(brand);
    }

    @RequestMapping(value = "queryById",method = RequestMethod.GET)
    public ServerResponse queryById(Integer id){
        return brandService.queryById(id);
    }

    @RequestMapping(value = "delete",method = RequestMethod.DELETE)
    public ServerResponse delete(Integer id){
        return brandService.delete(id);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ServerResponse update(@RequestBody Brand brand){
        return brandService.update(brand);
    }

    @RequestMapping(method = RequestMethod.DELETE)
    public ServerResponse deletePath(String ids){
        return brandService.deletePath(ids);
    }

    @RequestMapping(method = RequestMethod.GET)
    public ServerResponse  dataTable(BrandSearch brandSearch){
        return brandService.dataTable(brandSearch);
    }

//    @RequestMapping(method = RequestMethod.GET)
//    public ServerResponse dataTablelist(BrandSearch brandSearch){
//        return brandService.dataTablelist(brandSearch);
//    }

}
