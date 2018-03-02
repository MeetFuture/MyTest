package com.tangqiang.test;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class SemaphoreThreadPool {

	public static int numbers = 20;

	public static void main(String[] args) {
		// 允许2个线程同时访问
		final Semaphore semaphore = new Semaphore(2, true);
		final ExecutorService executorService = Executors.newFixedThreadPool(4);

		for (int i = 0; i < 20; i++) {
			final int index = i + 1;
			executorService.execute(new Runnable() {
				public void run() {
					try {
						numbers = numbers - 1;
						System.out.println("(" + index + ")." + "线程:" + Thread.currentThread().getName() + ",numbers=" + numbers);
						TimeUnit.SECONDS.sleep(1);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			});
		}
		System.out.println("................");
		executorService.shutdown();
	}

}