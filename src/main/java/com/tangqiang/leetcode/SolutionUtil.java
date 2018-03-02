package com.tangqiang.leetcode;

/**
 *
 * @author acer-pc
 * @date 2017年6月23日 下午2:05:34
 * 
 * @version 1.0 2017年6月23日 acer-pc create
 *
 */
public class SolutionUtil {

	public static String printBinary(int n) {
		String code = "0000000000000000" + Integer.toBinaryString(n);
		code = code.substring(code.length() - 16);
		System.out.println(code);
		return code;
	}

	public static void printArr(int[] res) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < res.length; i++) {
			sb.append(res[i] + "    ");
		}
		System.out.println(sb);
	}
	
	public static void println(int line) {
		for (int i = 0; i < line; i++) {
			System.out.println();
		}
	}
	
	public static void printArr(int[][] res) {
		for (int i = 0; i < res.length; i++) {
			StringBuilder sb = new StringBuilder();
			for (int j = 0; j < res[0].length; j++) {
				String string = res[i][j] == 0? " ":(""+res[i][j]);
				sb.append(string + "    ");
			}
			System.out.println(sb);
		}
	}

	public static void printArr(Integer[] res) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < res.length; i++) {
			sb.append(res[i] + "    ");
		}
		System.out.println(sb);
	}
}
