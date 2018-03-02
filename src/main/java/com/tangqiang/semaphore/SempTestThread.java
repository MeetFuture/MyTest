package com.tangqiang.semaphore;

import java.util.concurrent.Semaphore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SempTestThread extends Thread {
	private Logger logger = LoggerFactory.getLogger(getClass());
	private Semaphore semaphore;

	public SempTestThread(Semaphore semaphore) {
		this.semaphore = semaphore;
	}

	public void run() {
		try {
			logger.info("Thread :" + getName() + "  begin ......");
			Thread.sleep(2000);
			logger.info("Thread :" + getName() + "  end !!!");
		} catch (Exception e) {
			logger.error("Thread :" + getName() + "  error! ", e);
		} finally {
			semaphore.release();
		}
	}
}
