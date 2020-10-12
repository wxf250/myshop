package com.wxf.entity;

import java.io.Serializable;
import java.math.BigDecimal;

public class Cart implements Serializable {
    private static final Long serialVersionUID=1L;
    private Integer cid; //购物车的唯一标识
    private Integer uid; //用户实体的主键属性
    private Integer pid; //商品的唯一主键
    private BigDecimal ccount; //购物车小计
    private Integer cnum; //购物车商品数量
    private Product product; // 商品


    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public Integer getCid() {
        return cid;
    }

    public void setCid(Integer cid) {
        this.cid = cid;
    }

    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getPid() {
        return pid;
    }

    public void setPid(Integer pid) {
        this.pid = pid;
    }

    public BigDecimal getCcount() {
        return ccount;
    }

    /*public BigDecimal getCcount() {
            BigDecimal pprice = product.getPprice();
            BigDecimal bigDecimal = new BigDecimal(cnum);
            return pprice.multiply(bigDecimal);
        }
    */
    public void setCcount(BigDecimal ccount) {
        this.ccount = ccount;
    }

    public Integer getCnum() {
        return cnum;
    }

    public void setCnum(Integer cnum) {
        this.cnum = cnum;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "cid=" + cid +
                ", uid=" + uid +
                ", pid=" + pid +
                ", ccount=" + ccount +
                ", cnum=" + cnum +
                ", product=" + product +
                '}';
    }


}
