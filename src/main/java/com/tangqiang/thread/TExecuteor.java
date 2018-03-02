package com.tangqiang.thread;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.Executor;

/**
 * TODO
 *
 * @author Tom
 * @version 1.0 2017-11-08 0008 Tom create
 * @date 2017-11-08 0008
 * @copyright Copyright © 2017 Grgbanking All rights reserved.
 */
public class TExecuteor implements Executor {
    private static Logger logger = LoggerFactory.getLogger(TExecuteor.class);

    public static void main(String[] args) {
        TExecuteor th = new TExecuteor();
        th.execute(new Runnable() {
            @Override
            public void run() {
                logger.info("aaaaaaaaaaaaaaaaaaa");
            }
        });
    }


    @Override
    public void execute(Runnable command) {
        command.run();
    }
}
