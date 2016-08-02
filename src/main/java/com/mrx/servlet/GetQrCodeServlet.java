package com.mrx.servlet;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.zxing.BarcodeFormat;
import com.mrx.util.QRCode;
import com.mrx.util.ResourceUtil;

/**
 * 生成二维码图片以及uuid
 *
 */
public class GetQrCodeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException {
		PrintWriter out = response.getWriter();
		//生成唯一ID
		int uuid = (int) (Math.random() * 100000);
		//生成二维码
		String imgName = uuid + "_" + (int) (new Date().getTime() / 1000) + ".png";
		String path = getServletContext().getRealPath("/")+"images/";
		File file = new File(path+imgName);
		String callbackurl = URLEncoder.encode(ResourceUtil.getConfigByName("callbackurl") +"&uuid="+uuid);
		QRCode.encode("https://open.weixin.qq.com/connect/oauth2/authorize?appid=wx812eea92b8bd7255&redirect_uri="+callbackurl+"&response_type=code&scope=snsapi_userinfo&state=STATE#wechat_redirect ", file,"png", BarcodeFormat.QR_CODE, 500, 500, null);
		
		//生成的图片访问地址
		String qrCodeImg = "http://m.mr-x.com.cn/images/" + imgName;
		String jsonStr = "{\"uuid\":" + uuid + ",\"qrCodeImg\":\"" + qrCodeImg + "\"}";
		out.print(jsonStr);
		out.flush();
		out.close();
	}
}
