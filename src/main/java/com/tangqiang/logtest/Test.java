package com.tangqiang.logtest;

import java.util.Hashtable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.impl.StaticLoggerBinder;

/**
 *
 * @author Tom
 * @date 2017年8月7日 下午3:41:51
 *
 * @version 1.0  2017年8月7日  Tom  create
 * 
 * @copyright Copyright © 2017-???? 广电运通 All rights reserved.
 */
public class Test {
	private Logger logger = LoggerFactory.getLogger(getClass());

	
	public static void main(String[] args) {
		Hashtable<String,Object> ble = new Hashtable<String, Object>();
		ble.put(null, "");
		
//		Test test = new Test();
//		test
		//.show();
		System.out.println(ble);
	}
	
	private final void show() {
		logger.info("This is info !");
		//static  int i = 0;
		logger.debug("This is debug !");
		Integer.valueOf("").intValue();
		
		int[] a[] = new int[1][1];
		
		System.out.println(a);
	}

}
