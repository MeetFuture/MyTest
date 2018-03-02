package com.tangqiang.code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 代码行数统计
 *
 * @author Tom
 * @date 2017年8月27日 上午9:08:23
 *
 * @version 1.0 2017年8月27日 Tom create
 * 
 * @copyright Copyright © 2017-???? 广电运通 All rights reserved.
 */
public class CodeCount {
	private Logger logger = LoggerFactory.getLogger(getClass());

	public static void main(String[] args) {
		String path = "D:\\SVNSource\\CashBuildingSystem\\03-Code\\CashBuildingJavaWeb\\src";
		File file = new File(path);
		long count = new CodeCount().execute(file);
		System.out.println("Count over:" + count);
		// D:\SVNSource\CashBuildingSystem\03-Code\CashBuildingConsumer\src\ Count over:122570
		// D:\SVNSource\CashBuildingSystem\03-Code\CashBuildingFlexWeb\flex_src\ Count over:137796
		// D:\SVNSource\CashBuildingSystem\03-Code\CashBuildingJavaWeb\src\ Count over:22865
	}

	public long execute(File file) {
		long count = 0;
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File file2 : files) {
				count += execute(file2);
			}
		} else {
			count += countFile(file);
		}
		return count;
	}

	private Long countFile(File file) {
		long count = 0;
		try {
			FileInputStream fin = new FileInputStream(file);
			String encoding = "UTF-8";
			InputStreamReader read = new InputStreamReader(fin, encoding);
			BufferedReader bufferedReader = new BufferedReader(read);
			String lineTxt = null;
			while ((lineTxt = bufferedReader.readLine()) != null) {
				count++;
			}
			bufferedReader.close();
			read.close();
		} catch (Exception e) {
			logger.error("Read error !", e);
		}
		logger.info("File Count:" + count + "	" + file.getAbsolutePath());
		return count;
	}
}
