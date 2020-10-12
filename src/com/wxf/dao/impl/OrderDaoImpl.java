package com.wxf.dao.impl;

import com.wxf.dao.OrderDao;
import com.wxf.dao.UserDao;
import com.wxf.entity.Item;
import com.wxf.entity.Orders;
import com.wxf.entity.User;
import com.wxf.utils.C3P0Utils;
import com.wxf.utils.Constants;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class OrderDaoImpl implements OrderDao {
    private UserDao userDao = new UserDaoImpl();
    QueryRunner queryRunner = new QueryRunner(C3P0Utils.getDataSource());
    /**
     * 提交订单
     *
     * @param orders
     */
    @Override
    public void addOrder(Orders orders) throws SQLException {
        String sql = "insert into orders value(?,?,?,?,?,?)";
        queryRunner.update(sql,orders.getOid(),orders.getUid(),orders.getAid(),orders.getOcount(),orders.getOtime(),orders.getOstate());
    }

    /**
     * 查询订单列表
     *
     * @param uid
     * @return
     */
    @Override
    public List<Orders> findByUid(Integer uid) throws SQLException {
        String sql = "select * from orders where uid = ?";
        return queryRunner.query(sql,new BeanListHandler<Orders>(Orders.class),uid);
    }

    /**
     * 添加订单项
     *
     * @param itemList
     */
    @Override
    public void insertItem(List<Item> itemList) throws SQLException {
        Object object[][] = new Object[itemList.size()][];
        String sql = "insert into item values(?,?,?,?,?)";
        for (int i = 0; i < itemList.size(); i++) {
            Item item = itemList.get(i);
            object[i] = new Object[]{null,item.getOid(),item.getPid(),item.getIcount(),item.getInum()};
        }
        queryRunner.batch(sql,object);
    }

    /**
     * 查询订单详情
     *
     * @param oid
     * @return
     */
    @Override
    public Orders findByOid(String oid) throws SQLException {
        String sql = "select * from orders where oid = ?";
        return queryRunner.query(sql,new BeanHandler<>(Orders.class),oid);
    }

    /**
     * 根据订单id查询订单项
     *
     * @param oid
     * @return
     */
    @Override
    public List<Item> findItems(String oid) throws SQLException {
        String sql = "select * from item where oid = ?";
        return queryRunner.query(sql,new BeanListHandler<Item>(Item.class),oid);
    }

    /**
     * 确认收货
     *
     * @param oid
     */
    @Override
    public void changeStatus(String oid) throws SQLException {
        String sql = "update orders set ostate = ? where oid = ?";
        queryRunner.update(sql, 3,oid);
    }

    /**
     * 查询所有订单
     *
     * @return
     */
    @Override
    public List<Orders> findAll() throws SQLException {
        String sql = "select * from orders";
        return queryRunner.query(sql,new BeanListHandler<>(Orders.class));
    }

    /**
     * 修改订单状态
     *
     * @param oid
     */
    @Override
    public void sendOrder(String oid) throws SQLException {
        String sql = "update orders set ostate = ? where oid = ?";
        queryRunner.update(sql, 2,oid);
    }

    /**
     * 根据条件查询订单
     *
     * @param uname
     * @param ostate
     * @return
     */
    @Override
    public List<Orders> searchOrder(String uname, String ostate) throws SQLException {
        String sql = "select * from orders where 1=1 ";
        StringBuilder sb = new StringBuilder(sql);
        List params = new ArrayList();
        if (uname!=null && uname.length()>0) {
            try {
                User user = userDao.findUid(uname);
                if (user!=null) {
                    sb.append(" and uid = ? ");
                    params.add(user.getUid());
                }
            } catch (SQLException e) {

            }
        }
        if (ostate!=null && ostate.length()>0) {
            sb.append(" and ostate = ? ");
            params.add(Integer.parseInt(ostate));
        }
        sql = sb.toString();
        return queryRunner.query(sql,new BeanListHandler<>(Orders.class),params.toArray());
    }

    @Override
    public void paySuccess(String oid) throws SQLException {
        String sql = "update orders set ostate = ? where oid = ?";
        queryRunner.update(sql, 1,oid);
    }

    @Override
    public void deleteItem(String oid) throws SQLException {
        String sql = "delete from item where oid = ?";
        queryRunner.update(sql,oid);
    }

    @Override
    public void deleteByUid(int id) throws SQLException {
        String sql = "delete from orders where uid = ?";
        queryRunner.update(sql,id);
    }

}
