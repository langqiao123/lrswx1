package com.mrx.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;

import com.mrx.service.intf.WeixinUserService;
  
@SuppressWarnings("serial")  
@Controller  
@Scope("prototype")  
public class HelloServlet extends HttpServlet {  
  
    private WeixinUserService weixinUserService;  
      
    @Resource  
    public void setWeixinUserService(WeixinUserService weixinUserService) {  
        this.weixinUserService = weixinUserService;  
    }  
  
    public void doGet(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {  
        System.out.println("Get");  
        PrintWriter out = response.getWriter();  
        out.println(weixinUserService.sayHello("Hello,Spring.Servlet"));    
    }  
  
    public void doPost(HttpServletRequest request, HttpServletResponse response)  
            throws ServletException, IOException {  
        System.out.println("Post");  
        PrintWriter out = response.getWriter();  
        out.println(weixinUserService.sayHello("Hello,Spring.Servlet"));        
    }  
  
}  