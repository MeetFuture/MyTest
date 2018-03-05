package com.tangqiang.grgbanking.db;

import java.io.File;
import java.text.DecimalFormat;

import javax.swing.filechooser.FileSystemView;


import com.tangqiang.db.JdbcFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;



/**
 * 数据添加
 * 
 * @author tqiang
 * @email tqiang@grgbanking.com
 * @file com.tangqiang.db.InsertData.java
 * @date 2014-7-2 上午10:44:54
 * 
 * @version 1.0 2014-7-2 tqiang create
 * 
 * @copyright Copyright © 2011-2014 广电运通 All rights reserved.
 */
public class DBCreate {
	private Logger logger = LoggerFactory.getLogger(DBCreate.class);
	private JdbcTemplate jdbcTemplate = JdbcFactory.jdbcTemplate;

	public static void main(String[] args) {
		DBCreate id = new DBCreate();
		id.runTest();
	}

	public void runTest() {
		dropTXNTable(0, 100);
		dropNTSENTTable(0, 200);

//		createTXNTable(0, 100);
//		createNTSENTTable(0, 200);
		// putTableData(1, 2);
		// dropTable(0, 1500);
	}

	/**
	 * 创建表
	 * 
	 * @param iNoBegin
	 * @param iNoEnd
	 */
	private void createNTSENTTable(int iNoBegin, int iNoEnd) {
		for (int i = iNoBegin; i < iNoEnd; i++) {
			try {
				StringBuilder sbCreateTable = new StringBuilder();
				sbCreateTable.append("CREATE TABLE NT_SENT_" + i);
				sbCreateTable.append("(");
				sbCreateTable.append("C_ID VARCHAR(32) NOT NULL,");
				sbCreateTable.append("C_NT_TXN_ID VARCHAR(32) NOT NULL,");
				sbCreateTable.append("C_SERIAL_NO VARCHAR(32) NOT NULL,");
				sbCreateTable.append("C_TERMCODE VARCHAR(64),");
				sbCreateTable.append("C_C_JOURNAL_NO VARCHAR(32),");
				sbCreateTable.append("C_CASH_FLAG CHAR(2),");
				sbCreateTable.append("C_CASH_TYPE CHAR(2),");
				sbCreateTable.append("C_CASH_CLASS VARCHAR(8),");
				sbCreateTable.append("C_CASH_CURRENCY VARCHAR(10),");
				sbCreateTable.append("C_CASH_BOX_ID VARCHAR(5),");
				sbCreateTable.append("D_TRANS_DATE DATETIME,");
				//sbCreateTable.append("C_FILESERVER_ID VARCHAR(32),");
				sbCreateTable.append("C_SPARE_STR1 VARCHAR(32),");
				sbCreateTable.append("C_SPARE_STR2 VARCHAR(32),");
				sbCreateTable.append("I_SPARE_INT1 INTEGER,");
				sbCreateTable.append("I_SPARE_INT2 INTEGER,");
				sbCreateTable.append("PRIMARY KEY (C_ID),");
				sbCreateTable.append("INDEX INDEX_NT_SENT_NT_" + i + " (C_NT_TXN_ID),");
				sbCreateTable.append("INDEX INDEX_NT_SENT_SERIAL_" + i + " (C_SERIAL_NO),");
				sbCreateTable.append("INDEX INDEX_NT_SENT_DATE_" + i + " (d_TRANS_DATE)");
				sbCreateTable.append(")");
				sbCreateTable.append("ENGINE=MyISAM DEFAULT CHARSET=utf8;");

				String sCreateSql = sbCreateTable.toString();
				logger.info("开始执行sql:" + sCreateSql);
				jdbcTemplate.execute(sCreateSql);
			} catch (Exception e) {
				logger.error("创建表失败!", e);
			}
		}

		StringBuilder sbCreateAll = new StringBuilder();
		sbCreateAll.append("CREATE TABLE NT_SENT_ALL");
		sbCreateAll.append("(");
		sbCreateAll.append("C_ID VARCHAR(32) NOT NULL,");
		sbCreateAll.append("C_NT_TXN_ID VARCHAR(32) NOT NULL,");
		sbCreateAll.append("C_SERIAL_NO VARCHAR(32) NOT NULL,");
		sbCreateAll.append("C_TERMCODE VARCHAR(64),");
		sbCreateAll.append("C_C_JOURNAL_NO VARCHAR(32),");
		sbCreateAll.append("C_CASH_FLAG CHAR(2),");
		sbCreateAll.append("C_CASH_TYPE CHAR(2),");
		sbCreateAll.append("C_CASH_CLASS VARCHAR(8),");
		sbCreateAll.append("C_CASH_CURRENCY VARCHAR(10),");
		sbCreateAll.append("C_CASH_BOX_ID VARCHAR(5),");
		sbCreateAll.append("D_TRANS_DATE DATETIME,");
		//sbCreateAll.append("C_FILESERVER_ID VARCHAR(32),");
		sbCreateAll.append("C_SPARE_STR1 VARCHAR(32),");
		sbCreateAll.append("C_SPARE_STR2 VARCHAR(32),");
		sbCreateAll.append("I_SPARE_INT1 INTEGER,");
		sbCreateAll.append("I_SPARE_INT2 INTEGER,");
		sbCreateAll.append("PRIMARY KEY (C_ID),");
		sbCreateAll.append("INDEX INDEX_NT_SENT_NT_ALL (C_NT_TXN_ID),");
		sbCreateAll.append("INDEX INDEX_NT_SENT_SERIAL_ALL (C_SERIAL_NO),");
		sbCreateAll.append("INDEX INDEX_NT_SENT_DATE_ALL (d_TRANS_DATE)");
		sbCreateAll.append(")");
		sbCreateAll.append("ENGINE=MERGE UNION=(");
		for (int j = iNoBegin; j < iNoEnd; j++) {
			sbCreateAll.append("nt_sent_" + j);
			if (j != iNoEnd - 1) {
				sbCreateAll.append(",");
			}
		}
		sbCreateAll.append(") DEFAULT CHARSET=utf8;");
		String sCreateSql = sbCreateAll.toString();
		logger.info("开始执行sql:" + sCreateSql);
		jdbcTemplate.execute(sCreateSql);
	}

