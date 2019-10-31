package com.fh.shop.api.payLog.biz;

import com.fh.shop.api.common.ServerResponse;

import java.io.Serializable;

public interface IPayLogService {
    ServerResponse createNative(Long id);

    Serializable queryWxzf(Long id);
}
