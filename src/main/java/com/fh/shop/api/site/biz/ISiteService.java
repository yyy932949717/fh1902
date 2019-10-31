package com.fh.shop.api.site.biz;

import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.site.po.Site;

public interface ISiteService {
    ServerResponse addSite(Site site, Long id);

    ServerResponse deleteSite(Long id);

    ServerResponse findSite(Long id);
}
