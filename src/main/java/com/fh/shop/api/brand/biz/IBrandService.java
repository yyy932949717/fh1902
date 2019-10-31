package com.fh.shop.api.brand.biz;

import com.fh.shop.api.brand.po.Brand;
import com.fh.shop.api.common.DataTable;
import com.fh.shop.api.common.ServerResponse;
import com.fh.shop.api.search.BrandSearch;

public interface IBrandService {
    ServerResponse list();

    ServerResponse add(Brand brand);

    ServerResponse delete(Integer id);

    ServerResponse queryById(Integer id);

    ServerResponse update(Brand brand);

    ServerResponse deletePath(String ids);

    ServerResponse dataTable(BrandSearch brandSearch);

    ServerResponse dataTablelist(BrandSearch brandSearch);
}
