package com.fh.shop.api.common;

import java.io.Serializable;

public class DataTable implements Serializable  {
    private Integer draw;
    private  Integer start;
    private  Integer length;

    public Integer getDraw() {
        return draw;
    }

    public void setDraw(Integer draw) {
        this.draw = draw;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }
}
