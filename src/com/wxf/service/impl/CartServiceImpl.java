package com.wxf.service.impl;

import com.wxf.dao.CartDao;
import com.wxf.dao.ProductDao;
import com.wxf.dao.impl.CartDaoImpl;
import com.wxf.dao.impl.ProductDaoImpl;
import com.wxf.entity.Cart;
import com.wxf.entity.Product;
import com.wxf.service.CartService;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class CartServiceImpl implements CartService {
    private  CartDao cartDao = new CartDaoImpl();
    private ProductDao productDao = new ProductDaoImpl();
    /**
     * 创建购物车
     *
     * @param uid
     * @param pid
     */
    @Override
    public void createCart(Integer uid, int pid) {
        // 判断购物车是否存在
        Cart cart = cartDao.hasCart(uid,pid);
        if (cart != null) {
            // 已经添加过了购物车，更新购物车
            // 修改数量加1
            cart.setCnum(cart.getCnum()+1);
            // 更新购物车
            cartDao.updateCart(cart);

        } else {
            // 不存在，添加购物车
            ProductDao productDao = new ProductDaoImpl();
            Product product = productDao.findByPid(pid);
            cart = new Cart();
            cart.setPid(pid);
            cart.setCnum(1);
            cart.setUid(uid);
            cart.setCcount(product.getPprice());
            cart.setProduct(product);
            productDao.createCart(cart);
        }

    }

    /**
     * 通过当前登录用户id查询用户购物车
     *
     * @param uid
     * @return
     */
    @Override
    public List<Cart> findCartByUid(Integer uid) {
        List<Cart> cartList = new ArrayList();
        List<Cart> carts = cartDao.findCartByUid(uid);
        for (Cart cart : carts) {
            Integer pid = cart.getPid();
            Product product = productDao.findByPid(pid);
            cart.setProduct(product);
            cartList.add(cart);
        }
        return cartList;
    }

    /**
     * 删除购物车
     *
     * @param cid
     */
    @Override
    public void clearCart(int cid) {
        cartDao.clearCart(cid);
    }

    /**
     * 购物车更新
     *
     * @param cid
     * @param cnum
     * @param priceStr
     */
    @Override
    public void updateCart(int cid, int cnum, String priceStr) {
        BigDecimal price = new BigDecimal(priceStr);
        BigDecimal num = new BigDecimal(cnum);
        BigDecimal ccount = price.multiply(num);
        Cart cart = new Cart();
        cart.setCid(cid);
        cart.setCnum(cnum);
        cart.setCcount(ccount);
        cartDao.updateCart(cart);

    }

    /**
     * 清空购物车
     *
     * @param uid
     */
    @Override
    public void clearAllCart(int uid) {
        cartDao.clearAllCart(uid);
    }
}
