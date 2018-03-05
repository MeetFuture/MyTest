package com.tangqiang.db.ntdata;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;

import com.tangqiang.db.JdbcFactory;

/**
 * 冠字号数据生成
 * 
 * @author tqiang
 * @email tqiang@grgbanking.com
 * @file com.tangqiang.db.ntdata.InsertData.java
 * @date 2014-7-8 上午11:56:28
 * 
 * @version 1.0 2014-7-8 tqiang create
 * 
 * @copyright Copyright © 2011-2014 广电运通 All rights reserved.
 */
public class DataInsert extends Thread {
	private Logger logger = LoggerFactory.getLogger(DataInsert.class);
	private JdbcTemplate jdbcTemplate = JdbcFactory.jdbcTemplate;
	private int iThread = 0;

	private String[] sArrTable = new String[] { "1", "2", "3", "4", "5", "0" };
	private String[] sArrTermCode = new String[] { "10001", "10002", "20312", "10235", "54620", "12543", "851310" };
	private String[] sArrCardNo = new String[] { "45645610001", "2031210002", "1023520312", "5462010235", "5125434620", "1251254343", "85125431310" };
	private String[] sArrTransCode = new String[] { "1000", "1002", "2012", "1235", "5420", "1253", "1310" };
	private String[] sArrAccountName = new String[] { "A0", "VTM", "CDM", "CRS", "Darlin", "小尹", "FSD", "Tom", "Josn", "Limei" };
	private String[] sArrPhoneNo = new String[] { "45645610001", "2031210002", "1023520312", "5462010235", "5125434620", "1251254343", "85125431310" };
	private String[] sArrSerialNo = new String[] { "A012365489", "VTM1645238", "CDMN985462", "CRSE8795132", "DASL896423", "AMLI695134", "FSED9861354", "MOTO988461", "JOSN741302", "LMGK232015",
			"A012368489", "VTM1632238", "CDMN785462", "CRSE8235132", "DASL835223", "AMLI698134", "FSED9871354", "MOTO918461", "JOSN741682", "LMGK232515", "A012366489", "VTM1645223", "CDMN985445",
			"CRSE8795332", "DASL846423", "AMLI695136", "FSED9351354", "MOTO982461", "JOSN749302", "LMGK243215", "A012365989", "VTM1612238", "CDMN987562", "CRSE8995132", "DASL196423", "AMLI695834",
			"FSED9864354", "MOTO088861", "JOSN743402", "LMGK232115", "A012365849", "VTM1645286", "CDMN935462", "CRSE8796132", "DASL836423", "AMLI693134", "FSED1261354", "MOTO988361", "JOSN771302",
			"LMGK132015" };

	public static void main(String[] args) {
		DataInsert di = new DataInsert();
		// di.emptyDataSENT(1, 200);
		// di.emptyDataTXN(0, 100);

		 for (int i = 0; i < 10000; i++) {
		 di.insertData();
		 }

//		for (int i = 1; i < 100; i++) {
//			DataInsert di = new DataInsert();
//			di.setName(i+ "  ");
//			di.start();
//		}
	}

