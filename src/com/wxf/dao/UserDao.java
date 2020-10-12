package com.wxf.dao;

import com.wxf.entity.User;

import java.sql.SQLException;
import java.util.List;

/**
 * 用户模块持久层接口
 */
public interface UserDao {

    /**
     * 检测用户名是否存在
     * @param uname
     * @return
     */
    User checkUsername(String uname);

    /**
     * 用户注册
     */
    int register(User user);

    /**
     * 通过激活码查询用户是否存在
     * @param ucode
     * @return
     */
    User selectByCode(String ucode);

    /**
     * 改变用户激活状态
     * @return
     */
    int updateUserActive(Integer uid);

    /**
     * 查询所有用户
     * @return
     */
    List<User> findAll() throws SQLException;

    /**
     * 条件查询用户
     * @param uname
     * @param usex
     * @return
     */
    List<User> findUser(String uname, String usex) throws SQLException;

    /**
     * 查询用户
     * @param uid
     * @return
     */
    User findByUid(Integer uid) throws SQLException;

    User findUid(String uname) throws SQLException;

    void deleteByUid(int id) throws SQLException;
}
