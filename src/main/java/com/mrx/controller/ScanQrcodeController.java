package com.mrx.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.mrx.service.intf.WeixinUserService;

@Controller
@RequestMapping("/scanQrcodeController")
public class ScanQrcodeController {
	
	@Autowired
	private WeixinUserService weixinUserService;
	
	@RequestMapping("/test12344")
	public ModelAndView testModelAndView(){
		weixinUserService.saveWeixinLogin(null);
		System.out.println("nininini");
		return null;
	}
}
