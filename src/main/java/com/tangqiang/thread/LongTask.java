package com.tangqiang.thread;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

/**
 * 长时间任务测试
 * 
 * @author tqiang
 * @email tqiang@grgbanking.com
 * @file com.tangqiang.thread.LongCall.java
 * @date 2014-7-23 上午11:32:39
 * 
 * @version 1.0 2014-7-23 tqiang create
 * 
 * @copyright Copyright © 2011-2014 广电运通 All rights reserved.
 */
public class LongTask implements Callable<String> {
	private Logger logger = Logger.getLogger(getClass());

	@Override
	public String call() throws Exception {
		long lBeginTime = System.currentTimeMillis();
		TimeUnit.SECONDS.sleep(10);
		String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 10; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		String result = sb.toString();
		long lEndTime = System.currentTimeMillis();
		logger.info("Random Flag : " + result + " Time:" + (lEndTime - lBeginTime) + "/ms");
		return result;
	}

}