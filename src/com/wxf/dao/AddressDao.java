package com.wxf.dao;

import com.wxf.entity.Address;

import java.sql.SQLException;
import java.util.List;

public interface AddressDao {
    /**
     * 查询用户收获地址
     * @param uid
     */
    List<Address> findByUid(Integer uid) throws SQLException;

    /**
     * 添加地址
     * @param address
     */
    void addAddress(Address address) throws SQLException;

    /**
     * 修改为默认地址
     * @param aid
     */
    void addToDefault(int aid) throws SQLException;

    /**
     * 修改其他地址为普通地址
     */
    void addToCommon(int uid) throws SQLException;

    /**
     * 修改收货地址信息
     * @param address
     */
    void update(Address address) throws SQLException;

    /**
     * 删除收货地址信息
     * @param aid
     */
    void delete(int aid) throws SQLException;

    /**
     * 查询收货信息
     * @param aid
     * @return
     */
    Address findByAid(int aid) throws SQLException;
}
