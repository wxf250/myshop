package com.wxf.dao.impl;

import com.wxf.dao.UserDao;
import com.wxf.entity.User;
import com.wxf.utils.C3P0Utils;
import com.wxf.utils.Constants;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户模块持久层接口实现
 */
public class UserDaoImpl implements UserDao {
    // 创建QueryRunner
    QueryRunner queryRunner = new QueryRunner(C3P0Utils.getDataSource());
    /**
     * 检测用户名是否存在
     *
     * @param uname
     * @return
     */
    @Override
    public User checkUsername(String uname) {
        User user = null;
        String sql = "select * from user where uname = ?";
        try {
            user = queryRunner.query(sql,new BeanHandler<User>(User.class),uname);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return user;
    }

    /**
     * 用户注册
     *
     * @param user
     */
    @Override
    public int register(User user) {
        String sql = "insert into user values(?,?,?,?,?,?,?,?)";
        int updateRows = 0;
        try {
            updateRows = queryRunner.update(sql, null, user.getUname(), user.getUpassword(), user.getUemail(), user.getUsex(), user.getUstatus(), user.getUcode(), user.getUrole());
        } catch (Exception e) {
            e.printStackTrace();
        }
       return updateRows;
    }

    /**
     * 通过激活码查询用户是否存在
     *
     * @param ucode
     * @return
     */
    @Override
    public User selectByCode(String ucode) {
        User user = null;
        String sql = "select * from user where ucode = ?";
        try {
            user = queryRunner.query(sql,new BeanHandler<User>(User.class),ucode);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }



    /**
     * 改变用户激活状态
     *
     * @param uid
     * @return
     */
    @Override
    public int updateUserActive(Integer uid){
        String sql = "update user set ustatus = ? where uid = ?";
        int i = 0;
        try {
            i = queryRunner.update(sql,Constants.USER_ACTIVE,uid);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }

    /**
     * 查询所有用户
     *
     * @return
     */
    @Override
    public List<User> findAll() throws SQLException {
        String sql = "select * from user";
        return queryRunner.query(sql,new BeanListHandler<User>(User.class));
    }

    /**
     * 条件查询用户
     *
     * @param uname
     * @param usex
     * @return
     */
    @Override
    public List<User> findUser(String uname, String usex) throws SQLException {
        String sql = "select * from user where 1=1  ";
        StringBuilder sb = new StringBuilder(sql);
        List params = new ArrayList();
        if (uname!=null&& uname.length()>0 && !"null".equals(uname)) {
            sb.append(" and uname like ? ");
            params.add("%"+uname+"%");
        }
        if (usex!=null && usex.length()>0 &&!"null".equals(uname)) {
            sb.append(" and usex = ? ");
            params.add(usex);
        }
        sql = sb.toString();
        return queryRunner.query(sql,new BeanListHandler<>(User.class),params.toArray());
    }

    /**
     * 查询用户
     *
     * @param uid
     * @return
     */
    @Override
    public User findByUid(Integer uid) throws SQLException {
        String sql = "select * from user where uid =?";
        return queryRunner.query(sql,new BeanHandler<>(User.class),uid);
    }

    @Override
    public User findUid(String uname) throws SQLException {
        String sql = "select * from user where uname = ?";
        return queryRunner.query(sql,new BeanHandler<>(User.class),uname);
    }

    @Override
    public void deleteByUid(int id) throws SQLException {
        String sql = "delete from user where uid = ?";
        queryRunner.update(sql,id);
    }

}
