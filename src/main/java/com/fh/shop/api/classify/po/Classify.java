package com.fh.shop.api.classify.po;

import com.baomidou.mybatisplus.annotation.TableName;

import java.io.Serializable;

@TableName("t_classify")
public class Classify implements Serializable {
    private Long id;
    private String classIfyName;
    private Long pid;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getClassIfyName() {
        return classIfyName;
    }

    public void setClassIfyName(String classIfyName) {
        this.classIfyName = classIfyName;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }
}
