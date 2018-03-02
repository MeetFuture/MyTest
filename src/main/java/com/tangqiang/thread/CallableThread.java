package com.tangqiang.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Callable;
import java.util.concurrent.FutureTask;

/**
 * TODO
 *
 * @author Tom
 * @version 1.0 2017年4月19日 Tom create
 * @date 2017年4月19日 下午1:41:22
 * @copyright Copyright © 2017 广电运通 All rights reserved.
 */
public class CallableThread implements Callable<String> {
    private static Logger logger = LoggerFactory.getLogger(CallableThread.class);

    public static void main(String[] args) {
        try {
            CallableThread th = new CallableThread();
            FutureTask futureTask = new FutureTask(th);
            new Thread(futureTask).start();
            Object result = futureTask.get();
            logger.info("Result : " + result);
        } catch (Exception e) {
            logger.error("CallableThread main error !", e);
        }

    }

    @Override
    public String call() throws Exception {
        logger.info("进入CallableThread的call()方法, 开始睡觉, 睡觉时间为" + System.currentTimeMillis());
        Thread.sleep(5000);
        return "123";
    }


//	public static void main(String[] args) throws Exception {
//
//		ExecutorService es = Executors.newCachedThreadPool();
//		CallableThread ct = new CallableThread();
//		System.out.println("主线程提交Callable ....");
//		Future<String> f = es.submit(ct);
//		es.shutdown();
//
//		System.out.println("主线程等待, 当前时间为" + System.currentTimeMillis());
//
//		String str = f.get();
//		System.out.println("Future已拿到数据, str = " + str + ", 当前时间为" + System.currentTimeMillis());
//	}

}