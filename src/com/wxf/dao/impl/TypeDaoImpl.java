package com.wxf.dao.impl;

import com.wxf.dao.TypeDao;
import com.wxf.entity.Type;
import com.wxf.utils.C3P0Utils;
import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import java.sql.SQLException;
import java.util.List;

public class TypeDaoImpl implements TypeDao {
    QueryRunner queryRunner = new QueryRunner(C3P0Utils.getDataSource());
    /**
     * 查询商品类别
     * @return
     */
    @Override
    public List<Type> selectAll() {
        String sql = "select * from type";
        List<Type> typesList = null;
        try {
           typesList =  queryRunner.query(sql,new BeanListHandler<Type>(Type.class));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return typesList;
    }

    /**
     * \
     * 根据tid查询类别
     *
     * @param tid
     * @return
     */
    @Override
    public Type findByTid(Integer tid) throws SQLException {
        String sql = "select * from type where tid = ?";
        return queryRunner.query(sql,new BeanHandler<>(Type.class),tid);
    }
}
