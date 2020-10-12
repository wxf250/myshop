package com.wxf.web.controller;

import com.wxf.entity.User;
import com.wxf.service.UserService;
import com.wxf.service.impl.UserServiceImpl;
import com.wxf.utils.Base64Utils;
import com.wxf.utils.Constants;
import com.wxf.utils.MD5Utils;
import com.wxf.utils.RandomUtils;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

@WebServlet("/user")
public class UserController extends BaseServlet {
    // 引入业务层
    private UserService userService = new UserServiceImpl();

    /**
     * 检测用户名是否存在
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String check(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取请求参数
        String username = request.getParameter("uname");
        // 调用业务成，完成逻辑操作
        // 判断用户是否存在
        boolean flag = userService.checkUsername(username);
        // flag为true，用户名存在，用户名不可用，响应1；
        // flag为false，用户名不存在，用户名可用，响应0；
        if (flag) {
            return Constants.HAS_USER;
        } else {
            return Constants.NOT_HAS_USER;
        }
    }

    /**
     * 用户注册
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String register(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取请求参数
        Map<String, String[]> parameterMap = request.getParameterMap();
        User user = new User();
        // 封装
        try {
            BeanUtils.populate(user,parameterMap);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        // 完善用户信息
        // 已有信息：用户名、密码、邮箱、性别
        // 没有信息：用户激活状态、邮件激活码、用户角色、
        // 设置用户激活状态 0 未激活 1, 已激活
        user.setUstatus(Constants.USER_NOT_ACTIVE);
        // 设置用户邮件激活码
        user.setUcode(RandomUtils.createActive());
        // 设置用户角色 用户 0、管理员 1
        user.setUrole(Constants.USER_MEMBER);
        // 密码使用MD5加密存入
        user.setUpassword(MD5Utils.md5(user.getUpassword()));
        // 调用业务逻辑层完成用户注册
        boolean flag = userService.register(user);
        // 判断用户是否注册成功
        if (flag) {
            return Constants.FORWARD+"/registerSuccess.jsp";
        } else {
            request.setAttribute("registerMsg","注册失败");
            return Constants.FORWARD+"/register.jsp";
        }

    }

    /**
     * 用户邮件激活
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String active(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取用户激活码
        String c = request.getParameter("c");
        String ucode = Base64Utils.decode(c);
        // 调用激活业务逻辑
        UserService userService = new UserServiceImpl();
        // 0 未激活 1 已激活 2
        int i = userService.activeUser(ucode);
        if (i==Constants.ACTIVE_FAIL) {
            request.setAttribute("msg","激活失败");
        }
        else if (i==Constants.IS_ACTIVE) {
            request.setAttribute("msg","用户已激活");
        } else {
            request.setAttribute("msg","激活成功，请登录");
        }
        // 响应
        return Constants.FORWARD+"/message.jsp";
    }

    /**
     * 用户登录
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String login(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 获取请求参数，用户名 密码 验证码 自动登录
        String uname = request.getParameter("uname");
        String upassword = request.getParameter("upassword");
        String vcode = request.getParameter("vcode");
        String auto = request.getParameter("auto");
        // 获取session域中的验证码
        String code = (String) request.getSession().getAttribute("code");
        request.getSession().removeAttribute("code");
        // 判断验证码是否正确
        if (vcode.equalsIgnoreCase(code)) {
            // 判断用户名密码是否正确
            User user = userService.login(uname,upassword);
            // 用户不为空且用户已激活
            if (user!=null && user.getUstatus()==Constants.USER_ACTIVE) {
                // 判断用户是否勾选“两周以内免登录”
                if (auto!=null) {
                    // 用户勾选
                    String content = uname+Constants.FLAG+upassword;
                    String encode = Base64Utils.encode(content);
                    Cookie cookie = new Cookie(Constants.AUTO_NAME,encode);
                    cookie.setPath("/");
                    cookie.setMaxAge(60*60*24*14);
                    response.addCookie(cookie);
                } else {
                    // 用户没有勾选
                    Cookie cookie = new Cookie(Constants.AUTO_NAME,"");
                    cookie.setPath("/");
                    cookie.setMaxAge(0);
                    response.addCookie(cookie);
                }
                request.getSession().setAttribute("loginUser",user);
                return Constants.REDIRECT+"/index.jsp";
            } else if (user!=null && user.getUstatus()==Constants.USER_NOT_ACTIVE) {
                request.setAttribute("msg","账户尚未激活，请激活");
                return Constants.FORWARD+"/login.jsp";
            } else {
                request.setAttribute("msg","用户名或密码错误");
                return Constants.FORWARD+"/login.jsp";
            }
        } else {
            request.setAttribute("msg","验证码错误");
            return Constants.FORWARD+"/login.jsp";
        }

    }

    /**
     * 注销登录
     * @param request
     * @param response
     * @return
     * @throws ServletException
     * @throws IOException
     */
    public String logOut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 清除Session中的loginUser
        request.getSession().removeAttribute("loginUser");
        // 覆盖Cookie
        Cookie cookie = new Cookie(Constants.AUTO_NAME, "");
        cookie.setPath("/");
        cookie.setMaxAge(0);
        response.addCookie(cookie);
        // 重定向到login.jsp
        request.setAttribute("msg","注销登录成功");
        return Constants.FORWARD+"/login.jsp";
    }

}
