package com.mrx.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;

import com.mrx.dao.WeixinUserDao;
import com.mrx.model.WeixinUser;
import com.mrx.vo.LoginUserVo;
/**
* 用长连接，检查登录状态
*
*/
public class LongConnectionCheckServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uuid = request.getParameter("uuid");
		String jsonStr = "";
		System.out.println("uuid:" + uuid);
		long inTime = new Date().getTime();
		Boolean bool = true;
		while (bool) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			//检测登录
			WeixinUser userVo = (WeixinUser) LoginUserVo.getLoginUserMap().get(uuid);
			if(userVo != null){
				bool = false;
				jsonStr = "{\"uname\":\""+userVo.getOpenid()+"\"}";
				LoginUserVo.getLoginUserMap().remove(uuid);
			}else{
				if(new Date().getTime() - inTime > 5000){
					bool = false;
				}
			}
		}
		PrintWriter out = response.getWriter();
		out.print(jsonStr);
		out.flush();
		out.close();
	}
}
