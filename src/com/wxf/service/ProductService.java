package com.wxf.service;

import com.wxf.entity.PageBean;
import com.wxf.entity.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductService {
    /**
     * 分页查询类别对应的商品
     * @param tid
     * @param currentPage
     * @param pageSize
     * @return
     */
    PageBean<Product> findPage(Integer tid,Integer currentPage,Integer pageSize);

    /**
     * 根据商品id查询商品信息
     */
    Product findByPid(int pid);

    /**
     * 查询所有商品和所属类别
     * @return
     */
    List<Product> findAll();

    /**
     * 商品添加
     * @param product
     */
    void addProduct(Product product) throws SQLException;

    /**
     * 根据pid删除商品信息
     * @param pid
     */
    void deleteByPid(int pid) throws SQLException;
}
