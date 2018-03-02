package com.tangqiang.file;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FileReadBooks {
	private Logger logger = LoggerFactory.getLogger(getClass());

	public static void main(String[] args) {
		new FileReadBooks().read("E:\\books\\yinianyongheng.txt");
	}

	public void read(String fileUrl) {
		try {
			logger.info("FileReadBooks Read File:" + fileUrl);
			File file = new File(fileUrl);
			FileInputStream fin = new FileInputStream(file);
			InputStreamReader read = new InputStreamReader(fin, "GBK");// 考虑到编码格式
			BufferedReader bufferedReader = new BufferedReader(read);
			BufferedReader bufferedReaderSystemIn = new BufferedReader(new InputStreamReader(System.in));

			long skip = getSkip(file);
			if (skip > 0) {
				logger.info("bufferedReader.skip " + skip + " size .......");
				bufferedReader.skip(skip);
			}
			int iRead = 0;
			int iReadA = 20;
			String lineTxt = null;
			String strIn = "";
			while ((lineTxt = bufferedReader.readLine()) != null) {
				skip += lineTxt.length() + 2;
				iRead++;
				if (lineTxt.trim().length() > 0) {
					printLine(lineTxt);
					writeSkip(file, skip);
					if (strIn != null && strIn.length() > 1) {
						if (lineTxt.contains(strIn)) {
							strIn = null;
						} else {
							continue;
						}
					}
					if (iRead > iReadA) {
						iRead = 0;
						strIn = bufferedReaderSystemIn.readLine();

						System.out.println(new Date() + " | ");
						System.out.println(new Date() + " | ");
						System.out.println(new Date() + " | ");
						System.out.println(new Date() + " | ");
					}
				}
			}
			bufferedReader.close();
			read.close();
		} catch (Exception e) {
			logger.error("Read error !", e);
		}
	}

	private void printLine(String line) {
		int lineLen = 40;
		int count = line.length() / lineLen + 1;
		for (int i = 0; i < count; i++) {
			String s = new Date() + " | " + line.substring(i * lineLen, (i + 1) * lineLen > line.length() ? line.length() : (i + 1) * lineLen);
			System.out.println(s);
			// System.out.println(i == (count - 1) ? s : s + "\n");
		}
	}

	private void writeSkip(File file, long skip) {
		try {
			File fileSkip = new File(file.getAbsolutePath() + ".skip");
			FileWriter fw = new FileWriter(fileSkip);
			fw.write("" + skip);
			fw.flush();
			fw.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private long getSkip(File file) {
		long skip = 0;
		try {
			File fileSkip = new File(file.getAbsolutePath() + ".skip");
			FileReader fr = new FileReader(fileSkip);
			char[] cbuf = new char[100];
			fr.read(cbuf);
			String skips = new String(cbuf);
			skip = Long.valueOf(skips.trim());
			fr.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return skip;
	}
}
