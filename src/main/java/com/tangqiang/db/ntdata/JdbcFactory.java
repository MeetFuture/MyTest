package com.tangqiang.db.ntdata;

import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/**
 * TODO
 *
 * @author tqiang
 * @email tom617288330@gmail.com
 * @date 2014-7-29 上午8:51:11
 * 
 * @version 1.0 2014-7-29 tqiang  create
 *
 */
public class JdbcFactory {
	private static Logger logger = LoggerFactory.getLogger(JdbcFactory.class); // 日志记录器
	
	public static JdbcTemplate jdbcTemplate = null;
	private static ComboPooledDataSource cpds = null;
	private static String jdbcPropFile = "/jdbc.properties";
	private static String c3p0PropFile = "/c3p0.properties"; //该配置文件放置在根目录下会自动加载

	static {
		try {
			//com.mchange.v1.cachedstore.
			// 从文件/conf/jdbc.properties获取数据库连接信息
			logger.info("开始创建c3p0数据源, 数据库配置文件:" + jdbcPropFile);
			Properties propsJdbc = new Properties();
			InputStream inputStream = JdbcFactory.class.getResourceAsStream(jdbcPropFile);
			propsJdbc.load(inputStream);
			

			cpds = new ComboPooledDataSource();
			cpds.setDriverClass(propsJdbc.getProperty("jdbc.DriverClass"));
			cpds.setJdbcUrl(propsJdbc.getProperty("jdbc.JdbcUrl"));
			cpds.setUser(propsJdbc.getProperty("jdbc.User"));
			cpds.setPassword(propsJdbc.getProperty("jdbc.Password"));
			
			//cpds.setProperties(propsC3p0);
			
			jdbcTemplate = new JdbcTemplate(cpds);
			logger.info("创建c3p0数据源完成!");
		} catch (Exception e) {
			logger.error("创建c3p0数据源出现异常!", e);
		}
	}
	
}
