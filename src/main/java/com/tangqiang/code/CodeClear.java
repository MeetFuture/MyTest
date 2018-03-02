package com.tangqiang.code;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CodeClear {
	private Logger logger = LoggerFactory.getLogger(getClass());

	public static void main(String[] args) {
		String path = "D:\\WorkspaceHomeWork\\my-web-static\\src\\site";
		File file = new File(path);
		new CodeClear().execute(file);
	}

	public void execute(File file) {
		if (file.isDirectory()) {
			File[] files = file.listFiles();
			for (File file2 : files) {
				execute(file2);
			}
		} else {
			clearFile(file);
		}
	}

	private void clearFile(File file) {
		try {
			FileInputStream fin = new FileInputStream(file);
			String encoding = "UTF-8";
			InputStreamReader read = new InputStreamReader(fin, encoding);
			BufferedReader bufferedReader = new BufferedReader(read);
			String lineTxt = null;
			while ((lineTxt = bufferedReader.readLine()) != null) {
				if (lineTxt.contains("tppabs")) {
					logger.info(lineTxt);
				}
			}
			bufferedReader.close();
			read.close();
		} catch (Exception e) {
			logger.error("Read error !", e);
		}
	}
}
