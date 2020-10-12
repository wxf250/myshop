package com.wxf.service;

import com.wxf.entity.Address;

import java.util.List;

public interface AddressService {
    /**
     * 查询用户所有收获地址
     * @param uid
     * @return
     */
    List<Address> findByUid(Integer uid);

    /**
     * 添加地址
     */
    void addAddress(Address address);

    /**
     * 修改地址为默认地址
     * @param aid
     */
    void addToDefault(int aid);

    /**
     * 修改其他地址为普通地址
     */
    void addToCommon(int uid);

    /**
     * 修改收货信息
     * @param address
     */
    void update(Address address);

    /**
     * 删除收货地址
     * @param aid
     */
    void delete(int aid);

    /**
     * 根据aid查询收货地址
     * @param aid
     * @return
     */
    Address findById(int aid);
}
