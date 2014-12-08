package com.tangqiang.time;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 时间测试
 *
 * @author tqiang
 * @email tqiang@grgbanking.com
 * @file com.tangqiang.time.TimeTest.java 
 * @date 2014-6-23 上午8:19:09
 *
 * @version 1.0 tqiang create
 * 
 * @copyright Copyright © 2011-2014 广电运通 All rights reserved.
 */
public class TimeTest {
	
	public static void main(String[] args) {
		TimeTest tt = new TimeTest();
		System.out.println(tt.getDateEnd());
	}
	
	private String getDateEnd() {
		SimpleDateFormat sdfDateTime = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long lNow = System.currentTimeMillis();
		long lTime = (lNow -lNow % 86400000) + 143999000;
		return sdfDateTime.format(new Date(lTime));
	}
}
