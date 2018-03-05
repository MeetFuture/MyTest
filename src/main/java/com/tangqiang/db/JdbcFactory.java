package com.tangqiang.db;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

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
	private static Logger logger = LoggerFactory.getLogger(JdbcFactory.class);
	public static JdbcTemplate jdbcTemplate = null;

	static {
		try {
			jdbcTemplate = new JdbcTemplate(DruidDataSourceFactory.getDataSource());
		} catch (Exception e) {
			logger.error("创建数据源出现异常!", e);
		}
	}
	
}
