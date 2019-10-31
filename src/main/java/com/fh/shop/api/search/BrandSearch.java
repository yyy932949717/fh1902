package com.fh.shop.api.search;

import com.fh.shop.api.common.DataTable;

import java.io.Serializable;

public class BrandSearch extends DataTable implements Serializable {
    private  String brandName;

    public String getBrandName() {
        return brandName;
    }

    public void setBrandName(String brandName) {
        this.brandName = brandName;
    }
}
