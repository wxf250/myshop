package com.wxf.service.impl;

import com.wxf.dao.ProductDao;
import com.wxf.dao.TypeDao;
import com.wxf.dao.impl.ProductDaoImpl;
import com.wxf.dao.impl.TypeDaoImpl;
import com.wxf.entity.PageBean;
import com.wxf.entity.Product;
import com.wxf.entity.Type;
import com.wxf.service.ProductService;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ProductServiceImpl implements ProductService {
    private ProductDao productDao = new ProductDaoImpl();
    private TypeDao typeDao = new TypeDaoImpl();

    /**
     * 分页查询类别对应的商品
     *
     * @param tid
     * @param currentPage
     * @param pageSize
     * @return
     */
    @Override
    public PageBean<Product> findPage(Integer tid, Integer currentPage, Integer pageSize) {
        PageBean<Product> pageBean = new PageBean<>();
        // 设置当前页
        pageBean.setCurrentPage(currentPage);
        // 设置页面展示条数
        pageBean.setPageSize(pageSize);
        // 根据tid查询总记录数
        int totalCount = (int) productDao.findCount(tid);
        // 设置总记录数
        pageBean.setTotalCount(totalCount);
        // 获取总页数
        int totalPage = totalCount%pageSize==0 ? totalCount/pageSize : totalCount/pageSize+1;
        // 设置总页码
        pageBean.setTotalPage(totalPage);
        // 根据tid查询所对应的商品集合
        int start = (currentPage-1)*pageSize;
        List<Product> productsList = productDao.findByTid(tid,start,pageSize);
        pageBean.setList(productsList);
        // 设置查询
        return pageBean;
    }

    /**
     * 根据商品id查询商品信息
     *
     * @param pid
     */
    @Override
    public Product findByPid(int pid) {
        return productDao.findByPid(pid);
    }

    /**
     * 查询所有商品和所属类别
     *
     * @return
     */
    @Override
    public List<Product> findAll() {
        List<Product> productList = null;
        try {
             productList = productDao.findAll();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        List<Product> products = new ArrayList<>();
        for (Product product : productList) {
            Integer tid = product.getTid();
            try {
                Type type = typeDao.findByTid(tid);
                product.setType(type);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            products.add(product);
        }
        return products;
    }

    /**
     * 商品添加
     *
     * @param product
     */
    @Override
    public void addProduct(Product product) throws SQLException {
        productDao.addProduct(product);
    }

    /**
     * 根据pid删除商品信息
     *
     * @param pid
     */
    @Override
    public void deleteByPid(int pid) throws SQLException {
        productDao.deleteByPid(pid);
    }
}
