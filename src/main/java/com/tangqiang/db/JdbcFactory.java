package com.tangqiang.db;

import java.io.InputStream;
import java.util.Properties;

import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * TODO
 *
 * @author tqiang
 * @email tqiang@grgbanking.com
 * @file com.tangqiang.db.JdbcFactory.java 
 * @date 2014-7-2 上午10:49:19
 *
 * @version 1.0  2014-7-2  tqiang  create
 * 
 * @copyright Copyright © 2011-2014 广电运通 All rights reserved.
 */
public class JdbcFactory { 
	private static final Logger logUtil = Logger.getLogger(JdbcFactory.class); // 日志记录器
	private static ComboPooledDataSource cpds = null;
	private static String sPropertiesFile = "/conf/jdbc.properties";

	static {
		try {
			// 从文件/conf/jdbc.properties获取数据库连接信息
			Properties props = new Properties();
			InputStream inputStream = JdbcFactory.class
					.getResourceAsStream(sPropertiesFile);
			props.load(inputStream);
			cpds = new ComboPooledDataSource();
			cpds.setDriverClass(props.getProperty("jdbc.driver"));
			cpds.setJdbcUrl(props.getProperty("jdbc.url"));
			cpds.setUser(props.getProperty("jdbc.username"));
			cpds.setPassword(props.getProperty("jdbc.password"));
			
			// 连接池中保留的最小连接数
			cpds.setMinPoolSize(3);
			// 连接池中保留的最大连接数
			cpds.setMaxPoolSize(10);
			// 初始化时获取连接数量
			cpds.setInitialPoolSize(3);
			// 最大空闲时间,指定秒内未使用则连接被丢弃。若为0则永不丢弃。
			cpds.setMaxIdleTime(1000);
			// 当连接池中的连接耗尽的时候c3p0一次同时获取的连接数
			cpds.setAcquireIncrement(50);
			// 定义在从数据库获取新连接失败后重复尝试的次数
			cpds.setAcquireRetryAttempts(20);
			cpds.setAcquireRetryDelay(1000);
			// 如果设为true那么在取得连接的同时将校验连接的有效性
			cpds.setTestConnectionOnCheckin(false);
			// 定义所有连接测试都执行的测试语句。在使用连接测试的情况下这个一显著提高测试速度
			cpds.setTestConnectionOnCheckout(false);
			cpds.setAutomaticTestTable("c3p0Test");
			cpds.setIdleConnectionTestPeriod(1800);
			cpds.setCheckoutTimeout(10000);
		} catch (Exception e) {
			e.printStackTrace();
			logUtil.error("获取连接失败：", e);
			cpds = null;
		}
	}


	
	public static DataSource getDataSource(){
		return cpds;
	}
}
