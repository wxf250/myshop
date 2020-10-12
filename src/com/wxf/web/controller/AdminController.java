package com.wxf.web.controller;

import com.google.gson.Gson;
import com.wxf.entity.Orders;
import com.wxf.entity.Product;
import com.wxf.entity.User;
import com.wxf.service.CartService;
import com.wxf.service.OrderService;
import com.wxf.service.ProductService;
import com.wxf.service.UserService;
import com.wxf.service.impl.CartServiceImpl;
import com.wxf.service.impl.OrderServiceImpl;
import com.wxf.service.impl.ProductServiceImpl;
import com.wxf.service.impl.UserServiceImpl;
import com.wxf.utils.Constants;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;


@WebServlet("/admin/admin")
public class AdminController extends BaseServlet {
    private UserService userService = new UserServiceImpl();
    private ProductService productService = new ProductServiceImpl();
    private OrderService orderService = new OrderServiceImpl();
    private CartService cartService = new CartServiceImpl();

    /**
     * 管理员登录
     * @param request
     * @param response
     * @return
     */
    public String adminLogin(HttpServletRequest request, HttpServletResponse response){
        // 获取请求参数uname,upassword
        String uname = request.getParameter("uname");
        String upassword = request.getParameter("upassword");
        User user = userService.login(uname, upassword);
        String msg = null;
        if (user==null) {
            msg = "用户名或密码错误";
        }
        else if (user!=null && user.getUrole().equals(Constants.USER_MEMBER)) {
            msg = "当前用户不是管理员,请稍后重试";
        }
        else if (user!=null && user.getUrole().equals(Constants.USER_MANAGER)) {
            request.getSession().setAttribute("admin",user);
            return null;
        }
        else {
            msg = "服务器异常";
        }
        Gson gson = new Gson();
        String json = gson.toJson(msg);
        return json;
    }

    /**
     * 管理员销注
     * @param request
     * @param response
     * @return
     */
    public String logOut(HttpServletRequest request, HttpServletResponse response){
        request.getSession().setAttribute("admin",null);
        return Constants.REDIRECT+"/admin/admin.jsp";
    }

    /**
     * 会员管理
     * @param request
     * @param response
     * @return
     */
    public String getUserList(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        // 查询所有会员
        List<User> userList = userService.findAll();
        // 序列化为json
        Gson gson = new Gson();
        String users = gson.toJson(userList);
        // 返回json
        return users;
    }

    /**
     * 查询无效会员
     * @param request
     * @param response
     * @return
     * @throws SQLException
     */
    public String getInvalidUserList(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        // 查询所有无效会员
        List<User> userList = userService.findInvalid();
        // 序列化为json
        Gson gson = new Gson();
        String users = gson.toJson(userList);
        // 返回json
        return users;
    }

    /**
     * 根据条件查询用户
     * @param request
     * @param response
     * @return
     */
    public String searchUser(HttpServletRequest request, HttpServletResponse response){
        // 获取请求参数
        String uname = request.getParameter("username");
        String usex = request.getParameter("gender");
        // 查询用户是否存在
        List<User> userList = userService.findUser(uname,usex);
        if (userList==null) {
            return null;
        }
        Gson gson = new Gson();
        String json = gson.toJson(userList);
        return json;
    }

    /**
     * 查看商品分类
     * @param request
     * @param response
     * @return
     */
    public String getGoodsType(HttpServletRequest request, HttpServletResponse response){
        List<Product> productList = productService.findAll();
        request.setAttribute("productTypeList",productList);
        return Constants.FORWARD+"/admin/showGoodsType.jsp";
    }

    /**
     * 查看所有商品
     * @param request
     * @param response
     * @return
     */
    public String getGoodsList(HttpServletRequest request, HttpServletResponse response){
        List<Product> productList = productService.findAll();
        request.setAttribute("productTypeList",productList);
        return Constants.FORWARD+"/admin/showGoods.jsp";
    }
    /**
     * 删除商品信息
     * @param request
     * @param response
     * @return
     * @throws SQLException
     */
    public String deleteProduct(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        // 获取请求参数pid
        String pidStr = request.getParameter("pid");
        int pid = Integer.parseInt(pidStr);
        // 调用业务层，删除商品信息
        productService.deleteByPid(pid);
        return Constants.FORWARD+"/admin/admin?method=getGoodsList";
    }

    /**
     * 修改商品信息
     * @param request
     * @param response
     * @return
     * @throws SQLException
     */
    public String changeProduct(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        // 获取请求参数pid
        String pidStr = request.getParameter("pid");
        int pid = Integer.parseInt(pidStr);
        // 调用业务层，删除商品信息
        Product product = productService.findByPid(pid);
        request.setAttribute("product",product);
        return Constants.FORWARD+"/admin/changeGood.jsp";
    }

    /**
     * 查看所有订单
     * @param request
     * @param response
     * @return
     */
    public String getAllOrder(HttpServletRequest request, HttpServletResponse response){
        List<Orders> ordersList = orderService.findAll();
        request.setAttribute("orderList",ordersList);
        return Constants.FORWARD+"/admin/showAllOrder.jsp";
    }

    /**
     * 发货
     * @param request
     * @param response
     * @return
     */
    public String sendOrder(HttpServletRequest request, HttpServletResponse response){
        // 获取请求参数oid
        String oid = request.getParameter("oid");
        // 修改订单状态
        orderService.sendOrder(oid);
        return Constants.FORWARD+"/admin/admin?method=getAllOrder";
    }

    /**
     * 条件查询订单
     * @param request
     * @param response
     * @return
     */
    public String searchOrder(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        // 获取请求参数uname,status
        String uname = request.getParameter("username");
        String ostate = request.getParameter("status");
        List<Orders> ordersList = orderService.searchOrder(uname,ostate);
        request.setAttribute("orderList",ordersList);
        return Constants.FORWARD+"/admin/showAllOrder.jsp";
    }

    /**
     * 删除用户
     * @param request
     * @param response
     * @return
     * @throws SQLException
     */
    public String deleteUser(HttpServletRequest request, HttpServletResponse response) throws SQLException {
        // 获取请求参数id,uid
        // 需要删除的用户id
        String idStr = request.getParameter("id");
        int id = Integer.parseInt(idStr);
        // 获取登录用户id
        User admin = (User) request.getSession().getAttribute("admin");
        Integer uid = admin.getUid();
        // 获取删除用户角色
        User user = userService.findByUid(id);
        String msg = null;
        if (admin.getUrole()>user.getUrole()) {
            // 权限够，
            // 判断用户是否还有订单未完成
            List<Orders> orders = orderService.findByUid(id);
            boolean isDelete = true;
            for (Orders order : orders) {
                if (order.getOstate()==1||order.getOstate()==2) {
                    // 用户有订单没有完成，不能删除
                    msg = "用户尚有订单没有完成，不能删除";
                    isDelete = false;
                }
            }
            // 用户没有订单未完成
            if (isDelete==true) {
                // 删除用户所有信息
                // 批量删除Item
                List<String> oidList = new ArrayList<>();
                for (Orders order : orders) {
                    oidList.add(order.getOid());
                }
                orderService.deleteItem(oidList);
                // 删除Order订单
                orderService.deleteByUid(id);
                // 删除用户购物车
                cartService.clearAllCart(id);
                // 删除用户
                userService.deleteByUid(id);
                return null;
            }

        } else {
            // 权限不够，不能删除
            msg = "您的权限不够！不能删除当前用户";
        }
        Gson gson = new Gson();
        String json = gson.toJson(msg);
        return json;
    }




}
