package com.tangqiang.grgbanking.test.ocrmina;

import java.util.concurrent.TimeUnit;

/**
 * 冠字号处理测试
 * 
 * @author tqiang
 * @email tom617288330@gmail.com
 * @date 2014-8-6 上午9:24:16
 * 
 * @version 1.0 2014-8-6 tqiang create
 * 
 */
public class OcrTestMain {

	public static void main(String[] args) {
		try {
			for (int i = 0; i < 1; i++) {
				
				//OcrMinaClientThread thread = new OcrMinaClientThread();
				OcrSocketCilentThread thread = new OcrSocketCilentThread();
				thread.start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
