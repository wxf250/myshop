package com.wxf.dao;

import com.wxf.entity.Cart;

import java.math.BigDecimal;
import java.util.List;

public interface CartDao {
    /**
     * 商品是否添加过购物车
     * @param uid
     * @param pid
     * @return
     */
    Cart hasCart(Integer uid, int pid);

    /**
     * 更新购物车方法
     * @param cart
     */
    void updateCart(Cart cart);

    /**
     * 查询购物车方法
     * @param uid
     * @return
     */
    List<Cart> findCartByUid(Integer uid);

    /**
     * 删除购物车方法
     * @param cid
     */
    void clearCart(int cid);

    /**
     * 清空购物车方法
     * @param uid
     */
    void clearAllCart(int uid);
}
