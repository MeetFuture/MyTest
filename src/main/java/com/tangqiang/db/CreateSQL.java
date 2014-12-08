package com.tangqiang.db;

public class CreateSQL {
	
	public void testCreateNtSentSQL(){
		String sourceSQL = "CREATE TABLE NT_SENT_1  (   C_ID VARCHAR(32) NOT NULL, C_NT_TXN_ID VARCHAR(32) NOT NULL, C_SERIAL_NO VARCHAR(10) NOT NULL, C_CASH_FLAG CHAR(2), C_CASH_TYPE CHAR(2), C_CASH_CURRENCY VARCHAR(10), C_CASH_BOX_ID VARCHAR(5),C_TRANS_DATE VARCHAR(8) NOT NULL,C_TRANS_TIME VARCHAR(6) NOT NULL,  CONSTRAINT PK_NT_SENT_1 PRIMARY KEY (C_ID) ) ENGINE=InnoDB DEFAULT CHARSET=utf8;create index INDEX_NT_SENT_1 on NT_SENT_1  (C_NT_TXN_ID);";
		for(int i=0;i<100;i++){
			String sysOutSQL = sourceSQL.replaceAll("NT_SENT_1", "NT_SENT_"+i);
			System.out.println(sysOutSQL);
		}
	}
	
	public void testCreateNtCwdSQL(){
		String sourceSQL = "CREATE TABLE NT_TXN_CWD_1 ( C_ID VARCHAR(32) NOT NULL,C_TRANS_CODE VARCHAR(3) NOT NULL,C_TERMID VARCHAR(32) NOT NULL, C_TRANS_DATE VARCHAR(8) NOT NULL,C_TRANS_TIME VARCHAR(6) NOT NULL,C_ACCOUNT_NO VARCHAR(32) NOT NULL,C_C_JOURNAL_NO VARCHAR(32), C_P_JOURNAL_NO VARCHAR(32),I_TRANS_AMOUNT  decimal(20,0),C_TRANS_RESULT CHAR(1),I_TRANS_COUNT  decimal(20,0),I_RETRACT_COUNT decimal(20,0), CONSTRAINT PK_NT_TXN_CWD_1 PRIMARY KEY (C_ID)) ENGINE=InnoDB DEFAULT CHARSET=utf8;";
		for(int i=0;i<100;i++){
			String sysOutSQL = sourceSQL.replaceAll("NT_TXN_CWD_1", "NT_TXN_CWD_"+i);
			System.out.println(sysOutSQL);
			
			String sysDepOutSQL = sourceSQL.replaceAll("NT_TXN_CWD_1", "NT_TXN_DEP_"+i);
            System.out.println(sysDepOutSQL);
		}
	}
	
	public static void main(String[] args) {
		CreateSQL createSQL = new CreateSQL();
		createSQL.testCreateNtSentSQL();
//		createSQL.testCreateNtCwdSQL();
	}

}
