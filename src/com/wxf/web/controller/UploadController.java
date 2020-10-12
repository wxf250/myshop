package com.wxf.web.controller;

import com.wxf.entity.Product;
import com.wxf.service.ProductService;
import com.wxf.service.impl.ProductServiceImpl;
import com.wxf.utils.UploadUtils;
import org.apache.commons.beanutils.BeanUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@WebServlet("/admin/upload")
/* 配置上传文件大小 */
@MultipartConfig(maxFileSize = 100*1024*1024,maxRequestSize = 200*1024*1024)
public class UploadController extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //处理乱码
        request.setCharacterEncoding("utf-8");
        response.setContentType("text/html;charset=utf-8");
        ProductService productService = new ProductServiceImpl();
        String time = request.getParameter("time");
        Map<String, String[]> parameterMap = request.getParameterMap();
        Product product = new Product();
        try {
            BeanUtils.populate(product,parameterMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        //创建项目路径--项目部署路径
        String path = this.getServletContext().getRealPath("/upload");
        String contextPath = request.getContextPath()+"/upload";
//        System.out.println("部署："+path);
        Part part = part = request.getPart("pimage");   //拿二进制信息对象
        String oldName = part.getSubmittedFileName();
        //限制上传文件类型: jpg   jpeg   png  bmp
        List<String> list = new ArrayList<>();
        list.add(".jpg");
        list.add(".jpeg");
        list.add(".png");
        list.add(".bmp");
        String exName = oldName.substring(oldName.indexOf("."));
        if(!list.contains(exName)){
            request.getSession().setAttribute("msg",exName+"格式的上传类型受限制");
            request.getRequestDispatcher("/message.jsp").forward(request,response);
        }
        //处理文件重名问题
        String newName = UploadUtils.getNewName(oldName);  //得到uuid拼成的新文件名
        path = UploadUtils.getNewPath(path,oldName);     //得到新拼成的散列目录
        part.write(path+"/"+newName);    //将资源写入到指定路径中，将资源存到项目路径
        String substring = path.substring(path.lastIndexOf("exploded") + 9);
        product.setPimage(substring+"/"+newName);
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-hh");
        try {
            Date date = simpleDateFormat.parse(time);
            product.setPtime(date);
            productService.addProduct(product);
        } catch (Exception e) {
            e.printStackTrace();
        }

        request.getRequestDispatcher("/admin/admin?method=getGoodsList").forward(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }
}
