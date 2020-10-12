package com.wxf.dao;

import com.wxf.entity.Cart;
import com.wxf.entity.Product;

import java.sql.SQLException;
import java.util.List;

public interface ProductDao {

    /**
     * 查询总记录条数
     * @return
     */
    long findCount(Integer tid);

    /**
     * 分页查询商品
     * @param tid
     * @return
     */
    List<Product> findByTid(Integer tid, int start, Integer pageSize);

    /**
     * 根据id查询商品详细信息
     * @param pid
     * @return
     */
    Product findByPid(int pid);

    /**
     * 创建购物车
     * @param cart
     */
    void createCart(Cart cart);

    /**
     * 查询所有商品
     * @return
     */
    List<Product> findAll() throws SQLException;

    /**
     * 添加商品
     * @param product
     */
    void addProduct(Product product) throws SQLException;

    void deleteByPid(int pid) throws SQLException;
}
