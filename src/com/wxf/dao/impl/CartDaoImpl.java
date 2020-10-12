package com.wxf.dao.impl;

import com.wxf.dao.CartDao;
import com.wxf.entity.Cart;
import com.wxf.entity.Product;
import com.wxf.utils.C3P0Utils;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;
import org.apache.commons.dbutils.handlers.MapHandler;


import java.util.List;
import java.util.Map;

public class CartDaoImpl implements CartDao {
    QueryRunner queryRunner = new QueryRunner(C3P0Utils.getDataSource());
    /**
     * 商品是否添加过购物车
     *
     * @param uid
     * @param pid
     * @return
     */
    @Override
    public Cart hasCart(Integer uid, int pid) {
        String sql = "select product.*,cart.* from cart join product on product.pid = cart.pid where cart.uid = ? and cart.pid = ?";
        try {
            Map<String, Object> query = queryRunner.query(sql, new MapHandler(), uid, pid);
            if (query==null) {
                return null;
            }
            Cart cart = new Cart();
            Product product = new Product();
            BeanUtils.populate(cart,query);
            BeanUtils.populate(product,query);
            cart.setProduct(product);
            return cart;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 更新购物车方法
     *
     * @param cart
     */
    @Override
    public void updateCart(Cart cart) {
        String sql = "update cart set cnum = ?,ccount = ? where cid = ?";
        try {
            queryRunner.update(sql,cart.getCnum(),cart.getCcount(),cart.getCid());
        }catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * 查询购物车方法
     *
     * @param uid
     * @return
     */
    @Override
    public List<Cart> findCartByUid(Integer uid) {
        List<Cart> carts = null;
        String sql = "select * from cart where uid = ?";
        try {
            carts = queryRunner.query(sql,new BeanListHandler<>(Cart.class),uid);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return carts;
    }

    /**
     * 删除购物车方法
     *
     * @param cid
     */
    @Override
    public void clearCart(int cid) {
        String sql = "delete from cart where cid = ?";
        try {
            queryRunner.update(sql,cid);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 清空购物车方法
     *
     * @param uid
     */
    @Override
    public void clearAllCart(int uid) {
        String sql = "delete from cart where uid = ?";
        try {
            queryRunner.update(sql,uid);
        }catch (Exception e) {
            e.printStackTrace();
        }
    }
}
