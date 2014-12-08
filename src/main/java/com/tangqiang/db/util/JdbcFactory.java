package com.tangqiang.db.util;

import org.apache.log4j.Logger;
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
	private static Logger logger = Logger.getLogger(JdbcFactory.class); // 日志记录器
	
	public static JdbcTemplate jdbcTemplate = null;
	private static ComboPooledDataSource cpds = null;
	private static String c3p0PropFile = "classloader:jdbc.xml"; //该配置文件放置在根目录下会自动加载

	static {
		try {
			logger.info("开始创建c3p0数据源, 数据库配置文件:" + c3p0PropFile);
			System.setProperty("com.mchange.v2.c3p0.cfg.xml", c3p0PropFile);
			cpds = new ComboPooledDataSource("114");
			jdbcTemplate = new JdbcTemplate(cpds);
			logger.info("创建c3p0数据源完成!");
		} catch (Exception e) {
			logger.error("创建c3p0数据源出现异常!", e);
		}
	}
	
	public static void main(String[] args) {
		int i =  jdbcTemplate.queryForInt("select count(*) from nt_txn_4");
		System.out.println(i);
	}
}
