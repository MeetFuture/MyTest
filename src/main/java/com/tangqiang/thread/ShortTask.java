package com.tangqiang.thread;

import java.util.Random;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 随机生成6个字符的字符串
 * 
 * @author tqiang
 * @email tqiang@grgbanking.com
 * @file com.tangqiang.thread.RandomFlagTask.java
 * @date 2014-7-23 上午11:39:28
 * 
 * @version 1.0 2014-7-23 tqiang create
 * 
 * @copyright Copyright © 2011-2014 广电运通 All rights reserved.
 */
public class ShortTask implements Callable<String> {
	private Logger logger = LoggerFactory.getLogger(getClass());

	@Override
	public String call() throws Exception {
		long lBeginTime = System.currentTimeMillis();
		TimeUnit.MILLISECONDS.sleep(100);
		Lock lock = new ReentrantLock();
		
		String base = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < 6; i++) {
			int number = random.nextInt(base.length());
			sb.append(base.charAt(number));
		}
		String result = sb.toString();
		long lEndTime = System.currentTimeMillis();
		logger.info("Random Flag : " + result + " Time:" + (lEndTime - lBeginTime) + "/ms");
		return result;
	}
}