	public void run() {
		for (int i = 0; i < 11000; i++) {
			try {
				insertData();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
		copyData(iThread);
	}

	private void insertData() {
		String sId = UUID.randomUUID().toString().replaceAll("-", "");
		String sTermCode = getRandom(sArrTermCode);
		String sCardNo = getRandom(sArrCardNo);
		String sTransCode = getRandom(sArrTransCode);
		String sTime = "" + System.currentTimeMillis();
		String cJournalNo = sTime.substring(sTime.length() - 4);
		String pJournalNo = sTime.substring(sTime.length() - 8);
		String sTransResult = "0";

		int iSent = (int) (100 * Math.random()) + 1;
		String sTransAmount = "" + (iSent * 100);// "1000";
		String sTransCount = "" + iSent;
		String sRetractCount = "0";
		String sAccountName = getRandom(sArrAccountName);
		String sPhoneNo = getRandom(sArrPhoneNo);
		String sDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date());
		String sInsertSql = "insert into nt_txn_" + "0" + "(C_ID,C_TERMCODE,C_CARD_NO,C_TRANS_CODE,C_C_JOURNAL_NO,C_P_JOURNAL_NO,"
				+ "C_TRANS_RESULT,I_TRANS_AMOUNT,I_TRANS_COUNT,I_RETRACT_COUNT,C_ACCOUNT_NO,C_ACCOUNT_NAME,C_PHONE_NO,D_TRANS_DATE) " + "values ('"
				+ sId
				+ "','"
				+ sTermCode
				+ "','"
				+ sCardNo
				+ "','"
				+ sTransCode
				+ "','"
				+ cJournalNo
				+ "','"
				+ pJournalNo
				+ "','"
				+ sTransResult
				+ "','"
				+ sTransAmount
				+ "','"
				+ sTransCount
				+ "','"
				+ sRetractCount + "','" + sCardNo + "','" + sAccountName + "','" + sPhoneNo + "','" + sDateTime + "')";
		logger.info(getName()+ "sql:" + sInsertSql);
		jdbcTemplate.execute(sInsertSql);

		 for (int i = 0; i < iSent; i++) {
		 String sSentId = UUID.randomUUID().toString().replaceAll("-", "");
		 String sSerialNo = getRandom(sArrSerialNo);
		 String sCashFlag = "0";
		 String sCashType = "0";
		 String sCashClass = "CYN";
		 String sCashCurrency = "100";
		 StringBuilder sbSqlSent = new StringBuilder();
		 sbSqlSent.append("insert into nt_sent_" + getRandom(sArrTable));
		 sbSqlSent.append("(C_ID,C_NT_TXN_ID,C_SERIAL_NO,C_TERMCODE,C_C_JOURNAL_NO,C_CASH_FLAG,C_CASH_TYPE,C_CASH_CLASS,C_CASH_CURRENCY,C_CASH_BOX_ID,D_TRANS_DATE,C_FILESERVER_ID) values (");
		 sbSqlSent.append("'" + sSentId + "','" + sId + "','" + sSerialNo + "','" + sTermCode + "','" + cJournalNo + "','" + sCashFlag + "','" + sCashType + "','" + sCashClass + "','"
		 + sCashCurrency + "','1','" + sDateTime + "','8a81ddf146ea2b300146ea2e5e6e0003'");
		 sbSqlSent.append(")");
		 jdbcTemplate.execute(sbSqlSent.toString());
		 }
	}

	private void copyData(int i) {
		try {
			long lBeginTime = System.currentTimeMillis();
			StringBuffer sbSqlSent = new StringBuffer();
			// sbSqlSent.append("insert into nt_sent_"+i +" select * from nt_sent_0;");
			sbSqlSent.append("insert into nt_txn_" + getName() + " select * from nt_txn_0;");
			// sbSqlSent.append("(C_ID,C_NT_TXN_ID,C_SERIAL_NO,C_TERMCODE,C_C_JOURNAL_NO,C_CASH_FLAG,C_CASH_TYPE,C_CASH_CLASS,C_CASH_CURRENCY,C_CASH_BOX_ID,D_TRANS_DATE,C_FILESERVER_ID) ");
			// sbSqlSent.append("select concat(left(C_ID,28),'" + i +
			// "'),C_NT_TXN_ID,C_SERIAL_NO,C_TERMCODE,C_C_JOURNAL_NO,C_CASH_FLAG,C_CASH_TYPE,C_CASH_CLASS,C_CASH_CURRENCY,C_CASH_BOX_ID,D_TRANS_DATE,C_FILESERVER_ID from nt_sent_0;");
			String sSql = sbSqlSent.toString();
			logger.info("开始执行sql:" + sSql);
			jdbcTemplate.execute(sbSqlSent.toString());
			long lEndTime = System.currentTimeMillis();
			logger.info(" 执行sql耗时:" + (lEndTime - lBeginTime) + "/ms");
		} catch (Exception e) {
			logger.error("复制数据出现异常!", e);
		}

	}

	private String getRandom(String[] sArrData) {
		int iLocation = (int) (sArrData.length * Math.random());
		// logger.info("随机值:"+iLocation +"		"+sArrData.length);
		return sArrData[iLocation];
	}

	private void emptyDataSENT(int iBegin, int iEnd) {
		for (int i = iBegin; i < iEnd; i++) {
			try {
				String sTruncateSql = "truncate table nt_sent_" + i;
				logger.info("excute : " + sTruncateSql);
				jdbcTemplate.execute(sTruncateSql);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void emptyDataTXN(int iBegin, int iEnd) {
		for (int i = iBegin; i < iEnd; i++) {
			try {
				String sTruncateSql = "truncate table nt_txn_" + i;
				logger.info("excute : " + sTruncateSql);
				jdbcTemplate.execute(sTruncateSql);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}
