package com.wxf.web.filter;

import com.wxf.entity.User;
import com.wxf.service.UserService;
import com.wxf.service.impl.UserServiceImpl;
import com.wxf.utils.Base64Utils;
import com.wxf.utils.Constants;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/login.jsp")
public class AutoFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        // 获取cookie
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                // 判断是否有AutoName
                if (cookie.getName().equals(Constants.AUTO_NAME)) {
                    String value = cookie.getValue();
                    String decode = Base64Utils.decode(value);
                    String[] split = decode.split(Constants.FLAG);
                    String uname = split[0];
                    String upassword = split[1];
                    UserService userService = new UserServiceImpl();
                    User user = userService.login(uname, upassword);
                    if (user!=null) {
                        request.getSession().setAttribute("loginUser",user);
                        HttpServletResponse response = (HttpServletResponse) resp;
                        response.sendRedirect(request.getContextPath()+"/index.jsp");
                    } else {
                        chain.doFilter(req,resp);
                    }
                } else {
                    chain.doFilter(req,resp);
                }
            }

        } else {
            chain.doFilter(req, resp);
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
