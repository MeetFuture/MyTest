package com.tangqiang.db;

import java.io.InputStream;
import java.util.Properties;

import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * 
 *
 * @author Tom
 * @date 2017年5月5日 下午2:34:46
 *
 * @version 1.0 2017年5月5日 Tom create
 * 
 * @copyright Copyright © 2017 广电运通 All rights reserved.
 */
public class JdbcFactory {
	private static final Logger logUtil = LoggerFactory.getLogger(JdbcFactory.class); // 日志记录器
	private static DruidDataSource cpds = null;
	private static String sPropertiesFile = "/jdbc.properties";
	public static Properties props = null;

	static {
		try {
			// 从文件/conf/jdbc.properties获取数据库连接信息
			props = new Properties();
			InputStream inputStream = JdbcFactory.class.getResourceAsStream(sPropertiesFile);
			props.load(inputStream);
			cpds = new DruidDataSource();
			cpds.setDriverClassName(props.getProperty("jdbc.driver"));
			cpds.setUrl(props.getProperty("jdbc.url"));
			cpds.setUsername(props.getProperty("jdbc.username"));
			cpds.setPassword(props.getProperty("jdbc.password"));
			logUtil.info("JdbcFactory url:" + props.getProperty("jdbc.url") + "  username:" + props.getProperty("jdbc.username") + "  password:" + props.getProperty("jdbc.password"));

			cpds.setFilters("stat");
			cpds.setMaxActive(10);
			cpds.setInitialSize(1);
			cpds.setMaxWait(60000);
			cpds.setMinIdle(1);
			cpds.setTimeBetweenEvictionRunsMillis(60000);
			cpds.setMinEvictableIdleTimeMillis(300000);

			cpds.setValidationQuery("SELECT 1 FROM DURL");
			cpds.setTestWhileIdle(true);
			cpds.setTestOnBorrow(false);
			cpds.setTestOnReturn(false);

			cpds.setPoolPreparedStatements(true);
			cpds.setMaxPoolPreparedStatementPerConnectionSize(50);
		} catch (Exception e) {
			logUtil.error("获取连接失败：", e);
			cpds = null;
		}
	}

	public static DataSource getDataSource() {
		return cpds;
	}
}
