package com.fh.shop.api.area.po;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

@TableName("t_area")
public class Area implements Serializable {
    private Long id;
    private String name;
    private Long pid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }
}
