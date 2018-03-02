package com.tangqiang.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileReadTest {
	private Logger logger = LoggerFactory.getLogger(getClass());

	public static void main(String[] args) {

		System.out.println("\0\0\0\0\0");

		new FileReadTest().read();
	}

	public void read() {
		try {
			String fileUrl = "E:\\books\\yinianyongheng.txt";
			logger.info("Read File==" + fileUrl);
			File file = new File(fileUrl);
			FileInputStream fin = new FileInputStream(file);
			String encoding = "UTF-8";
			InputStreamReader read = new InputStreamReader(fin, encoding);// 考虑到编码格式
			BufferedReader bufferedReader = new BufferedReader(read);
			String lineTxt = null;
			while ((lineTxt = bufferedReader.readLine()) != null) {
				logger.info("1-" + lineTxt.replaceAll("\0", ""));
				// char[] chars = lineTxt.toCharArray();
				// String ll = "";
				// for (char c : chars) {
				// ll += (int) c + " ";
				// }
				// logger.info("11-" + ll);
				logger.info("2-" + new String(lineTxt.getBytes("UTF-8"), "Unicode"));
				logger.info("3-" + new String(lineTxt.getBytes("Unicode"), "UTF-8"));
			}
			bufferedReader.close();
			read.close();
		} catch (Exception e) {
			logger.error("Read error !", e);
		}
	}

}
