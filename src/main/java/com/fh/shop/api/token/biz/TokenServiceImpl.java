package com.fh.shop.api.token.biz;

import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.util.RedisUtil;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service("tokenService")
public class TokenServiceImpl implements TokenService {


    @Override
    public ServerResponse gertToken() {
        String token = UUID.randomUUID().toString();
        RedisUtil.set(token,token);
        return ServerResponse.success(token);
    }
}
