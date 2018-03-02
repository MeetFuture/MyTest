package com.tangqiang.grgbanking.db.datainsert;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import com.tangqiang.db.ntdata.JdbcFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.jdbc.core.JdbcTemplate;


/**
 * TODO
 * 
 * @author tqiang
 * @email tom617288330@gmail.com
 * @date 2014-8-11 上午9:55:02
 * 
 * @version 1.0 2014-8-11 tqiang create
 * 
 */
public class DataInsert extends Thread{
	private static Logger logger = LoggerFactory.getLogger(DataInsert.class);
	private JdbcTemplate jdbcTemplate = JdbcFactory.jdbcTemplate;
	private static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
	private static int id = 100000;
	private String[] sArrTable = new String[] { "1", "2", "3", "4", "0" };

	private static String[] sArrTransCode = new String[] { "111", "102", "112", "123", "520", "103", "101" };
	private static String[] sArrTermCode = new String[] { "000000001", "000000002", "000000003", "000000004", "000000005", "000000006", "000000007", "000000008", "000000009", "000000010",
			"000000011", "000000012", "000000013", "000000014", "000000015", "000000016", "000000017", "000000018", "000000019", "000000020", "000000021", "000000022" };

	private static long lTime = System.currentTimeMillis();
	private static String[] sArrDay = new String[] { 
		sdf.format(new Date(lTime - (long) 1 * 86400000)) };

	private static String[] sArrCardNo = new String[] { "4564561000165431", "56412322031210002", "4408511023520312", "3336545462010235", "4400005125434620", "4431201251254343", "53201185125431310" };
	private static String[] sArrAccountName = new String[] { "Xiangqing", "Fengyun", "Aron", "Curise", "Darlin", "小尹", "FoxReader", "Tom", "Josn", "Limei" };
	private static String[] sArrPhoneNo = new String[] { "15017585421", "15117541234", "13865421892", "13365492546", "13202135654", "13765231124", "15236231254" };
	private static String[] sArrSerialNo = new String[] { "A012365489", "VTM1645238", "CDMN985462", "CRSE8795132", "DASL896423", "AMLI695134", "FSED9861354", "MOTO988461", "JOSN741302", "LMGK232015",
			"AQ12368489", "VTM1632238", "CDMN785462", "CRSE8235132", "DASL835223", "AMLI698134", "FSED9871354", "MOTO918461", "JOSN741682", "LMGK232515", "A012366489", "VTM1645223", "CDMN985445",
			"CRSE8795332", "DASL846423", "AMLI695136", "FSED9351354", "MOTO982461", "JOSN749302", "LMGK243215", "A012365989", "VTM1612238", "CDMN987562", "CRSE8995132", "DASL196423", "AMLI695834",
			"FGGD9864354", "MOTO088861", "JOSN743402", "LMGK232115", "A012365849", "VTM1645286", "CDMN935462", "CRSE8796132", "DASL836423", "AMLI693134", "FSED1261354", "MOTO988361", "JOSN771302",
			"LMGK132015" };

	public static void main(String[] args) {
		for (String day : sArrDay) {
			logger.info(day);
		}
		for (int i = 0; i < 1; i++) {
			DataInsert di = new DataInsert();
			di.start();
		}
	}
	
	public void run(){
		for (int i = 0; i < 4000; i++) {
			insertData(i);
		}
	}

