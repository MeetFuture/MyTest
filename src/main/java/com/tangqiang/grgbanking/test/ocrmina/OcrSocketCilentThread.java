package com.tangqiang.grgbanking.test.ocrmina;

import java.io.PrintWriter;
import java.net.Socket;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

/**
 * 冠字号 socket发送
 * 
 * @author tqiang
 * @email tom617288330@gmail.com
 * @date 2014-8-7 上午11:17:56
 * 
 * @version 1.0 2014-8-7 tqiang create
 * 
 */
public class OcrSocketCilentThread extends Thread {
	private Logger logger = Logger.getLogger(OcrSocketCilentThread.class);

	private String host = "10.1.33.222";

	private int port = 8890 ;

	public void run() {
		while(true){
			long lBeginTime = System.nanoTime();
			try {
				TimeUnit.MILLISECONDS.sleep(500);
				String msg = new OcrMsgBuild().buildOcrMsg();
				Socket socket = new Socket(host, port);
				PrintWriter os = new PrintWriter(socket.getOutputStream());
				os.println(msg);
				os.flush();
				os.close();
				socket.close();
			} catch (Exception e) {
				logger.error("发送报文异常!", e);
			}
			long lEndTime = System.nanoTime();
			logger.info("一个循环结束， 耗时:" + (lEndTime - lBeginTime) / 1000000 + "/ms");
		}
	}
	
	public static void main(String[] args) {
		OcrSocketCilentThread o= new OcrSocketCilentThread();
		o.start();
	}
}
