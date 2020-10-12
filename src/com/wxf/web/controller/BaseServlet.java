package com.wxf.web.controller;


import com.wxf.utils.Constants;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Method;

/**
 * BaseServlet用于集中处理方法的调用！
 * 以及返回值处理！
 * 以及默认页对应方法！
 */
public class BaseServlet extends HttpServlet {

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp)  {
        
        //1.获取请求参数（标识符）
        String methodStr = req.getParameter(Constants.TAG);


        //2.如果method没有获取到值！我们就跳转到首页！（标识符异常处理）
        if (methodStr == null && methodStr.equals("")) {
            methodStr = Constants.INDEX;
        }

        //3.反射调用对应的业务逻辑方法
        Class  clazz = this.getClass();

        try {
            Method method = clazz.getMethod(methodStr, HttpServletRequest.class, HttpServletResponse.class);

            Object result = method.invoke(this,req,resp);

            //4.集中处理返回值响应
            if (result != null) {
                //转发 重定向  返回字符
                String str = (String) result;
                if (str.startsWith(Constants.FORWARD)) {
                    //转发
                    String path = str.substring(str.indexOf(Constants.FLAG) + 1);
                    req.getRequestDispatcher(path).forward(req,resp);
                }else if (str.startsWith(Constants.REDIRECT)){
                    //重定向
                    String path = str.substring(str.indexOf(Constants.FLAG) + 1);
                    resp.sendRedirect(req.getContextPath()+path);
                }else{
                    resp.getWriter().println(str);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            //没有反射到方法
            req.getSession().setAttribute("msg","当前服务器繁忙，请稍后重试");
            try {
                resp.sendRedirect(req.getContextPath()+"/message.jsp");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    /**
     * 当method标识符‘没有值’ 我们默认赋 index 访问每个controller的index方法！
     * 我们将方法提取到baseservlet中即可！
     * 默认处理：跳转到程序的首页！
     * @param req
     * @param resp
     * @return
     * @throws IOException
     * @throws ServletException
     */
    public String index(HttpServletRequest req,HttpServletResponse resp) {

        return Constants.FORWARD+"/index.jsp";
    }
}