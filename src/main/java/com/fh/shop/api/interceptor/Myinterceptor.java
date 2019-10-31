package com.fh.shop.api.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.fh.shop.api.annotation.Annotation;
import com.fh.shop.api.common.ResponseEnum;
import com.fh.shop.api.common.SytemConst;
import com.fh.shop.api.exception.Myexception;
import com.fh.shop.api.util.KeyUtil;
import com.fh.shop.api.util.Md5Util;
import com.fh.shop.api.util.RedisUtil;
import com.fh.shop.api.vip.vo.VipVo;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.Method;
import java.util.Base64;

public class Myinterceptor extends HandlerInterceptorAdapter {
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_ORIGIN,"http://www.yyy.com:8083");  //解决跨域
        response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_METHODS,"GET,POST,PUT,DELETE");//解决跨域请求方式
        response.addHeader(HttpHeaders.ACCESS_CONTROL_ALLOW_HEADERS,"x-auth,content-type,idem-token");    //解决接受自定义头信息
            //拦截options请求
            String requestMethod = request.getMethod();
            if (requestMethod.equalsIgnoreCase("options")){
            return  false;
        }


        HandlerMethod handlerMethed = (HandlerMethod) handler; //根据handler 强转获取 handlerMethed
        Method method = handlerMethed.getMethod();              //根据handlerMethed获取所有方法（内部）
        if(!method.isAnnotationPresent(Annotation.class)){      //判断方法上面是否有自定义注解
            return  true;
        }

        //判断头信息是否为空
        String header = request.getHeader("x-auth");
        if (StringUtils.isEmpty(header)){
            throw new Myexception(ResponseEnum.HEADER_IS_NULL);
        }
        //判断头信息长度是否正确
        String[] split = header.split("\\.");
        if(split.length!=2){
            throw new Myexception(ResponseEnum.HEADER_IS_ERROR);
        }
        //验签
        String jsonBase64 = split[0];
        String md5 = Md5Util.qianMing(jsonBase64, SytemConst.MI_YAO);
        String qianMing0 = Base64.getEncoder().encodeToString(md5.getBytes());
        String qianMing1 = split[1];
        if(!qianMing0.equals(qianMing1)){
            throw new Myexception(ResponseEnum.HEADER_IS_ERROR2);
        }
        //判断用户redis中是否过期
        byte[] decode = Base64.getDecoder().decode(jsonBase64);
        String voJson=new String(decode);
        VipVo vo = JSONObject.parseObject(voJson, VipVo.class);
        String name = vo.getName();
        String uuid = vo.getUuid();
        boolean exists = RedisUtil.exists(KeyUtil.buidVipKey(name, uuid));
        if (!exists){
            throw new Myexception(ResponseEnum.VIPKEY_IS_ESISTS);
        }

        //给request作用域赋值
        request.setAttribute(SytemConst.LOGIN_VIP,vo);
        //重设redis的头信息存活时间 进行续命
        RedisUtil.expire(KeyUtil.buidVipKey(name,uuid),SytemConst.VIP_REDIS);
        return true;
    }

}
