package com.tangqiang.file.count;

import java.io.File;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 文件统计线程
 *
 * @author Tom
 * @date 2017年5月8日 下午4:34:04
 *
 * @version 1.0 2017年5月8日 Tom create
 * 
 * @copyright Copyright © 2017 广电运通 All rights reserved.
 */
public class FileCountThread extends Thread {
	private Logger logger = LoggerFactory.getLogger(getClass());
	private CountDownLatch latch;
	private Semaphore semaphore;
	private AtomicInteger atomicInteger;

	private File folder;
	private int currentDepth = 0;

	public FileCountThread(CountDownLatch latch, Semaphore semaphore, File folder, int currentDepth, AtomicInteger atomicInteger) {
		this.setName("FileCountThread-" + folder.getAbsolutePath());
		this.latch = latch;
		this.semaphore = semaphore;
		this.folder = folder;
		this.currentDepth = currentDepth;
		this.atomicInteger = atomicInteger;
	}

	@Override
	public void run() {
		try {
			// logger.info("Start FileCountThread :" + folder.getAbsolutePath());
			AtomicInteger atomicIntegerSub = new AtomicInteger(0);
			File[] files = folder.listFiles();
			if (files != null && files.length != 0) {
				CountDownLatch latchSub = new CountDownLatch(files.length);
				for (File file : files) {
					if (file.isDirectory()) {
						if (currentDepth <= 3 && semaphore.availablePermits() > 0) {
							semaphore.acquire();
							new FileCountThread(latchSub, semaphore, file, currentDepth + 1, atomicIntegerSub).start();
						} else {
							countFolder(file, atomicIntegerSub);
							latchSub.countDown();
						}
					} else {
						atomicIntegerSub.addAndGet(1);
						latchSub.countDown();
					}
				}
				latchSub.await();
			}
			int folderCount = atomicIntegerSub.get();
			int allCount = atomicInteger.addAndGet(folderCount);
			if (currentDepth <= 2) {
				logger.info("End FileCountThread  Count:" + folderCount + "	All:" + allCount);
			}
		} catch (Exception e) {
			logger.error("FileCountThread error!", e);
		} finally {
			latch.countDown();
			semaphore.release();
		}
	}

	private void countFolder(File fileFolder, AtomicInteger atomicIntegerSub) {
		File[] files = fileFolder.listFiles();
		if (files != null && files.length != 0) {
			for (File file : files) {
				if (file.isDirectory()) {
					countFolder(file, atomicIntegerSub);
				} else {
					atomicIntegerSub.addAndGet(1);
				}
			}
		}
	}
}
