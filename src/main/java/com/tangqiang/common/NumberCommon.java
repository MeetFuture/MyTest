package com.tangqiang.common;


/**
 * 对数字进行操作
 * 
 * @author tqiang
 * @email tom617288330@gmail.com
 * @date 2014-7-28 下午3:33:11
 * 
 * @version 1.0 2014-7-28 tqiang create
 * 
 */
public class NumberCommon {
	

	// 定义10进制转2进制的方法。
	public static String C10T2(int numb) {
		String result = "";
		for (int i = numb; i > 0; i /= 2)
			result = i % 2 + result;
		return result;
	}

	// 定义10进制转8进制的方法。
	public static String C10T8(int numb) {
		String result = "";
		for (int i = numb; i > 0; i /= 8)
			result = i % 8 + result;
		return result;
	}

	// 定义2进制转10进制的方法。
	public static int C2T10(int numb) {
		int k = 0, result = 0;
		// String result=null;
		for (int i = numb; i > 0; i /= 10) {
			result += (i % 10) * Math.pow(2, k);
			k++;
		}
		return result;
	}

	// 定义8进制转10进制的方法。
	public static int C8T10(int numb) {
		int k = 0, temp = 0;
		for (int i = numb; i > 0; i /= 10) {
			temp += (i % 10) * Math.pow(8, k);
			k++;
		}
		return temp;
	}

	public static void convert10(int numb, int to) {
		String s = "";
		switch (to) {
		case 2:
			s = "" + C10T2(numb);
			break;
		case 8:
			s = "" + C10T8(numb);
			break;
		case 16:
			s = Integer.toHexString(numb).toUpperCase();
			break;
		default:
			System.out.println("wrong input!");
		}
		System.out.println(s);
	}

	public static void convert2(int numb, int to) {
		String s = "";
		switch (to) {
		case 10:
			s = "" + C2T10(numb);
			break;
		case 8:
			s = "" + C10T8(C2T10(numb));
			break;
		case 16:
			s = Integer.toHexString(C2T10(numb)).toUpperCase();
			break;
		default:
			System.out.println("wrong input!");
		}
		System.out.println(s);

	}

	public static void convert8(int numb, int to) {
		String s = "";
		switch (to) {
		case 2:
			s = "" + C10T2(C8T10(numb));
			break;
		case 10:
			s = "" + C8T10(numb);
			break;
		case 16:
			s = Integer.toHexString(C8T10(numb)).toUpperCase();
			break;
		default:
			System.out.println("wrong input!");
		}
		System.out.println(s);

	}

	public static void convert16(String numb, int to) {
		String s = "";
		switch (to) {
		case 2:
			int temp2 = Integer.parseInt(numb, 16);
			s = C10T2(temp2);
			break;
		case 8:
			int temp3 = Integer.parseInt(numb, 16);
			s = C10T8(temp3);
			break;
		case 10:
			int temp = Integer.parseInt(numb, 16);
			s = "" + temp;
			break;
		default:
			System.out.println("wrong input!");
		}
		System.out.println(s);

	}

}
