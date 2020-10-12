package com.wxf.entity;

import java.io.Serializable;

public class User implements Serializable {
    private static final Long serialVersionUID = 1L;
    private Integer uid;  //主键
    private String uname; //用户账号
    private String upassword; //用户密码
    private String uemail; //用户的邮箱！用于激活使用！
    private String usex; //用户性别
    private Integer ustatus; //用户的激活状态 0 未激活 1 激活
    private String ucode; //邮件激活码
    private Integer urole; //用户 0 管理员 1 超级管理员2

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public String getUname() {
        return uname;
    }

    public void setUname(String uname) {
        this.uname = uname;
    }

    public String getUpassword() {
        return upassword;
    }

    public void setUpassword(String upassword) {
        this.upassword = upassword;
    }

    public String getUemail() {
        return uemail;
    }

    public void setUemail(String uemail) {
        this.uemail = uemail;
    }

    public String getUsex() {
        return usex;
    }

    public void setUsex(String usex) {
        this.usex = usex;
    }

    public Integer getUstatus() {
        return ustatus;
    }

    public void setUstatus(Integer ustatus) {
        this.ustatus = ustatus;
    }

    public String getUcode() {
        return ucode;
    }

    public void setUcode(String ucode) {
        this.ucode = ucode;
    }

    public Integer getUrole() {
        return urole;
    }

    public void setUrole(Integer urole) {
        this.urole = urole;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", uname='" + uname + '\'' +
                ", upassword='" + upassword + '\'' +
                ", uemail='" + uemail + '\'' +
                ", usex='" + usex + '\'' +
                ", ustatus=" + ustatus +
                ", ucode='" + ucode + '\'' +
                ", urole=" + urole +
                '}';
    }
}
