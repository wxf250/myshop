package com.wxf.web.controller;

import com.wxf.entity.PageBean;
import com.wxf.entity.Product;
import com.wxf.service.ProductService;
import com.wxf.service.impl.ProductServiceImpl;
import com.wxf.utils.Constants;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/product")
public class ProductController extends BaseServlet {
    private ProductService productService = new ProductServiceImpl();
    public String show(HttpServletRequest request, HttpServletResponse response){
        // 获取参数商品类别tid和当前页码
        String tidStr = request.getParameter("tid");
        String currentPageStr = request.getParameter("currentPage");
        int currentPage = 1;
        int pageSize = 6;
        if (currentPageStr!=null && ! currentPageStr.trim().equals("")) {
            currentPage = Integer.parseInt(currentPageStr);
        }
        int tid = Integer.parseInt(tidStr);
        // 分页封装
        PageBean<Product> pageBean = productService.findPage(tid, currentPage, pageSize);
        request.setAttribute("pageBean",pageBean);
        return Constants.FORWARD+"/goodsList.jsp";
    }

    /**
     * 商品详情
     * @param request
     * @param response
     * @return
     */
    public String showDetail(HttpServletRequest request, HttpServletResponse response){
        // 获取请求参数
        String pidStr = request.getParameter("pid");
        int pid = Integer.parseInt(pidStr);
        // 调用业务完成商品查询
        ProductService productService = new ProductServiceImpl();
        Product product = productService.findByPid(pid);
        // 将product存入request域中
        request.setAttribute("product",product);
        return Constants.FORWARD+"/goodsDetail.jsp";
    }

}
