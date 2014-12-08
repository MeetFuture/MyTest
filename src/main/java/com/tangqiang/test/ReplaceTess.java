package com.tangqiang.test;

/**
 * 替换测试
 *
 * @author tqiang
 * @email tqiang@grgbanking.com
 * @file com.tangqiang.test.ReplaceTess.java 
 * @date 2014-7-1 上午11:03:19
 *
 * @version 1.0  2014-7-1  tqiang  create
 * 
 * @copyright Copyright © 2011-2014 广电运通 All rights reserved.
 */
public class ReplaceTess {
	
	
	public static void main(String[] args) {
		
		String serialNo = "sAsd23aFG/..sa-";
		System.out.println(serialNo.replaceAll("\\D+", ""));
		
	}
}
