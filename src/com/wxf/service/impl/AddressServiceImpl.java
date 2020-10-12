package com.wxf.service.impl;

import com.wxf.dao.AddressDao;
import com.wxf.dao.impl.AddressDaoImpl;
import com.wxf.entity.Address;
import com.wxf.service.AddressService;

import java.sql.SQLException;
import java.util.List;

public class AddressServiceImpl implements AddressService {
    private AddressDao addressDao = new AddressDaoImpl();
    /**
     * 查询用户所有收获地址
     *
     * @param uid
     * @return
     */
    @Override
    public List<Address> findByUid(Integer uid) {
        List<Address> addressList = null;
        try {
            addressList = addressDao.findByUid(uid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return addressList;
    }

    /**
     * 添加地址
     *
     * @param address
     */
    @Override
    public void addAddress(Address address) {
        try {
            addressDao.addAddress(address);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改地址为默认地址
     *
     * @param aid
     */
    @Override
    public void addToDefault(int aid) {
        try {
            addressDao.addToDefault(aid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改其他地址为普通地址
     */
    @Override
    public void addToCommon(int uid) {
        try {
            addressDao.addToCommon(uid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 修改收货信息
     *
     * @param address
     */
    @Override
    public void update(Address address) {
        try {
            addressDao.update(address);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 删除收货地址
     *
     * @param aid
     */
    @Override
    public void delete(int aid) {
        try {
            addressDao.delete(aid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    /**
     * 根据aid查询收货地址
     *
     * @param aid
     * @return
     */
    @Override
    public Address findById(int aid) {
        Address address = null;
        try {
            address = addressDao.findByAid(aid);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return address;
    }
}
