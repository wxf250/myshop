package com.wxf.dao;

import com.wxf.entity.Item;
import com.wxf.entity.Orders;

import java.sql.SQLException;
import java.util.List;

public interface OrderDao {
    /**
     * 提交订单
     * @param orders
     */
    void addOrder(Orders orders) throws SQLException;

    /**
     * 查询订单列表
     * @param uid
     * @return
     */
    List<Orders> findByUid(Integer uid) throws SQLException;

    /**
     * 添加订单项
     * @param itemList
     */
    void insertItem(List<Item> itemList) throws SQLException;

    /**
     * 查询订单详情
     * @param oid
     * @return
     */
    Orders findByOid(String oid) throws SQLException;

    /**
     * 根据订单id查询订单项
     * @param oid
     * @return
     */
    List<Item> findItems(String oid) throws SQLException;

    /**
     * 确认收货
     * @param oid
     */
    void changeStatus(String oid) throws SQLException;

    /**
     * 查询所有订单
     * @return
     */
    List<Orders> findAll() throws SQLException;

    /**
     * 修改订单状态
     */
    void sendOrder(String oid) throws SQLException;


    /**
     * 根据条件查询订单
     * @param uname
     * @param ostate
     * @return
     */
    List<Orders> searchOrder(String uname, String ostate) throws SQLException;

    void paySuccess(String oid) throws SQLException;

    void deleteItem(String oid) throws SQLException;

    void deleteByUid(int id) throws SQLException;
}
