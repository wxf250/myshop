package com.wxf.dao.impl;

import com.wxf.dao.AddressDao;
import com.wxf.entity.Address;
import com.wxf.utils.C3P0Utils;
import com.wxf.utils.Constants;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

public class AddressDaoImpl implements AddressDao {
    QueryRunner queryRunner = new QueryRunner(C3P0Utils.getDataSource());
    /**
     * 查询用户收获地址
     *
     * @param uid
     */
    @Override
    public List<Address> findByUid(Integer uid) throws SQLException {
        String sql = "select * from address where uid = ? order by astate desc";
        List<Address> addressList = queryRunner.query(sql,new BeanListHandler<Address>(Address.class),uid);
        return addressList;
    }

    /**
     * 添加地址
     *
     * @param address
     */
    @Override
    public void addAddress(Address address) throws SQLException {
        String sql = "insert into address values(?,?,?,?,?,?)";
        queryRunner.update(sql,null,address.getUid(),address.getAname(),address.getAphone(),address.getAdetail(),address.getAstate());
    }

    /**
     * 修改为默认地址
     *
     * @param aid
     */
    @Override
    public void addToDefault(int aid) throws SQLException {
        String sql = "update address set astate = ? where aid = ?";
        queryRunner.update(sql, Constants.DEFAULT_ADDRESS,aid);
    }

    /**
     * 修改其他地址为普通地址
     */
    @Override
    public void addToCommon(int uid) throws SQLException {
        String sql = "update address set astate = ? where uid = ?";
        queryRunner.update(sql,Constants.NO_DEFAULT_ADDRESS,uid);
    }

    /**
     * 修改收货地址信息
     *
     * @param address
     */
    @Override
    public void update(Address address) throws SQLException {
        String sql = "update address set aname = ?,aphone = ?,adetail = ? where aid = ?";
        queryRunner.update(sql,address.getAname(),address.getAphone(),address.getAdetail(),address.getAid());
    }

    /**
     * 删除收货地址信息
     *
     * @param aid
     */
    @Override
    public void delete(int aid) throws SQLException {
        String sql = "delete from address where aid = ?";
        queryRunner.update(sql,aid);
    }

    /**
     * 查询收货信息
     *
     * @param aid
     * @return
     */
    @Override
    public Address findByAid(int aid) throws SQLException {
        String sql = "select * from address where aid = ?";
        return queryRunner.query(sql,new BeanHandler<>(Address.class),aid);
    }
}
