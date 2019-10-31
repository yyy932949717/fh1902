package com.fh.shop.api.shop.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.api.shop.po.ShopPo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface IShopMapper extends BaseMapper<ShopPo> {

    Long updateSale(@Param("shopId") Long shopId, @Param("shopTotalCount") Long shopTotalCount);

    List<ShopPo> queryShopSales();

}