	/**
	 * 创建表
	 * 
	 * @param iNoBegin
	 * @param iNoEnd
	 */
	private void createTXNTable(int iNoBegin, int iNoEnd) {
		for (int i = iNoBegin; i < iNoEnd; i++) {
			try {
				StringBuilder sbCreateTable = new StringBuilder();
				sbCreateTable.append("CREATE TABLE NT_TXN_" + i);
				sbCreateTable.append("(");
				sbCreateTable.append("C_ID VARCHAR(32) NOT NULL,");
				sbCreateTable.append("C_TERMCODE VARCHAR(32),");

				sbCreateTable.append("C_CARD_NO VARCHAR(32),");
				sbCreateTable.append("C_TRANS_CODE VARCHAR(8),");
				sbCreateTable.append("C_C_JOURNAL_NO VARCHAR(32),");
				sbCreateTable.append("C_P_JOURNAL_NO VARCHAR(32),");
				sbCreateTable.append("C_TRANS_RESULT CHAR(1),");

				sbCreateTable.append("I_TRANS_AMOUNT DECIMAL(20,0),");
				sbCreateTable.append("I_TRANS_COUNT DECIMAL(20,0),");
				sbCreateTable.append("I_RETRACT_COUNT DECIMAL(20,0),");

				sbCreateTable.append("C_ACCOUNT_NO VARCHAR(32),");
				sbCreateTable.append("C_ACCOUNT_NAME VARCHAR(32),");
				sbCreateTable.append("C_PHONE_NO VARCHAR(32),");

				sbCreateTable.append("D_TRANS_DATE DATETIME,");
				sbCreateTable.append("C_SPARE_STR1 VARCHAR(32),");
				sbCreateTable.append("C_SPARE_STR2 VARCHAR(32),");
				sbCreateTable.append("I_SPARE_INT1 INTEGER,");
				sbCreateTable.append("I_SPARE_INT2 INTEGER,");
				sbCreateTable.append("PRIMARY KEY (C_ID),");
				sbCreateTable.append("INDEX INDEX_NT_TXN_TERMCODE_" + i + " (C_TERMCODE),");
				sbCreateTable.append("INDEX INDEX_NT_TXN_CARDNO_" + i + " (C_CARD_NO),");
				sbCreateTable.append("INDEX INDEX_NT_TXN__DATE_" + i + " (D_TRANS_DATE)");
				sbCreateTable.append(")");
				sbCreateTable.append("ENGINE=MyISAM DEFAULT CHARSET=utf8;");

				String sCreateSql = sbCreateTable.toString();
				logger.info("开始执行sql:" + sCreateSql);
				jdbcTemplate.execute(sCreateSql);
			} catch (Exception e) {
				logger.error("创建表失败!", e);
			}
		}

		StringBuilder sbCreateAll = new StringBuilder();
		sbCreateAll.append("CREATE TABLE NT_TXN_ALL");
		sbCreateAll.append("(");
		sbCreateAll.append("C_ID VARCHAR(32) NOT NULL,");
		sbCreateAll.append("C_TERMCODE VARCHAR(32),");

		sbCreateAll.append("C_CARD_NO VARCHAR(32),");
		sbCreateAll.append("C_TRANS_CODE VARCHAR(8),");
		sbCreateAll.append("C_C_JOURNAL_NO VARCHAR(32),");
		sbCreateAll.append("C_P_JOURNAL_NO VARCHAR(32),");
		sbCreateAll.append("C_TRANS_RESULT CHAR(1),");

		sbCreateAll.append("I_TRANS_AMOUNT DECIMAL(20,0),");
		sbCreateAll.append("I_TRANS_COUNT DECIMAL(20,0),");
		sbCreateAll.append("I_RETRACT_COUNT DECIMAL(20,0),");

		sbCreateAll.append("C_ACCOUNT_NO VARCHAR(32),");
		sbCreateAll.append("C_ACCOUNT_NAME VARCHAR(32),");
		sbCreateAll.append("C_PHONE_NO VARCHAR(32),");

		sbCreateAll.append("D_TRANS_DATE DATETIME,");
		sbCreateAll.append("C_SPARE_STR1 VARCHAR(32),");
		sbCreateAll.append("C_SPARE_STR2 VARCHAR(32),");
		sbCreateAll.append("I_SPARE_INT1 INTEGER,");
		sbCreateAll.append("I_SPARE_INT2 INTEGER,");
		sbCreateAll.append("PRIMARY KEY (C_ID),");
		sbCreateAll.append("INDEX INDEX_NT_TXN_TERMCODE_ALL (C_TERMCODE),");
		sbCreateAll.append("INDEX INDEX_NT_TXN_CARDNO_ALL (C_CARD_NO),");
		sbCreateAll.append("INDEX INDEX_NT_TXN_DATE_ALL (D_TRANS_DATE)");
		sbCreateAll.append(")");
		sbCreateAll.append("ENGINE=MERGE UNION=(");
		for (int j = iNoBegin; j < iNoEnd; j++) {
			sbCreateAll.append("NT_TXN_" + j);
			if (j != iNoEnd - 1) {
				sbCreateAll.append(",");
			}
		}
		sbCreateAll.append(") DEFAULT CHARSET=utf8;");
		String sCreateSql = sbCreateAll.toString();
		logger.info("开始执行sql:" + sCreateSql);
		jdbcTemplate.execute(sCreateSql);
	}

