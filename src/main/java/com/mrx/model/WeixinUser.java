package com.mrx.model;

import java.io.Serializable;

public class WeixinUser implements Serializable{
	private static final long serialVersionUID = 1L;
	private String openid;
	private String nickname;
	private String sex;
	private String province;
	private String city;
	public String getOpenid() {
		return openid;
	}
	@Override
	public String toString() {
		return "UserVo [openid=" + openid + ", nickname=" + nickname + ", sex="
				+ sex + ", province=" + province + ", city=" + city + "]";
	}
	public void setOpenid(String openid) {
		this.openid = openid;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getProvince() {
		return province;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
}