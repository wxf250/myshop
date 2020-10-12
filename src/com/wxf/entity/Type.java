package com.wxf.entity;

import java.io.Serializable;

public class Type implements Serializable {
    private static Long serialVersionUID = 1L;
    private Integer tid; //'类别的主键id'
    private String tname; //'类别的名称'
    private String tinfo; //'类别的描述'

    public Integer getTid() {
        return tid;
    }

    public void setTid(Integer tid) {
        this.tid = tid;
    }

    public String getTname() {
        return tname;
    }

    public void setTname(String tname) {
        this.tname = tname;
    }

    public String getTinfo() {
        return tinfo;
    }

    public void setTinfo(String tinfo) {
        this.tinfo = tinfo;
    }

    @Override
    public String toString() {
        return "Type{" +
                "tid=" + tid +
                ", tname='" + tname + '\'' +
                ", tinfo='" + tinfo + '\'' +
                '}';
    }
}
