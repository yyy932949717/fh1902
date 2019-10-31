package com.fh.shop.api.shop.biz;

import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.shop.po.ShopPo;

import java.util.List;

public interface IShopService {
    ServerResponse list();

    List<ShopPo> queryShopSales();

}
