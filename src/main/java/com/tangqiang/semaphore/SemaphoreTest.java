package com.tangqiang.semaphore;

import java.util.concurrent.Semaphore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SemaphoreTest {
	private Logger logger = LoggerFactory.getLogger(getClass());

	public static void main(String[] args) {
		try {
			Semaphore semaphore = new Semaphore(4);
			
			for (int i = 0; i < 10; i++) {
				semaphore.acquire();
				new SempTestThread(semaphore).start();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


}
