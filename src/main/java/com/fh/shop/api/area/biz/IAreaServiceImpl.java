package com.fh.shop.api.area.biz;

import com.alibaba.fastjson.JSONObject;
import com.fh.shop.api.area.mapper.IAreaMapper;
import com.fh.shop.api.area.po.Area;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("areaService")
public class IAreaServiceImpl implements IAreaService {

    @Autowired
    private IAreaMapper areaMapper;

    @Override
    public ServerResponse areaList(Long id) {
        String areaList = RedisUtil.get("areaList");
        if(!StringUtils.isEmpty(areaList)){
            List<Area> areas = JSONObject.parseArray(areaList, Area.class);
            List<Area> pidList = getPidList(areas, id);
            return  ServerResponse.success(pidList);
        }
        List<Area> areas = areaMapper.selectList(null);
        String jsonString = JSONObject.toJSONString(areas);
        RedisUtil.set("areaList",jsonString);
        List<Area> pidList = getPidList(areas,id);
        return ServerResponse.success(pidList);
    }

    private static List<Area> getPidList(List<Area> areas,Long id){
            List<Area> list =new ArrayList<>();
        for (Area area : areas) {
            if(area.getPid()==id){
                list.add(area);
            }
        }
        return list;
    }
}
