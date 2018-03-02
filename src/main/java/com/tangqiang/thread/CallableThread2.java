package com.tangqiang.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class CallableThread2 implements Callable<String> {
	public String call() throws Exception {
		System.out.println("进入CallableThread的call()方法, 开始睡觉, 睡觉时间为" + System.currentTimeMillis());
		Thread.sleep(10000);
		return "123";
	}

	public static void main(String[] args) throws Exception {
		ExecutorService es = Executors.newCachedThreadPool();
		CallableThread ct = new CallableThread();
		FutureTask<String> f = new FutureTask<String>(ct);
		es.submit(f);
		es.shutdown();

		System.out.println("主线程等待5秒, 当前时间为" + System.currentTimeMillis());
		Thread.sleep(5000);
		System.out.println("主线程5秒结束, 当前时间为" + System.currentTimeMillis());

		String str = f.get();
		System.out.println("Future已拿到数据, str = " + str + ", 当前时间为" + System.currentTimeMillis());
	}
}