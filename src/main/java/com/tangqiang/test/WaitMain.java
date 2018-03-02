package com.tangqiang.test;

public class WaitMain {

	public static Integer wait = new Integer(0);

	public static void main(String[] args) {
		try {
			WaitThread th = new WaitThread();
			th.start();

			Thread.sleep(1000);

			synchronized (wait) {
				// wait.notify();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
