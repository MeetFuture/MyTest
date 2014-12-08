package com.tangqiang.grgbanking.test.ocrmina;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.log4j.Logger;

/**
 * 冠字号交易报文生成
 * 
 * @author tqiang
 * @email tom617288330@gmail.com
 * @date 2014-8-6 上午10:24:58
 * 
 * @version 1.0 2014-8-6 tqiang create
 * 
 */
public class OcrMsgBuild extends Thread {
	private static Logger logger = Logger.getLogger(OcrMsgBuild.class);
	private char US = (char) 0x1F;

	private static String[] sArrTransCode = new String[] { "111", "102", "112", "123", "520", "103", "101" };
	private static String[] sArrTermCode = new String[] { "000000001", "000000002", "000000003", "000000004", "000000005", "000000006", "000000007", "000000008", "000000009", "000000010", "000000011",
			"000000012", "000000013", "000000014", "000000015", "000000016", "000000017", "000000018", "000000019", "000000020", "000000021", "000000022" };
	private static String[] sArrDay = new String[] { new SimpleDateFormat("yyyyMMdd").format(new Date()), new SimpleDateFormat("yyyyMMdd").format(new Date(System.currentTimeMillis() - 86400000)),
			new SimpleDateFormat("yyyyMMdd").format(new Date(System.currentTimeMillis() - 2 * 86400000)), new SimpleDateFormat("yyyyMMdd").format(new Date(System.currentTimeMillis() - 3 * 86400000)),
			new SimpleDateFormat("yyyyMMdd").format(new Date(System.currentTimeMillis() - 4 * 86400000)), new SimpleDateFormat("yyyyMMdd").format(new Date(System.currentTimeMillis() - 10 * 86400000)) };
	private static String[] sArrCardNo = new String[] { "4564561000165431", "56412322031210002", "4408511023520312", "3336545462010235", "4400005125434620", "4431201251254343", "53201185125431310" };
	private static String[] sArrAccountName = new String[] { "Xiangqing", "Fengyun", "Aron", "Curise", "Darlin", "小尹", "FoxReader", "Tom", "Josn", "Limei" };
	private static String[] sArrPhoneNo = new String[] { "15017585421", "15117541234", "13865421892", "13365492546", "13202135654", "13765231124", "15236231254" };
	private static String[] sArrSerialNo = new String[] { "A012365489", "VTM1645238", "CDMN985462", "CRSE8795132", "DASL896423", "AMLI695134", "FSED9861354", "MOTO988461", "JOSN741302", "LMGK232015",
			"AQ12368489", "VTM1632238", "CDMN785462", "CRSE8235132", "DASL835223", "AMLI698134", "FSED9871354", "MOTO918461", "JOSN741682", "LMGK232515", "A012366489", "VTM1645223", "CDMN985445",
			"CRSE8795332", "DASL846423", "AMLI695136", "FSED9351354", "MOTO982461", "JOSN749302", "LMGK243215", "A012365989", "VTM1612238", "CDMN987562", "CRSE8995132", "DASL196423", "AMLI695834",
			"FGGD9864354", "MOTO088861", "JOSN743402", "LMGK232115", "A012365849", "VTM1645286", "CDMN935462", "CRSE8796132", "DASL836423", "AMLI693134", "FSED1261354", "MOTO988361", "JOSN771302",
			"LMGK132015" };

	public static void main(String[] args) {
		
		System.out.println(new OcrMsgBuild().buildOcrMsg());
	}

	public String buildOcrMsg() {
		String msgFlag = "10008";
		String transCode = getRandom(sArrTransCode);
		String termCode = getRandom(sArrTermCode);
		String sDateTime = getRandom(sArrDay) + new SimpleDateFormat("HHmmss").format(new Date());
		String sTime = "" + System.currentTimeMillis();
		String cJournalNo = sTime.substring(sTime.length() - 4);
		String pJournalNo = sTime.substring(sTime.length() - 8);
		int billCount = (int) (Math.random() * 49) +1;
		int transAmount = 100 * billCount;
		int RetractCount = 0;
		String sTransResult = "0";
		String sAccount = getRandom(sArrCardNo);
		String sUserName = getRandom(sArrAccountName);
		String sPhone = getRandom(sArrPhoneNo);

		StringBuilder sbMsg = new StringBuilder();
		sbMsg.append(transCode).append(US).append(termCode).append(US).append(sDateTime).append(US).append(sAccount).append(US);
		sbMsg.append(cJournalNo).append(US).append(pJournalNo).append(US).append(transAmount).append(US).append(sTransResult).append(US);
		sbMsg.append(billCount).append(US).append(RetractCount).append(US).append(sAccount).append(US).append(sUserName).append(US).append(sPhone).append(US);

		for (int i = 0; i < billCount; i++) {
			sbMsg.append(getRandom(sArrSerialNo)).append(":").append("0").append(":").append("CNY").append(":").append("100").append(":").append(i % 4).append(":").append(i % 3).append(":")
					.append(i % 4).append("$");
		}
		int iLength = sbMsg.length() + 2;
		return msgFlag + US + iLength + US + sbMsg.toString();
	}

