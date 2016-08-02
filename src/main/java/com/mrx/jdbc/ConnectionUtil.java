package com.mrx.jdbc;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jolbox.bonecp.BoneCP;
import com.jolbox.bonecp.BoneCPConfig;

public class ConnectionUtil {

	private static Log log = LogFactory.getLog(ConnectionUtil.class);
	
	private static Properties prop = new Properties();
	
	private static BoneCP connectionPool = null;
	
	private static final ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();
	static {
		try {
			// 读取数据源配置信息
			InputStream is = com.mrx.jdbc.ConnectionUtil.class.getResourceAsStream(
	                "/dbconfig.properties");
			prop.load(is);
	        is.close();	
			Class.forName(prop.getProperty("db_driverClass"));
			BoneCPConfig config = new BoneCPConfig();
			config.setJdbcUrl(prop.getProperty("db_jdbcUrl"));
			config.setUsername(prop.getProperty("db_user"));
			config.setPassword(prop.getProperty("db_password"));
			
			config.setIdleMaxAgeInMinutes(Long.parseLong(prop.getProperty("bonecp.idleMaxAgeInMinutes")));
			config.setIdleConnectionTestPeriodInMinutes(Long.parseLong(prop.getProperty("bonecp.idleConnectionTestPeriodInMinutes")));
			config.setPartitionCount(Integer.parseInt(prop.getProperty("bonecp.partitionCount")));
			config.setAcquireIncrement(Integer.parseInt(prop.getProperty("bonecp.acquireIncrement")));
			config.setMaxConnectionsPerPartition(Integer.parseInt(prop.getProperty("bonecp.maxConnectionsPerPartition")));
			config.setMinConnectionsPerPartition(Integer.parseInt(prop.getProperty("bonecp.minConnectionsPerPartition")));
			config.setStatementsCacheSize(Integer.parseInt(prop.getProperty("bonecp.statementsCacheSize")));
			config.setReleaseHelperThreads(Integer.parseInt(prop.getProperty("bonecp.releaseHelperThreads")));
			config.setStatementsCachedPerConnection(Integer.parseInt(prop.getProperty("bonecp.statementsCachedPerConnection")));
			
			connectionPool = new BoneCP(config);
			
		} catch (IOException e) {
			log.error("属性文件读取错误", e);
		} catch (ClassNotFoundException e) {
			log.error("加载数据库驱动错误", e);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static Connection getConn() {
		Connection conn = threadLocal.get();
		try {
			if(conn == null || conn.isClosed()) {
				conn = connectionPool.getConnection();
				threadLocal.set(conn);
			}
		} catch(SQLException e) {
			log.error("获取连接异常", e);
			throw new IllegalStateException(e);
		}
		return conn;
	}
	
	/**
	 * 关闭connection
	 * @param conn
	 */
	public static void closeConn() {
		Connection conn = threadLocal.get();
		threadLocal.remove();
		
		try {
			if(conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			log.error("关闭连接异常", e);
			e.printStackTrace();
		}
	}
	
	public static void closeAll(Connection conn,Statement stm,ResultSet rst){
		try {
			if(conn!=null)
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("关闭数据库连接时发生异常："+e);
		}
		
		try {
			if(stm!=null)
			stm.close();
		} catch (SQLException e1) {
			e1.printStackTrace();
			log.error("关闭stm时发生异常："+e1);
		}
		try {
			if(rst!=null)
			rst.close();
		} catch (SQLException e) {
			e.printStackTrace();
			log.error("关闭rst时发生异常："+e);
		}
	}
}
