package com.fh.shop.api.order.biz;

import com.fh.shop.api.common.OrderParam;
import com.fh.shop.api.common.ServerResponse;

public interface IOrderService {
    ServerResponse addOrder(OrderParam orderParam, Long id);
}
