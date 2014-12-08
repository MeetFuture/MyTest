package com.tangqiang.thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

/**
 * 线程池测试
 * 
 * @author tqiang
 * @email tqiang@grgbanking.com
 * @file com.tangqiang.thread.ThreadPoolTest.java
 * @date 2014-7-23 上午10:33:07
 * 
 * @version 1.0 2014-7-23 tqiang create
 * 
 * @copyright Copyright © 2011-2014 广电运通 All rights reserved.
 */
public class ThreadPoolTest {
	private Logger logger = Logger.getLogger(getClass());

	public static void main(String[] args) {
		ThreadPoolTest tpt = new ThreadPoolTest();
		tpt.runTest();
	}

	private void runTest() {
		try {
			/**
			 * 使用线程池进行线程管理。
			 */
			logger.info("获取线程池服务!");
			//ExecutorService es = Executors.newCachedThreadPool();
			//ExecutorService es = Executors.newSingleThreadExecutor();
			ExecutorService es = Executors.newScheduledThreadPool(1);
			
			/**
			 * 使用计数栅栏
			 */
			CountDownLatch doneSignal = new CountDownLatch(3);
			logger.info(" getCount : " + doneSignal.getCount());
			es.submit(new MyThread(doneSignal));
			es.submit(new MyThread(doneSignal));
			es.submit(new ShortTask());

			/**
			 * 使用CountDownLatch的await方法，等待所有线程完成sheet操作
			 */
			doneSignal.await();
			logger.info(" getCount : " + doneSignal.getCount());
			logger.info("所有的线程运行完成! Time:" + System.currentTimeMillis());
			es.shutdown();
		} catch (Exception e) {
			logger.error("执行测试出现异常!", e);
		}
	}

	private class MyThread extends Thread {
		private CountDownLatch latch;

		public MyThread(CountDownLatch latch) {
			this.latch = latch;
		}

		public void run() {
			try {
				logger.info(getName() + " I'm begin !  Time:" + System.currentTimeMillis());
				Thread.sleep(5000);
				logger.info(getName() + " I'm done !  Time:" + System.currentTimeMillis());
			} catch (Exception e) {
				logger.error("线程异常!", e);
			} finally {
				latch.countDown();
			}
		}
	}
}
