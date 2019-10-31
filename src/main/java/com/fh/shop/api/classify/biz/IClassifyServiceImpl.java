package com.fh.shop.api.classify.biz;

import com.alibaba.fastjson.JSONObject;
import com.fh.shop.api.classify.mapper.IClassifyMapper;
import com.fh.shop.api.classify.po.Classify;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("classifyService")
public class IClassifyServiceImpl implements IClassifyService {
    @Autowired
    private IClassifyMapper classifyMapper;

    @Override
    public ServerResponse list() {
        String classifyjson = RedisUtil.get("classifyList");
        if (StringUtils.isNotEmpty(classifyjson)){
            List<Classify> classifyList = JSONObject.parseArray(classifyjson, Classify.class);
         return   ServerResponse.success(classifyList);
        }
        List<Classify> classifies = classifyMapper.selectList(null);
        String toJSONString = JSONObject.toJSONString(classifies);
        RedisUtil.set("classifyList",toJSONString);
        return ServerResponse.success(classifies);
    }
}