	private void insertData(int iBatch) {
		long beginMeth = System.nanoTime();
		String sTxnSql = "insert into nt_txn_4(C_ID,C_TERMCODE,C_CARD_NO,C_TRANS_CODE,C_C_JOURNAL_NO,C_P_JOURNAL_NO,"
				+ "C_TRANS_RESULT,I_TRANS_AMOUNT,I_TRANS_COUNT,I_RETRACT_COUNT,C_ACCOUNT_NO,C_ACCOUNT_NAME,C_PHONE_NO,C_PART_MONTHDAY,D_TRANS_DATE) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
		String sSentSql = "insert into nt_sent_4(C_ID,C_NT_TXN_ID,C_SERIAL_NO,C_TERMCODE,C_C_JOURNAL_NO,C_CASH_FLAG,C_CASH_TYPE,C_CASH_CLASS,C_CASH_CURRENCY,C_CASH_BOX_ID,D_TRANS_DATE,C_PART_MONTHDAY) values (?,?,?,?,?,?,?,?,?,?,?,?)";

		List<Object[]> listTxnData = new ArrayList<Object[]>();
		List<Object[]> listSentData = new ArrayList<Object[]>();
		for (int i = 0; i < 100; i++) {
			String sId = UUID.randomUUID().toString().replaceAll("-", "");
			String sTermCode = getRandom(sArrTermCode);
			String sCardNo = getRandom(sArrCardNo);
			String sTransCode = getRandom(sArrTransCode);
			String sTime = "" + System.currentTimeMillis();
			String cJournalNo = sTime.substring(sTime.length() - 4);
			String pJournalNo = sTime.substring(sTime.length() - 8);
			String sTransResult = "0";

			int iSent = (int) (10 * Math.random()) + 1;
			iSent = 50;
			String sTransAmount = "" + (iSent * 100);// "1000";
			String sTransCount = "" + iSent;
			String sRetractCount = "0";
			String sAccountName = getRandom(sArrAccountName);
			String sPhoneNo = getRandom(sArrPhoneNo);
			String sDay = getRandom(sArrDay);
			String sPartMonth = sDay.substring(4).replaceAll("-", "");
			String sDateTime = sDay + new SimpleDateFormat(" HH:mm:ss").format(new Date());

			Object[] arrTxn = new Object[] { sId, sTermCode, sCardNo, sTransCode, cJournalNo, pJournalNo, sTransResult, sTransAmount, sTransCount, sRetractCount, sCardNo, sAccountName, sPhoneNo,
					sPartMonth, sDateTime };
			listTxnData.add(arrTxn);

			for (int j = 0; j < iSent; j++) {
				//String sSentId = UUID.randomUUID().toString().replaceAll("-", "");
				String sSerialNo = getRandom(sArrSerialNo);
				String sCashFlag = "0";
				String sCashType = "0";
				String sCashClass = "CYN";
				String sCashCurrency = "100";

				Object[] arrSent = new Object[] { id++, sId, sSerialNo, sTermCode, cJournalNo, sCashFlag, sCashType, sCashClass, sCashCurrency, "1", sDateTime, sPartMonth };
				listSentData.add(arrSent);
			}
		}
//		long lBeginTxn = System.nanoTime();
//		jdbcTemplate.batchUpdate(sTxnSql, listTxnData);
//		long lEndTxn = System.nanoTime();
//		logger.info("Txn Insert size:" + listTxnData.size() + " Txn Count:" + iBatch * 1000 + " Used Time:" + (lEndTxn - lBeginTxn) / 1000000 + " ms");

		
		long lBeginSent = System.nanoTime();
		jdbcTemplate.batchUpdate(sSentSql, listSentData);
		long lEndSent = System.nanoTime();
		logger.info("Sent Insert size:" + listSentData.size() + " Sent Count:" + iBatch * 5000 + " Used Time:" + (lEndSent - lBeginSent) / 1000000 + " ms  MethpodTime =" +(lBeginSent - beginMeth) /1000000 );

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

	private static String[] sArrDayYear = new String[] { sdf.format(new Date(lTime - (long) 0 * 86400000)), sdf.format(new Date(lTime - (long) 1 * 86400000)),
			sdf.format(new Date(lTime - (long) 2 * 86400000)), sdf.format(new Date(lTime - (long) 3 * 86400000)), sdf.format(new Date(lTime - (long) 4 * 86400000)),
			sdf.format(new Date(lTime - (long) 5 * 86400000)), sdf.format(new Date(lTime - (long) 6 * 86400000)), sdf.format(new Date(lTime - (long) 7 * 86400000)),
			sdf.format(new Date(lTime - (long) 8 * 86400000)), sdf.format(new Date(lTime - (long) 9 * 86400000)), sdf.format(new Date(lTime - (long) 10 * 86400000)),
			sdf.format(new Date(lTime - (long) 11 * 86400000)), sdf.format(new Date(lTime - (long) 12 * 86400000)), sdf.format(new Date(lTime - (long) 13 * 86400000)),
			sdf.format(new Date(lTime - (long) 14 * 86400000)), sdf.format(new Date(lTime - (long) 15 * 86400000)), sdf.format(new Date(lTime - (long) 16 * 86400000)),
			sdf.format(new Date(lTime - (long) 17 * 86400000)), sdf.format(new Date(lTime - (long) 18 * 86400000)), sdf.format(new Date(lTime - (long) 19 * 86400000)),
			sdf.format(new Date(lTime - (long) 20 * 86400000)), sdf.format(new Date(lTime - (long) 21 * 86400000)), sdf.format(new Date(lTime - (long) 22 * 86400000)),
			sdf.format(new Date(lTime - (long) 23 * 86400000)), sdf.format(new Date(lTime - (long) 24 * 86400000)), sdf.format(new Date(lTime - (long) 25 * 86400000)),
			sdf.format(new Date(lTime - (long) 26 * 86400000)), sdf.format(new Date(lTime - (long) 27 * 86400000)), sdf.format(new Date(lTime - (long) 28 * 86400000)),
			sdf.format(new Date(lTime - (long) 29 * 86400000)), sdf.format(new Date(lTime - (long) 30 * 86400000)), sdf.format(new Date(lTime - (long) 31 * 86400000)),
			sdf.format(new Date(lTime - (long) 32 * 86400000)), sdf.format(new Date(lTime - (long) 33 * 86400000)), sdf.format(new Date(lTime - (long) 34 * 86400000)),
			sdf.format(new Date(lTime - (long) 35 * 86400000)), sdf.format(new Date(lTime - (long) 36 * 86400000)), sdf.format(new Date(lTime - (long) 37 * 86400000)),
			sdf.format(new Date(lTime - (long) 38 * 86400000)), sdf.format(new Date(lTime - (long) 39 * 86400000)), sdf.format(new Date(lTime - (long) 40 * 86400000)),
			sdf.format(new Date(lTime - (long) 41 * 86400000)), sdf.format(new Date(lTime - (long) 42 * 86400000)), sdf.format(new Date(lTime - (long) 43 * 86400000)),
			sdf.format(new Date(lTime - (long) 44 * 86400000)), sdf.format(new Date(lTime - (long) 45 * 86400000)), sdf.format(new Date(lTime - (long) 46 * 86400000)),
			sdf.format(new Date(lTime - (long) 47 * 86400000)), sdf.format(new Date(lTime - (long) 48 * 86400000)), sdf.format(new Date(lTime - (long) 49 * 86400000)),
			sdf.format(new Date(lTime - (long) 50 * 86400000)), sdf.format(new Date(lTime - (long) 51 * 86400000)), sdf.format(new Date(lTime - (long) 52 * 86400000)),
			sdf.format(new Date(lTime - (long) 53 * 86400000)), sdf.format(new Date(lTime - (long) 54 * 86400000)), sdf.format(new Date(lTime - (long) 55 * 86400000)),
			sdf.format(new Date(lTime - (long) 56 * 86400000)), sdf.format(new Date(lTime - (long) 57 * 86400000)), sdf.format(new Date(lTime - (long) 58 * 86400000)),
			sdf.format(new Date(lTime - (long) 59 * 86400000)), sdf.format(new Date(lTime - (long) 60 * 86400000)), sdf.format(new Date(lTime - (long) 61 * 86400000)),
			sdf.format(new Date(lTime - (long) 62 * 86400000)), sdf.format(new Date(lTime - (long) 63 * 86400000)), sdf.format(new Date(lTime - (long) 64 * 86400000)),
			sdf.format(new Date(lTime - (long) 65 * 86400000)), sdf.format(new Date(lTime - (long) 66 * 86400000)), sdf.format(new Date(lTime - (long) 67 * 86400000)),
			sdf.format(new Date(lTime - (long) 68 * 86400000)), sdf.format(new Date(lTime - (long) 69 * 86400000)), sdf.format(new Date(lTime - (long) 70 * 86400000)),
			sdf.format(new Date(lTime - (long) 71 * 86400000)), sdf.format(new Date(lTime - (long) 72 * 86400000)), sdf.format(new Date(lTime - (long) 73 * 86400000)),
			sdf.format(new Date(lTime - (long) 74 * 86400000)), sdf.format(new Date(lTime - (long) 75 * 86400000)), sdf.format(new Date(lTime - (long) 76 * 86400000)),
			sdf.format(new Date(lTime - (long) 77 * 86400000)), sdf.format(new Date(lTime - (long) 78 * 86400000)), sdf.format(new Date(lTime - (long) 79 * 86400000)),
			sdf.format(new Date(lTime - (long) 80 * 86400000)), sdf.format(new Date(lTime - (long) 81 * 86400000)), sdf.format(new Date(lTime - (long) 82 * 86400000)),
			sdf.format(new Date(lTime - (long) 83 * 86400000)), sdf.format(new Date(lTime - (long) 84 * 86400000)), sdf.format(new Date(lTime - (long) 85 * 86400000)),
			sdf.format(new Date(lTime - (long) 86 * 86400000)), sdf.format(new Date(lTime - (long) 87 * 86400000)), sdf.format(new Date(lTime - (long) 88 * 86400000)),
			sdf.format(new Date(lTime - (long) 89 * 86400000)), sdf.format(new Date(lTime - (long) 90 * 86400000)), sdf.format(new Date(lTime - (long) 91 * 86400000)),
			sdf.format(new Date(lTime - (long) 92 * 86400000)), sdf.format(new Date(lTime - (long) 93 * 86400000)), sdf.format(new Date(lTime - (long) 94 * 86400000)),
			sdf.format(new Date(lTime - (long) 95 * 86400000)), sdf.format(new Date(lTime - (long) 96 * 86400000)), sdf.format(new Date(lTime - (long) 97 * 86400000)),
			sdf.format(new Date(lTime - (long) 98 * 86400000)), sdf.format(new Date(lTime - (long) 99 * 86400000)), sdf.format(new Date(lTime - (long) 100 * 86400000)),
			sdf.format(new Date(lTime - (long) 101 * 86400000)), sdf.format(new Date(lTime - (long) 102 * 86400000)), sdf.format(new Date(lTime - (long) 103 * 86400000)),
			sdf.format(new Date(lTime - (long) 104 * 86400000)), sdf.format(new Date(lTime - (long) 105 * 86400000)), sdf.format(new Date(lTime - (long) 106 * 86400000)),
			sdf.format(new Date(lTime - (long) 107 * 86400000)), sdf.format(new Date(lTime - (long) 108 * 86400000)), sdf.format(new Date(lTime - (long) 109 * 86400000)),
			sdf.format(new Date(lTime - (long) 110 * 86400000)), sdf.format(new Date(lTime - (long) 111 * 86400000)), sdf.format(new Date(lTime - (long) 112 * 86400000)),
			sdf.format(new Date(lTime - (long) 113 * 86400000)), sdf.format(new Date(lTime - (long) 114 * 86400000)), sdf.format(new Date(lTime - (long) 115 * 86400000)),
			sdf.format(new Date(lTime - (long) 116 * 86400000)), sdf.format(new Date(lTime - (long) 117 * 86400000)), sdf.format(new Date(lTime - (long) 118 * 86400000)),
			sdf.format(new Date(lTime - (long) 119 * 86400000)), sdf.format(new Date(lTime - (long) 120 * 86400000)), sdf.format(new Date(lTime - (long) 121 * 86400000)),
			sdf.format(new Date(lTime - (long) 122 * 86400000)), sdf.format(new Date(lTime - (long) 123 * 86400000)), sdf.format(new Date(lTime - (long) 124 * 86400000)),
			sdf.format(new Date(lTime - (long) 125 * 86400000)), sdf.format(new Date(lTime - (long) 126 * 86400000)), sdf.format(new Date(lTime - (long) 127 * 86400000)),
			sdf.format(new Date(lTime - (long) 128 * 86400000)), sdf.format(new Date(lTime - (long) 129 * 86400000)), sdf.format(new Date(lTime - (long) 130 * 86400000)),
			sdf.format(new Date(lTime - (long) 131 * 86400000)), sdf.format(new Date(lTime - (long) 132 * 86400000)), sdf.format(new Date(lTime - (long) 133 * 86400000)),
			sdf.format(new Date(lTime - (long) 134 * 86400000)), sdf.format(new Date(lTime - (long) 135 * 86400000)), sdf.format(new Date(lTime - (long) 136 * 86400000)),
			sdf.format(new Date(lTime - (long) 137 * 86400000)), sdf.format(new Date(lTime - (long) 138 * 86400000)), sdf.format(new Date(lTime - (long) 139 * 86400000)),
			sdf.format(new Date(lTime - (long) 140 * 86400000)), sdf.format(new Date(lTime - (long) 141 * 86400000)), sdf.format(new Date(lTime - (long) 142 * 86400000)),
			sdf.format(new Date(lTime - (long) 143 * 86400000)), sdf.format(new Date(lTime - (long) 144 * 86400000)), sdf.format(new Date(lTime - (long) 145 * 86400000)),
			sdf.format(new Date(lTime - (long) 146 * 86400000)), sdf.format(new Date(lTime - (long) 147 * 86400000)), sdf.format(new Date(lTime - (long) 148 * 86400000)),
			sdf.format(new Date(lTime - (long) 149 * 86400000)), sdf.format(new Date(lTime - (long) 150 * 86400000)), sdf.format(new Date(lTime - (long) 151 * 86400000)),
			sdf.format(new Date(lTime - (long) 152 * 86400000)), sdf.format(new Date(lTime - (long) 153 * 86400000)), sdf.format(new Date(lTime - (long) 154 * 86400000)),
			sdf.format(new Date(lTime - (long) 155 * 86400000)), sdf.format(new Date(lTime - (long) 156 * 86400000)), sdf.format(new Date(lTime - (long) 157 * 86400000)),
			sdf.format(new Date(lTime - (long) 158 * 86400000)), sdf.format(new Date(lTime - (long) 159 * 86400000)), sdf.format(new Date(lTime - (long) 160 * 86400000)),
			sdf.format(new Date(lTime - (long) 161 * 86400000)), sdf.format(new Date(lTime - (long) 162 * 86400000)), sdf.format(new Date(lTime - (long) 163 * 86400000)),
			sdf.format(new Date(lTime - (long) 164 * 86400000)), sdf.format(new Date(lTime - (long) 165 * 86400000)), sdf.format(new Date(lTime - (long) 166 * 86400000)),
			sdf.format(new Date(lTime - (long) 167 * 86400000)), sdf.format(new Date(lTime - (long) 168 * 86400000)), sdf.format(new Date(lTime - (long) 169 * 86400000)),
			sdf.format(new Date(lTime - (long) 170 * 86400000)), sdf.format(new Date(lTime - (long) 171 * 86400000)), sdf.format(new Date(lTime - (long) 172 * 86400000)),
			sdf.format(new Date(lTime - (long) 173 * 86400000)), sdf.format(new Date(lTime - (long) 174 * 86400000)), sdf.format(new Date(lTime - (long) 175 * 86400000)),
			sdf.format(new Date(lTime - (long) 176 * 86400000)), sdf.format(new Date(lTime - (long) 177 * 86400000)), sdf.format(new Date(lTime - (long) 178 * 86400000)),
			sdf.format(new Date(lTime - (long) 179 * 86400000)), sdf.format(new Date(lTime - (long) 180 * 86400000)), sdf.format(new Date(lTime - (long) 181 * 86400000)),
			sdf.format(new Date(lTime - (long) 182 * 86400000)), sdf.format(new Date(lTime - (long) 183 * 86400000)), sdf.format(new Date(lTime - (long) 184 * 86400000)),
			sdf.format(new Date(lTime - (long) 185 * 86400000)), sdf.format(new Date(lTime - (long) 186 * 86400000)), sdf.format(new Date(lTime - (long) 187 * 86400000)),
			sdf.format(new Date(lTime - (long) 188 * 86400000)), sdf.format(new Date(lTime - (long) 189 * 86400000)), sdf.format(new Date(lTime - (long) 190 * 86400000)),
			sdf.format(new Date(lTime - (long) 191 * 86400000)), sdf.format(new Date(lTime - (long) 192 * 86400000)), sdf.format(new Date(lTime - (long) 193 * 86400000)),
			sdf.format(new Date(lTime - (long) 194 * 86400000)), sdf.format(new Date(lTime - (long) 195 * 86400000)), sdf.format(new Date(lTime - (long) 196 * 86400000)),
			sdf.format(new Date(lTime - (long) 197 * 86400000)), sdf.format(new Date(lTime - (long) 198 * 86400000)), sdf.format(new Date(lTime - (long) 199 * 86400000)),
			sdf.format(new Date(lTime - (long) 200 * 86400000)), sdf.format(new Date(lTime - (long) 201 * 86400000)), sdf.format(new Date(lTime - (long) 202 * 86400000)),
			sdf.format(new Date(lTime - (long) 203 * 86400000)), sdf.format(new Date(lTime - (long) 204 * 86400000)), sdf.format(new Date(lTime - (long) 205 * 86400000)),
			sdf.format(new Date(lTime - (long) 206 * 86400000)), sdf.format(new Date(lTime - (long) 207 * 86400000)), sdf.format(new Date(lTime - (long) 208 * 86400000)),
			sdf.format(new Date(lTime - (long) 209 * 86400000)), sdf.format(new Date(lTime - (long) 210 * 86400000)), sdf.format(new Date(lTime - (long) 211 * 86400000)),
			sdf.format(new Date(lTime - (long) 212 * 86400000)), sdf.format(new Date(lTime - (long) 213 * 86400000)), sdf.format(new Date(lTime - (long) 214 * 86400000)),
			sdf.format(new Date(lTime - (long) 215 * 86400000)), sdf.format(new Date(lTime - (long) 216 * 86400000)), sdf.format(new Date(lTime - (long) 217 * 86400000)),
			sdf.format(new Date(lTime - (long) 218 * 86400000)), sdf.format(new Date(lTime - (long) 219 * 86400000)), sdf.format(new Date(lTime - (long) 220 * 86400000)),
			sdf.format(new Date(lTime - (long) 221 * 86400000)), sdf.format(new Date(lTime - (long) 222 * 86400000)), sdf.format(new Date(lTime - (long) 223 * 86400000)),
			sdf.format(new Date(lTime - (long) 224 * 86400000)), sdf.format(new Date(lTime - (long) 225 * 86400000)), sdf.format(new Date(lTime - (long) 226 * 86400000)),
			sdf.format(new Date(lTime - (long) 227 * 86400000)), sdf.format(new Date(lTime - (long) 228 * 86400000)), sdf.format(new Date(lTime - (long) 229 * 86400000)),
			sdf.format(new Date(lTime - (long) 230 * 86400000)), sdf.format(new Date(lTime - (long) 231 * 86400000)), sdf.format(new Date(lTime - (long) 232 * 86400000)),
			sdf.format(new Date(lTime - (long) 233 * 86400000)), sdf.format(new Date(lTime - (long) 234 * 86400000)), sdf.format(new Date(lTime - (long) 235 * 86400000)),
			sdf.format(new Date(lTime - (long) 236 * 86400000)), sdf.format(new Date(lTime - (long) 237 * 86400000)), sdf.format(new Date(lTime - (long) 238 * 86400000)),
			sdf.format(new Date(lTime - (long) 239 * 86400000)), sdf.format(new Date(lTime - (long) 240 * 86400000)), sdf.format(new Date(lTime - (long) 241 * 86400000)),
			sdf.format(new Date(lTime - (long) 242 * 86400000)), sdf.format(new Date(lTime - (long) 243 * 86400000)), sdf.format(new Date(lTime - (long) 244 * 86400000)),
			sdf.format(new Date(lTime - (long) 245 * 86400000)), sdf.format(new Date(lTime - (long) 246 * 86400000)), sdf.format(new Date(lTime - (long) 247 * 86400000)),
			sdf.format(new Date(lTime - (long) 248 * 86400000)), sdf.format(new Date(lTime - (long) 249 * 86400000)), sdf.format(new Date(lTime - (long) 250 * 86400000)),
			sdf.format(new Date(lTime - (long) 251 * 86400000)), sdf.format(new Date(lTime - (long) 252 * 86400000)), sdf.format(new Date(lTime - (long) 253 * 86400000)),
			sdf.format(new Date(lTime - (long) 254 * 86400000)), sdf.format(new Date(lTime - (long) 255 * 86400000)), sdf.format(new Date(lTime - (long) 256 * 86400000)),
			sdf.format(new Date(lTime - (long) 257 * 86400000)), sdf.format(new Date(lTime - (long) 258 * 86400000)), sdf.format(new Date(lTime - (long) 259 * 86400000)),
			sdf.format(new Date(lTime - (long) 260 * 86400000)), sdf.format(new Date(lTime - (long) 261 * 86400000)), sdf.format(new Date(lTime - (long) 262 * 86400000)),
			sdf.format(new Date(lTime - (long) 263 * 86400000)), sdf.format(new Date(lTime - (long) 264 * 86400000)), sdf.format(new Date(lTime - (long) 265 * 86400000)),
			sdf.format(new Date(lTime - (long) 266 * 86400000)), sdf.format(new Date(lTime - (long) 267 * 86400000)), sdf.format(new Date(lTime - (long) 268 * 86400000)),
			sdf.format(new Date(lTime - (long) 269 * 86400000)), sdf.format(new Date(lTime - (long) 270 * 86400000)), sdf.format(new Date(lTime - (long) 271 * 86400000)),
			sdf.format(new Date(lTime - (long) 272 * 86400000)), sdf.format(new Date(lTime - (long) 273 * 86400000)), sdf.format(new Date(lTime - (long) 274 * 86400000)),
			sdf.format(new Date(lTime - (long) 275 * 86400000)), sdf.format(new Date(lTime - (long) 276 * 86400000)), sdf.format(new Date(lTime - (long) 277 * 86400000)),
			sdf.format(new Date(lTime - (long) 278 * 86400000)), sdf.format(new Date(lTime - (long) 279 * 86400000)), sdf.format(new Date(lTime - (long) 280 * 86400000)),
			sdf.format(new Date(lTime - (long) 281 * 86400000)), sdf.format(new Date(lTime - (long) 282 * 86400000)), sdf.format(new Date(lTime - (long) 283 * 86400000)),
			sdf.format(new Date(lTime - (long) 284 * 86400000)), sdf.format(new Date(lTime - (long) 285 * 86400000)), sdf.format(new Date(lTime - (long) 286 * 86400000)),
			sdf.format(new Date(lTime - (long) 287 * 86400000)), sdf.format(new Date(lTime - (long) 288 * 86400000)), sdf.format(new Date(lTime - (long) 289 * 86400000)),
			sdf.format(new Date(lTime - (long) 290 * 86400000)), sdf.format(new Date(lTime - (long) 291 * 86400000)), sdf.format(new Date(lTime - (long) 292 * 86400000)),
			sdf.format(new Date(lTime - (long) 293 * 86400000)), sdf.format(new Date(lTime - (long) 294 * 86400000)), sdf.format(new Date(lTime - (long) 295 * 86400000)),
			sdf.format(new Date(lTime - (long) 296 * 86400000)), sdf.format(new Date(lTime - (long) 297 * 86400000)), sdf.format(new Date(lTime - (long) 298 * 86400000)),
			sdf.format(new Date(lTime - (long) 299 * 86400000)), sdf.format(new Date(lTime - (long) 300 * 86400000)), sdf.format(new Date(lTime - (long) 301 * 86400000)),
			sdf.format(new Date(lTime - (long) 302 * 86400000)), sdf.format(new Date(lTime - (long) 303 * 86400000)), sdf.format(new Date(lTime - (long) 304 * 86400000)),
			sdf.format(new Date(lTime - (long) 305 * 86400000)), sdf.format(new Date(lTime - (long) 306 * 86400000)), sdf.format(new Date(lTime - (long) 307 * 86400000)),
			sdf.format(new Date(lTime - (long) 308 * 86400000)), sdf.format(new Date(lTime - (long) 309 * 86400000)), sdf.format(new Date(lTime - (long) 310 * 86400000)),
			sdf.format(new Date(lTime - (long) 311 * 86400000)), sdf.format(new Date(lTime - (long) 312 * 86400000)), sdf.format(new Date(lTime - (long) 313 * 86400000)),
			sdf.format(new Date(lTime - (long) 314 * 86400000)), sdf.format(new Date(lTime - (long) 315 * 86400000)), sdf.format(new Date(lTime - (long) 316 * 86400000)),
			sdf.format(new Date(lTime - (long) 317 * 86400000)), sdf.format(new Date(lTime - (long) 318 * 86400000)), sdf.format(new Date(lTime - (long) 319 * 86400000)),
			sdf.format(new Date(lTime - (long) 320 * 86400000)), sdf.format(new Date(lTime - (long) 321 * 86400000)), sdf.format(new Date(lTime - (long) 322 * 86400000)),
			sdf.format(new Date(lTime - (long) 323 * 86400000)), sdf.format(new Date(lTime - (long) 324 * 86400000)), sdf.format(new Date(lTime - (long) 325 * 86400000)),
			sdf.format(new Date(lTime - (long) 326 * 86400000)), sdf.format(new Date(lTime - (long) 327 * 86400000)), sdf.format(new Date(lTime - (long) 328 * 86400000)),
			sdf.format(new Date(lTime - (long) 329 * 86400000)), sdf.format(new Date(lTime - (long) 330 * 86400000)), sdf.format(new Date(lTime - (long) 331 * 86400000)),
			sdf.format(new Date(lTime - (long) 332 * 86400000)), sdf.format(new Date(lTime - (long) 333 * 86400000)), sdf.format(new Date(lTime - (long) 334 * 86400000)),
			sdf.format(new Date(lTime - (long) 335 * 86400000)), sdf.format(new Date(lTime - (long) 336 * 86400000)), sdf.format(new Date(lTime - (long) 337 * 86400000)),
			sdf.format(new Date(lTime - (long) 338 * 86400000)), sdf.format(new Date(lTime - (long) 339 * 86400000)), sdf.format(new Date(lTime - (long) 340 * 86400000)),
			sdf.format(new Date(lTime - (long) 341 * 86400000)), sdf.format(new Date(lTime - (long) 342 * 86400000)), sdf.format(new Date(lTime - (long) 343 * 86400000)),
			sdf.format(new Date(lTime - (long) 344 * 86400000)), sdf.format(new Date(lTime - (long) 345 * 86400000)), sdf.format(new Date(lTime - (long) 346 * 86400000)),
			sdf.format(new Date(lTime - (long) 347 * 86400000)), sdf.format(new Date(lTime - (long) 348 * 86400000)), sdf.format(new Date(lTime - (long) 349 * 86400000)),
			sdf.format(new Date(lTime - (long) 350 * 86400000)), sdf.format(new Date(lTime - (long) 351 * 86400000)), sdf.format(new Date(lTime - (long) 352 * 86400000)),
			sdf.format(new Date(lTime - (long) 353 * 86400000)), sdf.format(new Date(lTime - (long) 354 * 86400000)), sdf.format(new Date(lTime - (long) 355 * 86400000)),
			sdf.format(new Date(lTime - (long) 356 * 86400000)), sdf.format(new Date(lTime - (long) 357 * 86400000)), sdf.format(new Date(lTime - (long) 358 * 86400000)),
			sdf.format(new Date(lTime - (long) 359 * 86400000)), sdf.format(new Date(lTime - (long) 360 * 86400000)), sdf.format(new Date(lTime - (long) 361 * 86400000)),
			sdf.format(new Date(lTime - (long) 362 * 86400000)), sdf.format(new Date(lTime - (long) 363 * 86400000)), sdf.format(new Date(lTime - (long) 364 * 86400000)),
			sdf.format(new Date(lTime - (long) 365 * 86400000)) };
}