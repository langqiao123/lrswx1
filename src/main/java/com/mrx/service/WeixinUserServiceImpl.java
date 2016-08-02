package com.mrx.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mrx.dao.WeixinUserDao;
import com.mrx.model.WeixinUser;
import com.mrx.service.intf.WeixinUserService;

@Service
public class WeixinUserServiceImpl implements WeixinUserService{

	@Autowired
	private WeixinUserDao weixinDao;
	
	public boolean saveWeixinLogin(WeixinUser user) {
		return weixinDao.saveWeixinLogin(user);
	}
	
	public String sayHello(String s){
		System.out.println(s);
		return s;
	}
}
