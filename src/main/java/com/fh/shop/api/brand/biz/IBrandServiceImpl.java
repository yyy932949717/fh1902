package com.fh.shop.api.brand.biz;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.api.brand.mapper.IBrandMapper;
import com.fh.shop.api.brand.po.Brand;
import com.fh.shop.api.brand.vo.BrandVo;
import com.fh.shop.api.common.DataTable;
import com.fh.shop.api.common.DataTableResult;
import com.fh.shop.api.common.ResponseEnum;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.search.BrandSearch;
import com.fh.shop.api.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("brandService")
public class IBrandServiceImpl implements IBrandService {
    @Autowired
    private IBrandMapper brandMapper;

    @Override
    public ServerResponse list() {
        //从redis中对应key的value
        String redisBrand = RedisUtil.get("apiBrand");
        if(StringUtils.isNotEmpty(redisBrand)){//判断是否为空
            //不为空就转换为list 并响应到前台
            List<BrandVo> brandVo = JSONObject.parseArray(redisBrand, BrandVo.class);
            ServerResponse.success(brandVo);
        }
       // 为空就去数据库查

        QueryWrapper queryWrapper=new QueryWrapper(); //查询条件
        queryWrapper.eq("ishot",1);
        queryWrapper.orderByDesc("sort");
        List list = brandMapper.selectList(queryWrapper);
        String brandJson = JSONObject.toJSONString(list);//转为json格式字符串
        RedisUtil.set("apiBrand",brandJson);//存到redis服务中
        return ServerResponse.success(list);
    }

    @Override
    public ServerResponse add(Brand brand) {
        brandMapper.insert(brand);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse delete(Integer id) {
        brandMapper.deleteById(id);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse queryById(Integer id) {
        Brand brand = brandMapper.selectById(id);
        if (brand==null){
            ServerResponse.error(ResponseEnum.BRAND_IS_NULL);
        }
        return ServerResponse.success(brand);
    }

    @Override
    public ServerResponse update(Brand brand) {
        brandMapper.updateById(brand);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse deletePath(String ids) {
        if (ids==null){
            return ServerResponse.error(ResponseEnum.IDS_IS_NULL);
        }
        String[] split = ids.split(",");
        List<Integer> arr=new ArrayList();
        for (String s : split) {
            arr.add(Integer.valueOf(s));
        }
        brandMapper.deleteBatchIds(arr);

        return ServerResponse.success();
    }

    @Override
    public ServerResponse dataTable(BrandSearch brandSearch) {
       Long count= brandMapper.queryCount();
       List<Brand> list=brandMapper.queryDataTalbeList(brandSearch);
        DataTableResult dataTableResult=new DataTableResult(brandSearch.getDraw(),count,count,list);
        return ServerResponse.success(dataTableResult);
    }

    @Override
    public ServerResponse dataTablelist(BrandSearch brandSearch) {
        QueryWrapper queryWrapper=new QueryWrapper();


        return null;
    }
}
