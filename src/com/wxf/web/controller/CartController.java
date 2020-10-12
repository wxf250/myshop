package com.wxf.web.controller;

import com.wxf.entity.Cart;
import com.wxf.entity.User;
import com.wxf.service.CartService;
import com.wxf.service.impl.CartServiceImpl;
import com.wxf.utils.Constants;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@WebServlet("/cart")
public class CartController extends BaseServlet {
    private CartService cartService = new CartServiceImpl();
    // 添加购物车
    public String addCart(HttpServletRequest request, HttpServletResponse response) {
        // 判断用户是否登录
        User user = (User) request.getSession().getAttribute("loginUser");
        if (user==null) {
            // 用户未登录
            request.setAttribute("msg","添加购物车需先登录");
            return Constants.FORWARD+"/login.jsp";
        }
        // 商品id和用户id
        Integer uid = user.getUid();
        String pidStr = request.getParameter("pid");
        int pid = Integer.parseInt(pidStr);
        cartService.createCart(uid,pid);
        return Constants.FORWARD+"/cartSuccess.jsp";
    }

    /**
     * 查看购物车
     * @param request
     * @param response
     * @return
     */
    public String gotoCart(HttpServletRequest request, HttpServletResponse response) {
        // 获取参数uid
        // 判断用户是否登录
        User user = (User) request.getSession().getAttribute("loginUser");
        if (user==null) {
            // 用户未登录
            request.setAttribute("msg","添加购物车需先登录");
            return Constants.FORWARD+"/login.jsp";
        }
        Integer uid = user.getUid();
        // 调用业务层，通过uid查询用购物车
        List<Cart> cartsList =  cartService.findCartByUid(uid);
        request.setAttribute("carts",cartsList);
        return Constants.FORWARD+"/cart.jsp";
    }

    /**
     * 更新购物车
     * @param request
     * @param response
     * @return
     */
    public String updateCart(HttpServletRequest request, HttpServletResponse response) {
        // 获取参数cid,cnum,ppric
        String cidStr = request.getParameter("cid"); // 购物车id
        String cnumStr = request.getParameter("cnum"); // 修改后的商品数量
        String priceStr = request.getParameter("price"); // 商品价格、
        int cid = Integer.parseInt(cidStr);
        int cnum = Integer.parseInt(cnumStr);
        // 修改购物车数量以及对应的总计
        cartService.updateCart(cid,cnum,priceStr);
        // 响应
        return Constants.FORWARD+"/cart?method=gotoCart";
    }

    /**
     * 购物车删除
     * @param request
     * @param response
     * @return
     */
    public String clearCart(HttpServletRequest request, HttpServletResponse response) {
        // 获取参数pid
        String cidStr = request.getParameter("cid");
        int cid = Integer.parseInt(cidStr);
        // 调用业务层，根据pid删除购物车
        cartService.clearCart(cid);
        // 响应
        return Constants.FORWARD+"/cart?method=gotoCart";

    }

    /**
     * 清空购物车
     * @param request
     * @param response
     * @return
     */
    public String clearAllCart(HttpServletRequest request, HttpServletResponse response) {
        // 获取参数uid
        String uidStr = request.getParameter("uid");
        int uid = Integer.parseInt(uidStr);
        // 调用业务层，根据uid删除购物车
        cartService.clearAllCart(uid);
        // 响应
        return Constants.FORWARD+"/cart?method=gotoCart";

    }



}
