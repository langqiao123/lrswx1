package com.mrx.service.intf;

import com.mrx.model.WeixinUser;

public interface WeixinUserService {

	public boolean saveWeixinLogin(WeixinUser user) ;
	
	public String sayHello(String s);
	
}
