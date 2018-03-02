package com.tangqiang.thread;

import java.util.concurrent.atomic.AtomicLong;

/**
 * TODO
 *
 * @author Tom
 * @version 1.0 2017-11-08 0008 Tom create
 * @date 2017-11-08 0008
 * @copyright Copyright © 2017 Grgbanking All rights reserved.
 */
public class SimpleThread extends Thread {
    private AtomicLong count;

    public SimpleThread(AtomicLong count) {
        this.count = count;
    }

    @Override
    public void run() {
//        logger.info("Thread start ....");
        count.incrementAndGet();
    }
}
