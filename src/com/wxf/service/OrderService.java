package com.wxf.service;

import com.wxf.entity.Orders;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;

public interface OrderService {
    /**
     * 提交订单
     * @param orders
     */
    void addOrder(Orders orders);

    /**
     * 根据用户id查询用户订单
     * @param uid
     * @return
     */
    List<Orders> findByUid(Integer uid);

    /**
     * 提交订单
     * @param uid
     * @param aid
     * @param bigDecimal
     */
    Orders insertOrder(Integer uid, int aid, BigDecimal bigDecimal) throws SQLException;

    /**
     * 查询订单
     * @param oid
     * @return
     */
    Orders findByOid(String oid);

    /**
     * 修改订单状态
     * @param oid
     */
    void changeStatus(String oid);

    /**
     * 查询所有订单
     * @return
     */
    List<Orders> findAll();

    /**
     * 发货
     * @param oid
     */
    void sendOrder(String oid);

    /**
     * 条件查询订单
     * @param uname
     * @param ostate
     * @return
     */
    List<Orders> searchOrder(String uname, String ostate) throws SQLException;

    /**
     * 付款
     * @param oid
     */
    void paySuccess(String oid) throws SQLException;

    /**
     * 删除item
     * @param oidList
     */
    void deleteItem(List<String> oidList) throws SQLException;

    /**
     * 根据用户id删除订单
     * @param id
     */
    void deleteByUid(int id) throws SQLException;
}
