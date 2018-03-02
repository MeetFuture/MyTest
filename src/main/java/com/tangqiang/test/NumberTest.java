package com.tangqiang.test;

import java.text.DecimalFormat;

/**
 * 数字测试
 *
 * @author Tom
 * @date 2016年12月7日 上午8:33:54
 *
 * @version 1.0 2016年12月7日 Tom create
 * 
 * @copyright Copyright © 2016 广电运通 All rights reserved.
 */
public class NumberTest {

	public static void main(String[] args) {

		for (int i = 0; i < 020; i++) {
			String tmp = "0000" + Integer.toBinaryString(i);
			String x = tmp.substring(tmp.length() - 4);
			System.out.println(i + "		" + x);
		}

		int xTmp = 0xF;
		for (int i = 0; i < 0x10; i++) {
			int x1 = i | xTmp;
			int x2 = i & xTmp;

			System.out.println(i + "|" + xTmp + " = " + x1 + "		" + i + "$" + xTmp + " = " + x2);
		}

		new NumberTest().buildModule();

	}

	private void buildModule() {
		DecimalFormat df = new DecimalFormat("000");
		for (int i = 0; i < 100; i++) {
			System.out.println("M" + df.format(i));
		}
	}

}
