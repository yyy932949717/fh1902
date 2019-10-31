package com.fh.shop.api.cart.biz;

import com.fh.shop.api.common.ServerResponse;

public interface ICartService {
    ServerResponse addCartShop(Long id, Long count, Long vipId);

    ServerResponse queryCart(Long id);

    ServerResponse deleteCartShop(Long shopId, Long id);
}
