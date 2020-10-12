package com.wxf.service.impl;

import com.wxf.dao.*;
import com.wxf.dao.impl.*;
import com.wxf.entity.*;
import com.wxf.service.CartService;
import com.wxf.service.OrderService;
import com.wxf.utils.Constants;
import com.wxf.utils.RandomUtils;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class OrderServiceImpl implements OrderService {
    private OrderDao orderDao = new OrderDaoImpl();
    private AddressDao addressDao = new AddressDaoImpl();
    private CartService cartService = new CartServiceImpl();
    private CartDao cartDao = new CartDaoImpl();
    private ProductDao productDao = new ProductDaoImpl();
    private UserDao userDao = new UserDaoImpl();
    /**
     * 提交订单
     *
     * @param orders
     */
    @Override
    public void addOrder(Orders orders) {
        try {
            orderDao.addOrder(orders);
            // 插入成功，清空购物车
            cartService.clearAllCart(orders.getUid());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据用户id查询用户订单
     *
     * @param uid
     * @return
     */
    @Override
    public List<Orders> findByUid(Integer uid) {
        List<Orders> ordersList = null;
        try {
            ordersList = orderDao.findByUid(uid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<Orders> orderList = new ArrayList<>();
        for (Orders orders : ordersList) {
            Integer aid = orders.getAid();
            try {
                Address address = addressDao.findByAid(aid);
                orders.setAddress(address);
                orderList.add(orders);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return orderList;
    }

    /**
     * 提交订单
     *
     * @param uid
     * @param aid
     * @param bigDecimal
     */
    @Override
    public Orders insertOrder(Integer uid, int aid, BigDecimal bigDecimal) throws SQLException {
        //实例化orders
        Orders orders = new Orders();
        orders.setUid(uid);
        List<Cart> cartList = cartService.findCartByUid(uid);
        orders.setCarts(cartList);
        Address address = addressDao.findByAid(aid);
        // 设置地址id
        orders.setAid(aid);
        orders.setAddress(address);
        // 设置订单总金额
        orders.setOcount(bigDecimal);

        String orderId = RandomUtils.createOrderId();
        // 设置订单编号
        orders.setOid(orderId);
        /*String time = RandomUtils.getTime();*/
        orders.setOtime(new Date());
        orders.setOstate(Constants.NO_PAY);
        // 插入订单到数据库
        orderDao.addOrder(orders);
        // 插入数据到item
        List<Item> itemList = new ArrayList<>();
        for (Cart cart : cartList) {
            Item item = new Item();
            item.setOid(orderId);
            item.setPid(cart.getPid());
            item.setIcount(cart.getCcount());
            item.setInum(cart.getCnum());
            itemList.add(item);
        }
        orderDao.insertItem(itemList);
        // 清空购物车
        cartDao.clearAllCart(uid);
        return orders;
    }

    /**
     * 查询订单
     *
     * @param oid
     * @return
     */
    @Override
    public Orders findByOid(String oid) {
        Orders orders = null;
        try {
             orders =orderDao.findByOid(oid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Integer aid = orders.getAid();
        try {
            Address address = addressDao.findByAid(aid);
            orders.setAddress(address);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<Item> itemList = null;
        try {
            itemList = orderDao.findItems(oid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<Item> items = new ArrayList<>();
        for (Item item : itemList) {
            Integer pid = item.getPid();
            Product product = productDao.findByPid(pid);
            item.setProduct(product);
            items.add(item);
        }
        orders.setItems(items);
        return orders;
    }

    /**
     * 修改订单状态
     *
     * @param oid
     */
    @Override
    public void changeStatus(String oid) {
        try {
            orderDao.changeStatus(oid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 查询所有订单
     *
     * @return
     */
    @Override
    public List<Orders> findAll() {
        List<Orders> ordersList = null;
        try {
             ordersList = orderDao.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<Orders> orders = new ArrayList<>();
        for (Orders order : ordersList) {
            Integer uid = order.getUid();
            try {
                User user = userDao.findByUid(uid);
                order.setUser(user);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            orders.add(order);
        }
        return orders;
    }

    /**
     * 发货
     *
     * @param oid
     */
    @Override
    public void sendOrder(String oid) {
        try {
            orderDao.sendOrder(oid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 条件查询订单
     *
     * @param uname
     * @param ostate
     * @return
     */
    @Override
    public List<Orders> searchOrder(String uname, String ostate)  {
        List<Orders> ordersList = null;
        try {
            ordersList = orderDao.searchOrder(uname,ostate);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<Orders> orders = new ArrayList<>();
        for (Orders order : ordersList) {
            Integer uid = order.getUid();
            User user = null;
            try {
                user = userDao.findByUid(uid);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            order.setUser(user);
            orders.add(order);
        }
        return orders;
    }

    /**
     * 付款
     *
     * @param oid
     */
    @Override
    public void paySuccess(String oid) throws SQLException {
        orderDao.paySuccess(oid);
    }

    /**
     * 删除item
     *
     * @param oidList
     */
    @Override
    public void deleteItem(List<String> oidList) throws SQLException {
        for (String oid : oidList) {
            orderDao.deleteItem(oid);
        }
    }

    /**
     * 根据用户id删除订单
     *
     * @param id
     */
    @Override
    public void deleteByUid(int id) throws SQLException {
        orderDao.deleteByUid(id);
    }
}
