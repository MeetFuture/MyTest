package com.tangqiang.test;

/**
 * TODO
 *
 * @author Tom
 * @date 2016年11月14日 上午10:45:04
 *
 * @version 1.0 2016年11月14日 Tom create
 * 
 * @copyright Copyright © 2016 广电运通 All rights reserved.
 */
public class WaitThread extends Thread {

	@Override
	public void run() {
		try {
			System.out.println("Wait Thread wait ... " + System.currentTimeMillis());
			synchronized (WaitMain.wait) {
				WaitMain.wait.wait(2000);
			}
			System.out.println("Wait Thread notify ..." + System.currentTimeMillis());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
