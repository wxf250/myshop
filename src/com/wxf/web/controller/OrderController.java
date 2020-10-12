package com.wxf.web.controller;

import com.google.gson.Gson;
import com.wxf.entity.*;
import com.wxf.service.AddressService;
import com.wxf.service.CartService;
import com.wxf.service.OrderService;
import com.wxf.service.impl.AddressServiceImpl;
import com.wxf.service.impl.CartServiceImpl;
import com.wxf.service.impl.OrderServiceImpl;
import com.wxf.utils.Constants;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

@WebServlet("/order")
public class OrderController extends BaseServlet {
    private CartService cartService = new CartServiceImpl();
    private AddressService addressService = new AddressServiceImpl();
    private OrderService orderService = new OrderServiceImpl();
    public String show(HttpServletRequest request, HttpServletResponse response) {
        // 获取用户uid
        User user = (User) request.getSession().getAttribute("loginUser");
        if (user==null) {
            // 用户未登录
            request.setAttribute("msg","添加购物车需先登录");
            return Constants.FORWARD+"/login.jsp";
        }
        Integer uid = user.getUid();
        List<Cart> cartList = cartService.findCartByUid(uid);
        List<Address> addressList = addressService.findByUid(uid);
        request.setAttribute("cartList",cartList);
        request.setAttribute("addList",addressList);
        return Constants.FORWARD+"/order.jsp";
    }

    /**
     * 提交订单
     * @param request
     * @param response
     * @return
     */
    public String add(HttpServletRequest request, HttpServletResponse response) {
        // 获取用户uid
        User user = (User) request.getSession().getAttribute("loginUser");
        if (user==null) {
            // 用户未登录
            request.setAttribute("msg","订单提交需先登录");
            return Constants.FORWARD+"/login.jsp";
        }
        Integer uid = user.getUid();
        // 获取aid
        String aidStr = request.getParameter("aid");
        int aid = Integer.parseInt(aidStr);
        String ocount = request.getParameter("ocount");
        BigDecimal bigDecimal = new BigDecimal(ocount);
        try {
            Orders orders = orderService.insertOrder(uid,aid,bigDecimal);
            request.setAttribute("order",orders);
        } catch (SQLException e) {
            e.printStackTrace();
        }
      return Constants.FORWARD+"/orderSuccess.jsp";
    }

    /**
     * 查看订单详情
     * @param request
     * @param response
     * @return
     */
    public String showDetail(HttpServletRequest request, HttpServletResponse response) {
        // 获取用户uid
        User user = (User) request.getSession().getAttribute("loginUser");
        if (user==null) {
            // 用户未登录
            request.setAttribute("msg","查看需先登录");
            return Constants.FORWARD+"/login.jsp";
        }
        String oid = request.getParameter("oid");
        Orders orders = orderService.findByOid(oid);
        request.setAttribute("order",orders);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        String dateTime = simpleDateFormat.format(orders.getOtime());
        request.setAttribute("dateTime",dateTime);
        return Constants.FORWARD+"/orderDetail.jsp";
    }

    /**
     * 查询订单列表
     * @param request
     * @param response
     * @return
     */
    public String getOrderList(HttpServletRequest request, HttpServletResponse response) {
        // 获取用户uid
        User user = (User) request.getSession().getAttribute("loginUser");
        if (user==null) {
            // 用户未登录
            request.setAttribute("msg","用户未登录");
            return Constants.FORWARD+"/login.jsp";
        }
        // 调用业务层，查询订单列表
        List<Orders> ordersList = orderService.findByUid(user.getUid());
        request.setAttribute("orderList",ordersList);
        return Constants.FORWARD+"/orderList.jsp";
    }

    /**
     * 确认收货
     * @param request
     * @param response
     * @return
     */
    public String changeStatus(HttpServletRequest request, HttpServletResponse response) {
        // 获取用户uid
        User user = (User) request.getSession().getAttribute("loginUser");
        if (user==null) {
            // 用户未登录
            request.setAttribute("msg","用户未登录");
            return Constants.FORWARD+"/login.jsp";
        }
        // 改变订单状态
        String oid = request.getParameter("oid");
        orderService.changeStatus(oid);
        return Constants.FORWARD+"order?method=getOrderList";
    }

    /**
     * 付款成功
     * @param request
     * @param response
     * @return
     */
    public String paySuccess(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        // 改变订单状态
        String oid = request.getParameter("oid");
        String result = request.getParameter("result");
        Gson gson = new Gson();
        WeiXin weiXin = gson.fromJson(result, WeiXin.class);
        String result_code = weiXin.getResult().getResult_code();
        if (result_code!=null && result_code.equals("SUCCESS")) {
            orderService.paySuccess(oid);
            return Constants.FORWARD+"order?method=getOrderList";
        } else {
            request.getSession().setAttribute("msg",oid+"订单支付失败");
            return Constants.FORWARD+"/message.jsp";
        }

    }

}
