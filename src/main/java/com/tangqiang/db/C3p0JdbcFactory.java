package com.tangqiang.db;

import java.io.InputStream;
import java.util.Properties;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * TODO
 *
 * @author tqiang
 * @email tqiang@grgbanking.com
 * @file com.tangqiang.db.JdbcFactory.java
 * @date 2015年3月12日 上午11:28:55
 *
 * @version 1.0 2015年3月12日 tqiang create
 * 
 * @copyright Copyright © 2011-2015 广电运通 All rights reserved.
 */
public class C3p0JdbcFactory {
	private static final Logger logUtil = LoggerFactory.getLogger(C3p0JdbcFactory.class); // 日志记录器
	private static ComboPooledDataSource cpds = null;
	private static String sPropertiesFile = "/jdbc.properties";
	public static Properties props = null;

	static {
		try {
			// 从文件/conf/jdbc.properties获取数据库连接信息
			props = new Properties();
			InputStream inputStream = C3p0JdbcFactory.class.getResourceAsStream(sPropertiesFile);
			props.load(inputStream);
			cpds = new ComboPooledDataSource();
			cpds.setDriverClass(props.getProperty("jdbc.driver"));
			cpds.setJdbcUrl(props.getProperty("jdbc.url"));
			cpds.setUser(props.getProperty("jdbc.username"));
			cpds.setPassword(props.getProperty("jdbc.password"));
			logUtil.info("JdbcFactory url:" + props.getProperty("jdbc.url") + "  username:" + props.getProperty("jdbc.username") + "  password:" + props.getProperty("jdbc.password"));

			// 连接池中保留的最小连接数
			cpds.setMinPoolSize(1);
			// 连接池中保留的最大连接数
			cpds.setMaxPoolSize(10);
			// 初始化时获取连接数量
			cpds.setInitialPoolSize(1);
			// 最大空闲时间,指定秒内未使用则连接被丢弃。若为0则永不丢弃。
			cpds.setMaxIdleTime(1000);
			// 当连接池中的连接耗尽的时候c3p0一次同时获取的连接数
			cpds.setAcquireIncrement(5);
			// 定义在从数据库获取新连接失败后重复尝试的次数
			cpds.setAcquireRetryAttempts(20);
			cpds.setAcquireRetryDelay(200);
			// 如果设为true那么在取得连接的同时将校验连接的有效性
			cpds.setTestConnectionOnCheckin(false);
			// 定义所有连接测试都执行的测试语句。在使用连接测试的情况下这个一显著提高测试速度
			cpds.setTestConnectionOnCheckout(false);
			// cpds.setAutomaticTestTable("dual");
			cpds.setIdleConnectionTestPeriod(1800);
			cpds.setCheckoutTimeout(1000);
		} catch (Exception e) {
			logUtil.error("获取连接失败：", e);
			cpds = null;
		}
	}

	public static DataSource getDataSource() {
		return cpds;
	}
}
