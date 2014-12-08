package com.tangqiang.time;

import java.util.concurrent.TimeUnit;

public class SleepTest {

	public static void main(String[] args) throws InterruptedException {
		for (int i = 0; i < 20; i++) {
			test();
		}
	}

	private static void test() throws InterruptedException {
		long nano = System.nanoTime();
		long millis = System.currentTimeMillis();

//		Thread.sleep(2);
		TimeUnit.NANOSECONDS.sleep(2000000);

		System.out.println((double) (System.nanoTime() - nano) / 1000000);
		System.out.println((System.currentTimeMillis() - millis));
		System.out.println("---------------");
	}
}
