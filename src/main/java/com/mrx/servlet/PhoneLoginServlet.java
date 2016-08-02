package com.mrx.servlet;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import com.mrx.model.WeixinUser;
import com.mrx.service.WeixinUserServiceImpl;
import com.mrx.service.intf.WeixinUserService;
import com.mrx.util.HttpUtil;
import com.mrx.util.ResourceUtil;
import com.mrx.vo.LoginUserVo;
/**
* 二维码手机端登录
*
*/
@Controller
public class PhoneLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Autowired
	private WeixinUserService weixinUserService;
	
	public PhoneLoginServlet() {
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String uuid = request.getParameter("uuid");
		String code = request.getParameter("code");
		boolean bool = true;
		new WeixinUserServiceImpl().saveWeixinLogin(null);
		//根据回调url返回的code换取网页授权access_token
		if(null != code && code != ""){
			String appid = ResourceUtil.getConfigByName("appid");
			String appsecret = ResourceUtil.getConfigByName("appsecret");
            JSONObject json_accessToken = HttpUtil.httpGet("https://api.weixin.qq.com/sns/oauth2/access_token?appid="+appid+"&secret="+appsecret+"&code="+code+"&grant_type=authorization_code");
            String access_token = json_accessToken.getString("access_token");
            String openid = json_accessToken.getString("openid");  
            //拉取用户信息
            JSONObject json_userinfo = HttpUtil.httpGet("https://api.weixin.qq.com/sns/userinfo?access_token="+access_token+"&openid="+openid);
            //TODO 验证登录
            if(bool){
            	//将登陆信息存入map
            	WeixinUser userVo = (WeixinUser) LoginUserVo.getLoginUserMap().get(uuid);
            	if(userVo == null){
            		userVo = new WeixinUser();
            		userVo.setOpenid(json_userinfo.getString("openid"));
            		userVo.setNickname(json_userinfo.getString("nickname"));
            		userVo.setSex(json_userinfo.getString("sex"));
            		userVo.setProvince(json_userinfo.getString("province"));
            		userVo.setCity(json_userinfo.getString("city"));
            		//WeixinUserService weixinUserService = new WeixinUserService();
            		weixinUserService.saveWeixinLogin(userVo);
            		LoginUserVo.getLoginUserMap().put(uuid, userVo);
            	}
            }
		}
		PrintWriter out = response.getWriter();
		out.print(bool);
		out.flush();
		out.close();
	}
}