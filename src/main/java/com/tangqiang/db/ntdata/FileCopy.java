package com.tangqiang.db.ntdata;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * sql文件复制
 * 
 * @author tqiang
 * @email tqiang@grgbanking.com
 * @file com.tangqiang.db.ntdata.FileCopy.java
 * @date 2014-7-21 上午9:53:28
 * 
 * @version 1.0 2014-7-21 tqiang create
 * 
 * @copyright Copyright © 2011-2014 广电运通 All rights reserved.
 */
public class FileCopy {
	private Logger logger = LoggerFactory.getLogger(FileCopy.class);
	
	public static void main(String[] args) {
		FileCopy fc = new FileCopy();
		for (int i = 1; i < 50; i++) {
			fc.doFileCopyByReader("E:\\nt_sent_data.sql", "E:\\nt_sent_"+i+".sql", "_0", "_"+i);
		}
	}
	
	/**
	 * 使用FileReader\FileWriter读取写入文件
	 * @param sFileSource
	 * @param sFileDest
	 * @param sOldTable
	 * @param sNewTable
	 */
	private void doFileCopyByReader(String sFileSource, String sFileDest,String sOldTable,String sNewTable) {
		logger.info("开始从文件[" + sFileSource + "]复制内容到文件[" + sFileDest + "]");
		int iCount = 0;
		long lBeginTime = System.currentTimeMillis();
		File fileSource = new File(sFileSource);
		File fileDest = new File(sFileDest);
		try {
			BufferedReader reader = new BufferedReader(new FileReader(fileSource));
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileDest));
			String line = null;
			while ((line=reader.readLine()) != null) {
				line = line.replaceAll(sOldTable, sNewTable);
				writer.write(line);
				iCount = iCount + 1 ;
			}
			writer.flush();
			reader.close();
			writer.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		long lEndTime = System.currentTimeMillis();
		logger.info("文件复制完成!复制行数:"+ iCount +" 耗时:"+(lEndTime - lBeginTime )+"/ms");
	}
}