	// 报文长度"       name="MsgLen"                mapParam=""                      form="a" />
	// <field index="3" alias="交易类型" name="TxnCode" mapParam="TxnCode" form="an"/>
	// <field index="4" alias="终端设备号" name="TmlID" mapParam="TmlID" form="an"/>
	// <field index="5" alias="交易时间" name="TxnTime" mapParam="TxnTime" form="YYYYMMDDhhmmss" fixedLen="14"/>
	// <field index="6" alias="交易卡号" name="TxnCard" mapParam="TxnCard" form="ans"/>
	// <field index="7" alias="主机流水号" name="HostSequenceNum" mapParam="HostSequenceNum" form="an"/>
	// <field index="8" alias="机端流水号 " name="TmlSN" mapParam="TmlSN" form="an"/>
	// <field index="9" alias="交易金额" name="Amount" mapParam="Amount" form="n" >
	// <cast method="String2Int">
	// <param type="java.lang.String" thisRef="true"></param>
	// <param type="int">10</param>
	// </cast>
	// </field>
	// <field index="10" alias="交易结果" name="TxnResult" mapParam="TxnResult" form="ans"/>
	// <field index="11" alias="交易张数" name="BillCount" mapParam="BillCount" form="n" >
	// <cast method="String2Int">
	// <param type="java.lang.String" thisRef="true"></param>
	// <param type="int">10</param>
	// </cast>
	// </field>
	// <field index="12" alias="回收张数" name="RetractCount" mapParam="RetractCount" form="n" >
	// <cast method="String2Int">
	// <param type="java.lang.String" thisRef="true"></param>
	// <param type="int">10</param>
	// </cast>
	// </field>
	// <field index="13" alias="交易帐号" name="TxnAccounts" mapParam="TxnAccounts" form="ans"/>
	// <field index="14" alias="交易用户名" name="TxnUserName" mapParam="TxnUserName" form="ans"/>
	// <field index="15" alias="交易用户手机" name="TxnUserPhone" mapParam="TxnUserPhone" form="n"/>
	// <field index="16" alias="冠字号信息组" name="BillInfoArray" mapParam="BillInfoArray" form="@ARRAY" delimiter="$">
	// <field alias="冠字号信息" name="BillInfo" form="@MAP" delimiter=":">
	// <field alias="冠字号" name="No" mapParam="No" form="an"/>
	// <field alias="正常标识" name="Normal" mapParam="Normal" form="n">
	// <cast method="String2Int">
	// <param type="java.lang.String" thisRef="true"></param>
	// <param type="int">10</param>
	// </cast>
	// </field>
	// <field alias="币种" name="Currency" mapParam="Currency" form="an"/>
	// <field alias="面额" name="Value" mapParam="Value" form="n">
	// <cast method="String2Int">
	// <param type="java.lang.String" thisRef="true"></param>
	// <param type="int">10</param>
	// </cast>
	// </field>
	// <field alias="钞票识别" name="Verify" mapParam="Verify" form="n">
	// <cast method="String2Int">
	// <param type="java.lang.String" thisRef="true"></param>
	// <param type="int">10</param>
	// </cast>
	// </field>
	// <field alias="钞票标识" name="Flag" mapParam="Flag" form="n">
	// <cast method="String2Int">
	// <param type="java.lang.String" thisRef="true"></param>
	// <param type="int">10</param>
	// </cast>
	// </field>
	// <field alias="钞箱逻辑序号" name="BoxNo" mapParam="BoxNo" form="n">
	// <cast method="String2Int">
	// <param type="java.lang.String" thisRef="true"></param>
	// <param type="int">10</param>
	// </cast>
	// </field>

	private String getRandom(String[] sArrData) {
		int iLocation = (int) (sArrData.length * Math.random());
		return sArrData[iLocation];
	}

}
