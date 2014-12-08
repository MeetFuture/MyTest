package com.tangqiang.web;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.Semaphore;

import org.apache.log4j.Logger;

/**
 * 从地址循环获取图片
 * 
 * @author tqiang
 * @email tqiang@grgbanking.com
 * @file com.tangqiang.web.CatchImgTest.java
 * @date 2014-7-22 上午9:45:26
 * 
 * @version 1.0 2014-7-22 tqiang create
 * 
 * @copyright Copyright © 2011-2014 广电运通 All rights reserved.
 */
public class CatchImgTest extends Thread {
	private Logger logger = Logger.getLogger(CatchImgTest.class);
	private static int iCountRequest = 0;
	private static long lBeginTime = 0;
	private static long lEndTime = 0;

	public static void main(String[] args) {
		lBeginTime = System.currentTimeMillis();
		for (int i = 0; i < 20; i++) {
			CatchImgTest cit = new CatchImgTest();
			cit.setName("" + i);
			cit.start();
		}
	}

	public void run() {
		try {
			while (true) {
				String sUrl = "http://10.1.93.164:8080/fileserver/imgTest";
				// String sUrl = "http://10.1.93.164:8080/test/20140721/2.jpg";
				getImg(sUrl);
				iCountRequest++;
				if (iCountRequest > 10000) {
					break;
				}
//				Thread.sleep(10);
			}
			lEndTime = System.currentTimeMillis();
		} catch (Exception e) {
			e.printStackTrace();
		}
		logger.info("次数:"+iCountRequest+" 时间:"+(lEndTime-lBeginTime)+"/ms");
	}

	/**
	 * 从地址获取图片数据
	 * @param sUrl
	 */
	private void getImg(String sUrl) {
		try {
			long lBeginTime = System.currentTimeMillis();
			URL url = new URL(sUrl);
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			connection.setConnectTimeout(1000);
			connection.setReadTimeout(5000);
			connection.connect();
			InputStream ins = connection.getInputStream();
			ByteArrayOutputStream bos = new ByteArrayOutputStream();
			int read = -1;
			while ((read = ins.read()) != -1) {
				bos.write(read);
			}
			ins.close();
			bos.close();
			connection.disconnect();
			long lEndTime = System.currentTimeMillis();
			logger.info(this.getName() + "读取图片:[" + sUrl + "]  大小:[" + bos.size() + "] 耗时:" + (lEndTime - lBeginTime) + "/ms");
		} catch (Exception e) {
			logger.error("读取图片异常!", e);
		}

	}
}
