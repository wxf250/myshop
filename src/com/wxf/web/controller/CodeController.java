package com.wxf.web.controller;

import cn.dsna.util.images.ValidateCode;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 生成验证码的Controller
 */
@WebServlet("/code")
public class CodeController extends BaseServlet {
    public void createCode(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 生成验证码
        ValidateCode validateCode = new ValidateCode(100,35,5,20);
        // 存入Session
        String code = validateCode.getCode();
        request.getSession().setAttribute("code",code);
        // 向页面写回验证码
        validateCode.write(response.getOutputStream());
    }

}
