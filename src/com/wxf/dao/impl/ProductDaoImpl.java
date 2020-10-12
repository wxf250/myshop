package com.wxf.dao.impl;

import com.wxf.dao.ProductDao;
import com.wxf.entity.Cart;
import com.wxf.entity.Product;
import com.wxf.utils.C3P0Utils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.ScalarHandler;

import java.sql.SQLException;
import java.util.List;

public class ProductDaoImpl implements ProductDao {
    QueryRunner queryRunner = new QueryRunner(C3P0Utils.getDataSource());
    /**
     * 查询总记录条数
     *
     * @return
     */
    @Override
    public long findCount(Integer tid) {
        String sql = "select count(*) from product where tid = ?";
        long count = 0;
        try {
            count = (long) queryRunner.query(sql,new ScalarHandler(), tid);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return count;
    }

    /**
     * 分页查询商品
     *
     * @param tid
     * @param start
     * @param pageSize
     * @return
     */
    @Override
    public List<Product> findByTid(Integer tid, int start, Integer pageSize) {
        String sql = "select * from product where tid = ? limit ?, ? ";
        List<Product> productList = null;
        try {
            productList = queryRunner.query(sql,new BeanListHandler<>(Product.class),tid,start,pageSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return productList;
    }

    /**
     * 根据id查询商品详细信息
     *
     * @param pid
     * @return
     */
    @Override
    public Product findByPid(int pid) {
        Product product = null;
        String sql = "select * from product where pid = ?";
        try {
            product = queryRunner.query(sql,new BeanHandler<>(Product.class),pid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return product;
    }

    /**
     * 创建购物车
     *
     * @param cart
     */
    @Override
    public void createCart(Cart cart) {
        String sql = "insert into cart values(?,?,?,?,?)";
        try {
            queryRunner.update(sql,null,cart.getUid(),cart.getPid(),cart.getCcount(),cart.getCnum());
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 查询所有商品
     *
     * @return
     */
    @Override
    public List<Product> findAll() throws SQLException {
        String sql = "select * from product";
        return queryRunner.query(sql,new BeanListHandler<>(Product.class));
    }

    /**
     * 添加商品
     *
     * @param product
     */
    @Override
    public void addProduct(Product product) throws SQLException {
        String sql = "insert into product values(?,?,?,?,?,?,?,?)";
        queryRunner.update(sql,null,product.getTid(),product.getPname(),product.getPtime(),product.getPimage(),product.getPprice(),product.getPstate(),product.getPinfo());
    }

    @Override
    public void deleteByPid(int pid) throws SQLException {
        String sql = "delete from product where pid = ?";
        queryRunner.update(sql,pid);
    }

}
