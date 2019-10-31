package com.fh.shop.api.site.biz;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.site.mapper.ISiteMapper;
import com.fh.shop.api.site.po.Site;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service("siteService")
public class ISiteServiceImpl implements ISiteService {
    @Autowired
    private ISiteMapper siteMapper;

    @Override
    public ServerResponse addSite(Site site, Long id) {
        site.setVipId(id);
        siteMapper.insert(site);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse deleteSite(Long id) {
        siteMapper.deleteById(id);
        return ServerResponse.success();
    }

    @Override
    public ServerResponse findSite(Long vipId) {
      List<Site>  sites= siteMapper.findSite(vipId);
        return ServerResponse.success(sites);
    }
}

