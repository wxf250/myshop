package com.wxf.service;

import com.wxf.entity.Cart;

import java.util.List;

public interface CartService {
    /**
     * 创建购物车
     * @param uid
     * @param pid
     */
    void createCart(Integer uid, int pid);

    /**
     * 通过当前登录用户id查询用户购物车
     * @param uid
     * @return
     */
    List<Cart> findCartByUid(Integer uid);

    /**
     * 删除购物车
     * @param cid
     */
    void clearCart(int cid);

    /**
     * 购物车更新
     * @param cid
     * @param cnum
     * @param priceStr
     */
    void updateCart(int cid, int cnum, String priceStr);

    /**
     * 清空购物车
     * @param uid
     */
    void clearAllCart(int uid);
}
