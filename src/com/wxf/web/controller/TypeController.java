package com.wxf.web.controller;

import com.google.gson.Gson;
import com.wxf.entity.Type;
import com.wxf.service.TypeService;
import com.wxf.service.impl.TypeServiceImpl;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@WebServlet("/type")
public class TypeController extends BaseServlet {
    private TypeService typeService = new TypeServiceImpl();

    /**
     * 查询所有商品类别
     * @param request
     * @param response
     */
    public String findAll(HttpServletRequest request, HttpServletResponse response) {
        // 调用业务逻辑，查询商品类别
        List<Type> typeList = typeService.findAll();
        // 序列化为json
        Gson gson = new Gson();
        String json = gson.toJson(typeList);
        // 响应
        return json;
    }
}
