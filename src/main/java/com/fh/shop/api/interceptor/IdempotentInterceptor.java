package com.fh.shop.api.interceptor;

import com.fh.shop.api.annotation.Idempotent;
import com.fh.shop.api.common.ResponseEnum;
import com.fh.shop.api.exception.Myexception;
import com.fh.shop.api.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;

public class IdempotentInterceptor extends HandlerInterceptorAdapter {
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {//

        HandlerMethod handlerMethod = (HandlerMethod) handler;
        Method method = handlerMethod.getMethod();
        if (!method.isAnnotationPresent(Idempotent.class)){
            return  true;
        }

        String idem_token = request.getHeader("idem-token");
        if (StringUtils.isEmpty(idem_token)){
            throw  new Myexception(ResponseEnum.HEADER_IS_NULL);
        }

        boolean exists = RedisUtil.exists(idem_token);
        if(!exists){
            throw new Myexception(ResponseEnum.TOKEN_IS_NULL);
        }

        Long del = RedisUtil.del(idem_token);
        if(del==0){
            throw new Myexception(ResponseEnum.CFCZ);
        }


        return true;
    }

}