	private void dropNTSENTTable(int iNoBegin, int iNoEnd) {
		for (int i = iNoBegin; i < iNoEnd; i++) {
			try {
				String sCreateTable = "DROP TABLE NT_SENT_" + i;
				logger.info("开始执行sql:" + sCreateTable);
				jdbcTemplate.execute(sCreateTable);
			} catch (Exception e) {
				logger.error("删除表失败!", e);
			}
		}

		try {
			String sCreateTable = "DROP TABLE NT_SENT_ALL";
			logger.info("开始执行sql:" + sCreateTable);
			jdbcTemplate.execute(sCreateTable);
		} catch (Exception e) {
			logger.error("删除表失败!", e);
		}
	}

	private void dropTXNTable(int iNoBegin, int iNoEnd) {
		for (int i = iNoBegin; i < iNoEnd; i++) {
			try {
				String sDropTable = "DROP TABLE NT_TXN_" + i;
				logger.info("开始执行sql:" + sDropTable);
				jdbcTemplate.execute(sDropTable);
			} catch (Exception e) {
				logger.error("删除表失败!", e);
			}
		}

		try {
			String sDropTable = "DROP TABLE NT_TXN_ALL";
			logger.info("开始执行sql:" + sDropTable);
			jdbcTemplate.execute(sDropTable);
		} catch (Exception e) {
			logger.error("删除表失败!", e);
		}
	}

	public static String FormetFileSize(long fileS) {
		DecimalFormat df = new DecimalFormat("#.00");
		String fileSizeString = "";
		if (fileS < 1024) {
			fileSizeString = df.format((double) fileS) + "B";
		} else if (fileS < 1048576) {
			fileSizeString = df.format((double) fileS / 1024) + "K";
		} else if (fileS < 1073741824) {
			fileSizeString = df.format((double) fileS / 1048576) + "M";
		} else {
			fileSizeString = df.format((double) fileS / 1073741824) + "G";
		}
		return fileSizeString;
	}

	/**
	 * 获取硬盘的每个盘符
	 */
	public boolean driver() {
		// 当前文件系统类
		FileSystemView fsv = FileSystemView.getFileSystemView();
		// 列出所有windows 磁盘
		File[] fs = File.listRoots();
		// 显示磁盘卷标
		for (int i = 0; i < fs.length; i++) {
			System.out.println(fsv.getSystemDisplayName(fs[i]));
			System.out.print("总大小" + FormetFileSize(fs[i].getTotalSpace()));
			System.out.println("剩余" + FormetFileSize(fs[i].getFreeSpace()) + "   " + fs[i].getFreeSpace());

			String sName = fsv.getSystemDisplayName(fs[i]);
			long lLast = fs[i].getFreeSpace();
			if (sName.contains("D:") && lLast < 27547746304l) {
				return false;
			}
		}

		return true;
	}
}
