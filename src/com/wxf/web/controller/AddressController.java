package com.wxf.web.controller;

import com.wxf.entity.Address;
import com.wxf.entity.User;
import com.wxf.service.AddressService;
import com.wxf.service.impl.AddressServiceImpl;
import com.wxf.utils.Constants;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;


@WebServlet("/address")
public class AddressController extends BaseServlet {
    private AddressService addressService = new AddressServiceImpl();

    /**
     * 用户收获地址展示
     * @param request
     * @param response
     * @return
     */
    public String show(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute("loginUser");
        Integer uid = user.getUid();
        List<Address> addressList = addressService.findByUid(uid);
        request.setAttribute("addressList",addressList);
        return Constants.FORWARD+"/self_info.jsp";
    }

    /**
     * 添加收获地址
     * @param request
     * @param response
     * @return
     */
    public String add(HttpServletRequest request, HttpServletResponse response) {
        // 获取请求参数
        Map<String, String[]> parameterMap = request.getParameterMap();
        Address address = new Address();
        // 封装请求参数
        try {
            BeanUtils.populate(address,parameterMap);
            address.setAstate(Constants.NO_DEFAULT_ADDRESS);
        } catch (Exception e) {
            e.printStackTrace();
        }
        addressService.addAddress(address);
        return Constants.FORWARD+"/address?method=show";
    }

    /**
     * 修改地址为默认地址
     * @param request
     * @param response
     * @return
     */
    public String addToDefault(HttpServletRequest request, HttpServletResponse response) {
        // 获取请求参数aid
        String aidStr = request.getParameter("aid");
        int aid = Integer.parseInt(aidStr);
        User user = (User) request.getSession().getAttribute("loginUser");
        if (user==null) {
            // 用户未登录
            request.setAttribute("msg","添加购物车需先登录");
            return Constants.FORWARD+"/login.jsp";
        }
        // 修改其他地址为普通
        addressService.addToCommon(user.getUid());
        // 调用业务层，修改为默认地址
        addressService.addToDefault(aid);
        // 响应
        return Constants.FORWARD+"/address?method=show";
    }

    /**
     * 修改地址
     * @param request
     * @param response
     * @return
     */
    public String update(HttpServletRequest request, HttpServletResponse response) {
        // 获取请求参数
        Map<String, String[]> parameterMap = request.getParameterMap();
        Address address = new Address();
        // 封装请求参数
        try {
            BeanUtils.populate(address,parameterMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 调用业务层，修改收货信息
        addressService.update(address);
        // 响应
        return Constants.FORWARD+"/address?method=show";
    }
    // 删除地址
    public String delete(HttpServletRequest request, HttpServletResponse response) {
        // 获取请求参数
        String aidStr = request.getParameter("aid");
        int aid = Integer.parseInt(aidStr);
        // 调用业务层，删除地址
        addressService.delete(aid);
        // 响应
        return Constants.FORWARD+"/address?method=show";
    }

}
