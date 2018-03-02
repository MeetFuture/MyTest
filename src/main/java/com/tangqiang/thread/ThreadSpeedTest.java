package com.tangqiang.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * TODO
 *
 * @author Tom
 * @version 1.0 2017-11-08 0008 Tom create
 * @date 2017-11-08 0008
 * @copyright Copyright © 2017 Grgbanking All rights reserved.
 */
public class ThreadSpeedTest {


    public static void main(String[] args) throws InterruptedException {
        ThreadSpeedTest th = new ThreadSpeedTest();

        th.testSimple();
        th.testPool();
    }


    private void testSimple() throws InterruptedException {
        Long start = System.currentTimeMillis();
        AtomicLong count = new AtomicLong(0);
        for (int i = 0; i < 10000; i++) {
            SimpleThread simpleThread = new SimpleThread(count);
            simpleThread.start();
            simpleThread.join();
        }
        Long end = System.currentTimeMillis();
        System.out.println("ThreadSpeedTest.main count:" + count.longValue() + "    Time:" + (end - start) + "/ms");
    }

    private void testPool() throws InterruptedException {
        ExecutorService service = Executors.newFixedThreadPool(5);


        Long start = System.currentTimeMillis();
        AtomicLong count = new AtomicLong(0);
        for (int i = 0; i < 10000; i++) {
            service.execute(new SimpleThread(count));
        }
        service.awaitTermination(10, TimeUnit.SECONDS);
        Long end = System.currentTimeMillis();
        System.out.println("ThreadSpeedTest.main count:" + count.longValue() + "    Time:" + (end - start) + "/ms");

    }
}
