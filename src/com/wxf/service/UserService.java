package com.wxf.service;

import com.wxf.entity.User;

import java.sql.SQLException;
import java.util.List;

/**
 * 用户模块对应的业务成接口
 */
public interface UserService {
    /**
     * 检测用户名是否存在
     * @param uname
     * @return
     */
    boolean checkUsername(String uname);

    /**
     * 用户注册
     * @param user
     * @return
     */
    boolean register(User user);

    /**
     * 用户激活
     * @param ucode
     * @return
     */
    int activeUser(String ucode);

    /**
     * 用户登录
     */
    User login(String uname, String upassword);

    /**
     * 查询所有用户
     * @return
     */
    List<User> findAll() throws SQLException;

    /**
     * 查询用户
     * @param uname
     * @param usex
     * @return
     */
    List<User> findUser(String uname, String usex);


    /**
     * 删除用户
     * @param id
     */
    void deleteByUid(int id) throws SQLException;

    /**
     * 通过用户id查询用户
     * @param id
     * @return
     */
    User findByUid(int id) throws SQLException;

    /**
     * 查询无效会员
     * @return
     */
    List<User> findInvalid() throws SQLException;
}
