package com.wxf.service.impl;

import com.wxf.dao.TypeDao;
import com.wxf.dao.impl.TypeDaoImpl;
import com.wxf.entity.Type;
import com.wxf.service.TypeService;

import java.util.List;

public class TypeServiceImpl implements TypeService {
    private TypeDao typeDao = new TypeDaoImpl();
    /**
     * 查询所有商品类别
     *
     * @return
     */
    @Override
    public List<Type> findAll() {
        return typeDao.selectAll();
    }
}
