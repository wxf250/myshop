package com.wxf.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class Item implements Serializable {
    private static Long serialVersionUID = 1L;
    private Integer iid; //订单项的唯一标识
    private String oid; //订单编号是字符串类型但是也是唯一标识
    private Integer pid; //商品的唯一主键
    private BigDecimal icount; //订单项的小计
    private Integer inum; //订单项的数量
    private Product product;

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getIid() {
        return iid;
    }

    public void setIid(Integer iid) {
        this.iid = iid;
    }

    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public BigDecimal getIcount() {
        return icount;
    }

    public void setIcount(BigDecimal icount) {
        this.icount = icount;
    }

    public Integer getInum() {
        return inum;
    }

    public void setInum(Integer inum) {
        this.inum = inum;
    }

    @Override
    public String toString() {
        return "Item{" +
                "iid=" + iid +
                ", oid='" + oid + '\'' +
                ", pid=" + pid +
                ", icount=" + icount +
                ", inum=" + inum +
                ", product=" + product +
                '}';
    }
}
