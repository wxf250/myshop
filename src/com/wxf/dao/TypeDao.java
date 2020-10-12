package com.wxf.dao;

import com.wxf.entity.Type;

import java.sql.SQLException;
import java.util.List;

public interface TypeDao {
    /**
     * 查询商品类别
     * @return
     */
    public List<Type> selectAll();

    /**\
     * 根据tid查询类别
     * @param tid
     * @return
     */
    Type findByTid(Integer tid) throws SQLException;
}
