package com.wxf.web.filter;

import com.wxf.entity.User;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebFilter("/admin/*")
public class AdminFilter implements Filter {
    public void destroy() {
    }

    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws ServletException, IOException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        boolean flag = request.getRequestURI().contains("login.jsp");
        boolean method = request.getRequestURI().contains(".jsp");
        if (flag==true || method==false) {
            chain.doFilter(req,resp);
        } else {
            User user = (User) request.getSession().getAttribute("admin");
            if (user!=null) {
                chain.doFilter(req, resp);
            } else {
                response.sendRedirect(request.getContextPath()+"/admin/login.jsp");
            }
        }
    }

    public void init(FilterConfig config) throws ServletException {

    }

}
