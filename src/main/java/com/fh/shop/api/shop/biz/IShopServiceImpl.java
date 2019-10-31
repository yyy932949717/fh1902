package com.fh.shop.api.shop.biz;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.shop.mapper.IShopMapper;
import com.fh.shop.api.shop.po.ShopPo;
import com.fh.shop.api.shop.vo.ShopVo;
import com.fh.shop.api.util.DateUtil;
import com.fh.shop.api.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("shopService")
public class IShopServiceImpl implements IShopService {
    @Autowired
    private IShopMapper shopMapper;
    @Override
    public ServerResponse list() {
        String apiShop = RedisUtil.get("apiShop");

        if (StringUtils.isNotEmpty(apiShop)){
            List<ShopVo> voList = JSONObject.parseArray(apiShop, ShopVo.class);
            return ServerResponse.success(voList);
        }

        QueryWrapper queryWrapper=new QueryWrapper();
        queryWrapper.eq("isUp",1);
        queryWrapper.orderByDesc("id");

        List<ShopPo> poList = shopMapper.selectList(queryWrapper);
        List<ShopVo> voList = getShopVo(poList);
        String voJson = JSONObject.toJSONString(voList);


        RedisUtil.setEx("apiShop",voJson,60);
        return ServerResponse.success(voList);
    }

    @Override
    public List<ShopPo> queryShopSales() {

        return shopMapper.queryShopSales();
    }

    public List<ShopVo> getShopVo(List<ShopPo> poList) {
        List<ShopVo> voList=new ArrayList<ShopVo>();
        for (ShopPo po : poList) {
            ShopVo vo=new ShopVo();
            vo.setId(po.getId());
            vo.setShopName(po.getShopName());
            vo.setPrice(po.getPrice().toString());
            vo.setImgUrl(po.getImgUrl());
            vo.setTime(DateUtil.fromData(po.getTime(),DateUtil.YYYY_MM_DD_HH_MM_SS));
            vo.setIshot(po.getIshot());
            vo.setIsUp(po.getIsUp());
            vo.setSales(po.getSales());
            voList.add(vo);
        }
        return voList;
    }
}
