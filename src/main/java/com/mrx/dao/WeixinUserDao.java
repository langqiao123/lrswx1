package com.mrx.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Date;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.mrx.jdbc.ConnectionUtil;
import com.mrx.model.WeixinUser;

@Repository
public class WeixinUserDao{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	public boolean saveWeixinLogin(WeixinUser user) {
		boolean flag = true;
		String sqlStr = "insert into weixin_user(id,openid,nickname,sex,province,city,create_date) values(?,?,?,?,?,?,now())";
		Connection conn = null;
		PreparedStatement stm = null;
		try {
			conn = ConnectionUtil.getConn();
			conn.setAutoCommit(false);
			stm = conn.prepareStatement(sqlStr);
			stm.setString(1,UUID.randomUUID().toString()); // 这里将问号赋值
			stm.setString(2,user.getOpenid()); // 这里将问号赋值
			stm.setString(3,user.getNickname()); // 这里将问号赋值
			stm.setString(4,user.getSex()); // 这里将问号赋值
			stm.setString(5,user.getProvince()); // 这里将问号赋值
			stm.setString(6,user.getCity()); // 这里将问号赋值
			Timestamp tsTime = new Timestamp(new Date().getTime());  
			stm.executeUpdate(); 
			conn.commit();
		}catch (Exception e) {
			flag = false;
			try {
				conn.rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			try {
				if(conn != null && !conn.isClosed()) {
					conn.close();
				}
				if(stm != null && !stm.isClosed()) {
					stm.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return flag;
	}
}
