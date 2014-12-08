package com.tangqiang.test;

import java.util.UUID;

/**
 * java系统自带随机数生成
 *
 * @author tqiang
 * @email tqiang@grgbanking.com
 * @file com.tangqiang.test.RandomTest.java 
 * @date 2014-7-2 下午4:47:36
 *
 * @version 1.0  2014-7-2  tqiang  create
 * 
 * @copyright Copyright © 2011-2014 广电运通 All rights reserved.
 */
public class RandomTest {
	public static void main(String[] args) {
		long lBeginTime = System.currentTimeMillis();
		for (int i = 0; i < 100000000; i++) {
			//5af1ef48-257d-4de2-8e90-e74a398a0b56
			String sUUID = UUID.randomUUID().toString();
			String sDecode = sUUID.replaceAll("-", "");
			
		}
		long lEndTime = System.currentTimeMillis();
		System.out.println("耗时 :" + (lEndTime-lBeginTime)+"/ms");
	}
}
