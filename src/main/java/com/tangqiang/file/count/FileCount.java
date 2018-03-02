package com.tangqiang.file.count;

import java.io.File;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 统计电脑文件数量
 *
 * @author Tom
 * @date 2017年5月8日 下午4:30:29
 *
 * @version 1.0 2017年5月8日 Tom create
 * 
 * @copyright Copyright © 2017 广电运通 All rights reserved.
 */
public class FileCount {
	private Logger logger = LoggerFactory.getLogger(getClass());

	public static void main(String[] args) {
		FileCount fileCount = new FileCount();
		fileCount.execute();
	}

	public void execute() {
		try {
			Thread.sleep(5000);
			logger.info("Begin execute ......");
			long startTime = System.currentTimeMillis();

			File[] listRoot = new File("D:\\").listFiles();// File.listRoots();

			CountDownLatch latch = new CountDownLatch(listRoot.length);
			AtomicInteger atomicInteger = new AtomicInteger();
			Semaphore semaphore = new Semaphore(200);

			for (File file : listRoot) {
				logger.info("Begin Root file:" + file.getAbsolutePath());
				semaphore.acquire();
				FileCountThread th = new FileCountThread(latch, semaphore, file, 1, atomicInteger);
				th.start();
			}
			latch.await();
			long endTime = System.currentTimeMillis();
			logger.info("End execute . File count is :" + atomicInteger.get() + "  TimeUsed:" + (endTime - startTime) + "/ms");
			System.exit(0);
		} catch (Exception e) {
			logger.error("FileCount execute error !", e);
		}

	}
}
