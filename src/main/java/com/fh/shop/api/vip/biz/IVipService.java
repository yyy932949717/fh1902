package com.fh.shop.api.vip.biz;

import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.vip.po.Vip;

public interface IVipService {
    ServerResponse addVip(Vip vip);

    ServerResponse getCode(String phone);

    ServerResponse verify(String name);

    ServerResponse verifyPhone(String phone);

    ServerResponse verifyEmail(String email);

    ServerResponse login(Vip vip);

    ServerResponse phoneLogin(Vip vip);
}
