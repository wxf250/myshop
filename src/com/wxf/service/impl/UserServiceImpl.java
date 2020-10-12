package com.wxf.service.impl;

import com.wxf.dao.UserDao;
import com.wxf.dao.impl.UserDaoImpl;
import com.wxf.entity.User;
import com.wxf.service.UserService;
import com.wxf.utils.Constants;
import com.wxf.utils.EmailUtils;
import com.wxf.utils.MD5Utils;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


/**
 * 业务层接口实现
 */
public class UserServiceImpl implements UserService {

    private UserDao userDao = new UserDaoImpl();

    /**
     * 检测用户名是否存在
     *
     * @param uName
     * @return
     */
    @Override
    public boolean checkUsername(String uName) {
        User user = userDao.checkUsername(uName);
        if (user!=null) {
            return true;
        }
        return false;
    }

    /**
     * 用户注册
     *
     * @param user
     * @return
     */
    @Override
    public boolean register(User user) {
        int register = userDao.register(user);
        System.out.println(register);
        // 为1，用户注册成功，否则注册失败
        if (register==1) {
            EmailUtils.sendEmail(user);
            return true;
        } else {
            return false;
        }
    }

    /**
     * 用户激活
     *
     * @param ucode
     * @return
     */
    @Override
    public int activeUser(String ucode) {
        // 根据激活码查找用户
        UserDao userDao = new UserDaoImpl();
        User user = userDao.selectByCode(ucode);
        // 判断/修改激活状态
        if (user==null) {
            return Constants.ACTIVE_FAIL; // 激活失败
        }
        if (user.getUstatus().equals(Constants.USER_ACTIVE)){
            return Constants.IS_ACTIVE; // 用户已激活
        }
        // 用户激活,影响行数
        int rows = userDao.updateUserActive(user.getUid());
        if (rows>0) {
            return Constants.ACTIVE_SUCCESS; //激活成功
        }
        return Constants.ACTIVE_FAIL;
    }

    /**
     * 用户登录
     *
     * @param uname
     * @param upassword
     */
    @Override
    public User login(String uname, String upassword) {
        // 密码用MD5加密
        String md5Password = MD5Utils.md5(upassword);
        // 根据用户名查询用户是否存在
        User user = userDao.checkUsername(uname);
        // 判断用户密码跟输入密码是否相同
        if (user!=null && user.getUpassword().equals(md5Password)) {
            return user;
        }
        return null;
    }

    /**
     * 查询所有用户
     *
     * @return
     */
    @Override
    public List<User> findAll() throws SQLException {
        List<User> userList = userDao.findAll();
        List<User> users = new ArrayList<>();
        for (User user : userList) {
            if (user.getUstatus().equals(Constants.USER_ACTIVE)) {
                users.add(user);
            }
        }
        return users;
    }

    /**
     * 查询用户
     *
     * @param uname
     * @param usex
     * @return
     */
    @Override
    public List<User> findUser(String uname, String usex) {
        try {
            return userDao.findUser(uname,usex);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 删除用户
     *
     * @param id
     */
    @Override
    public void deleteByUid(int id) throws SQLException {
        userDao.deleteByUid(id);
    }

    /**
     * 通过用户id查询用户
     *
     * @param id
     * @return
     */
    @Override
    public User findByUid(int id) throws SQLException {
        return userDao.findByUid(id);
    }

    /**
     * 查询无效会员
     *
     * @return
     */
    @Override
    public List<User> findInvalid() throws SQLException {
        List<User> userList = userDao.findAll();
        List<User> users = new ArrayList<>();
        for (User user : userList) {
            if (user.getUstatus().equals(Constants.USER_NOT_ACTIVE)) {
                users.add(user);
            }
        }
        return users;
    }

}
