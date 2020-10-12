package com.wxf.service;

import com.wxf.entity.Type;

import java.util.List;

public interface TypeService {
    /**
     * 查询所有商品类别
     * @return
     */
    public List<Type> findAll();
}
