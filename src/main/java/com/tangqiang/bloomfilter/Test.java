package com.tangqiang.bloomfilter;

import java.util.HashMap;
import java.util.Map;

/**
 * 测试
 *
 * @author Tom
 * @date 2017年5月22日 上午9:50:24
 *
 * @version 1.0 2017年5月22日 Tom create
 * 
 * @copyright Copyright © 2017 广电运通 All rights reserved.
 */
public class Test {

	public static void main(String[] args) {
		BloomFilter bf = new BloomFilter();
		Map<String, String> map = new HashMap<String, String>();

		long i = 0;
		long same = 0;
		while (i < 3000000) {
			String s = randomString();
			boolean cont = bf.contains(s);
			int[] t = bf.add(s);
			String key = formatIntArr(t);

			if (cont) {
				String old = map.get(key);
				if (!s.equals(old)) {
					System.out.println("New:" + s + "	old:" + old + "	" + key + " ++++" + i);
					same++;
				}
			}
			map.put(key, s);
			i++;
			if (i % 10000 == 0) {
				System.out.println("--------" + i);
			}
		}
		System.out.println("Same:" + same + " per:" + (double) same / i);
	}

	private static String randomString() {
		String def = "0123456789abcdefghijklmnopqrstuvwxyz";
		StringBuilder sb = new StringBuilder();
		int len = (int) ((Math.random() * (def.length() - 5)) + 5);
		for (int i = 0; i < 10; i++) {
			sb.append(def.charAt((int) (Math.random() * def.length())));
		}
		return sb.toString();
	}

	private static String formatIntArr(int[] t) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < t.length; i++) {
			sb.append(t[i] + "-");
		}
		return sb.toString();
	}
}
