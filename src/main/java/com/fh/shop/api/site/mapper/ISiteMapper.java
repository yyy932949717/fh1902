package com.fh.shop.api.site.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.fh.shop.api.site.po.Site;

import java.util.List;

public interface ISiteMapper extends BaseMapper<Site> {

    List<Site> findSite(Long vipId);
}
