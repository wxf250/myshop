package com.wxf.entity;

import java.io.Serializable;

public class Address implements Serializable {
    private static final Long serialVersionUID=1L;
    private Integer aid; //主键
    private Integer uid; //用户实体的主键
    private String aname; //地址的收件人
    private String aphone; //收件人电话
    private String adetail; //收货人详细地址
    private Integer astate; //是否是默认地址 0 不是 1是默认地址


    public Integer getAid() {
        return aid;
    }

    public void setAid(Integer aid) {
        this.aid = aid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getAname() {
        return aname;
    }

    public void setAname(String aname) {
        this.aname = aname;
    }

    public String getAphone() {
        return aphone;
    }

    public void setAphone(String aphone) {
        this.aphone = aphone;
    }

    public String getAdetail() {
        return adetail;
    }

    public void setAdetail(String adetail) {
        this.adetail = adetail;
    }

    public Integer getAstate() {
        return astate;
    }

    public void setAstate(Integer astate) {
        this.astate = astate;
    }

    @Override
    public String toString() {
        return "Address{" +
                "aid=" + aid +
                ", uid=" + uid +
                ", aname='" + aname + '\'' +
                ", aphone='" + aphone + '\'' +
                ", adetail='" + adetail + '\'' +
                ", astate=" + astate +
                '}';
    }
}
