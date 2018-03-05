package com.tangqiang.db;

import org.springframework.jdbc.core.JdbcTemplate;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;

/**
 * 测试
 *
 * @author tqiang
 * @version 1.0 2015年3月12日 tqiang create
 * @email tqiang@grgbanking.com
 * @file com.tangqiang.db.JdbcTest.java
 * @date 2015年3月12日 上午11:32:52
 * @copyright Copyright © 2011-2015 广电运通 All rights reserved.
 */
public class JdbcTest extends Thread {
    private static JdbcTemplate jdbcTemplate = JdbcFactory.jdbcTemplate;

    public static void main(String[] args) throws Exception {
        String sql = "select * from c3p0";

        DatabaseMetaData databaseMetaData = jdbcTemplate.getDataSource().getConnection().getMetaData();
        // 获取所有表
        ResultSet tableSet = databaseMetaData.getTables(null, "%", "%", new String[]{"TABLE"});
        System.out.println(tableSet);
        while (tableSet.next()) {
            String tableName = tableSet.getString("TABLE_NAME");
            String tableComment = tableSet.getString("REMARKS");
            System.out.println("Table Name : " + tableName);
            ResultSet columnSet = databaseMetaData.getColumns(null, "%", tableName, "%");
            while (columnSet.next()) {
                String columnName = columnSet.getString("COLUMN_NAME");
                String columnComment = columnSet.getString("REMARKS");
                String sqlType = columnSet.getString("DATA_TYPE");
            }
        }

    }
}
