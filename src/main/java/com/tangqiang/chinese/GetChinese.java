package com.tangqiang.chinese;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 读取文章分析使用的汉字
 *
 * @author Tom
 * @date 2017年4月5日 下午4:04:02
 *
 * @version 1.0 2017年4月5日 Tom create
 * 
 * @copyright Copyright © 2017 广电运通 All rights reserved.
 */
public class GetChinese {

	public static void main(String[] args) throws Exception {
		GetChinese sss = new GetChinese();
		Map<String, Integer> mapCode = sss.execute("E:\\books\\wuji.txt");
		System.out.println(mapCode.size());
		System.out.println(mapCode);
		List<String> list = sss.sortMap(mapCode);
		System.out.println(list.size());
		for (String string : list) {
			System.out.println(string);
		}
	}

	private Map<String, Integer> execute(String filePath) throws Exception {
		Map<String, Integer> mapCode = new HashMap<String, Integer>();
		String encoding = "GBK";
		File file = new File(filePath);
		long totalLen = file.length();
		long currentLen = 0;
		InputStreamReader read = new InputStreamReader(new FileInputStream(file), encoding);
		BufferedReader bufferedReader = new BufferedReader(read);
		String lineTxt = null;
		while ((lineTxt = bufferedReader.readLine()) != null) {
			currentLen += lineTxt.length();
			int percent = (int) (currentLen * 100 / totalLen);
			// if (percent % 5 == 0) {
			// System.out.println("Already deal:" + percent + "%");
			// }
			char[] chars = lineTxt.toCharArray();
			for (int i = 0; i < chars.length; i++) {
				char c = chars[i];
				char c2 = i + 1 == chars.length ? ' ' : chars[i + 1];
				int count = mapCode.get("" + c) == null ? 0 : mapCode.get("" + c);
				count = count + 1;
				mapCode.put("" + c, count);

				int count2 = mapCode.get("" + c) == null ? 0 : mapCode.get("" + c);
				count = count + 1;
				mapCode.put("" + c, count);
			}
		}
		bufferedReader.close();
		return mapCode;
	}

	private List<String> sortMap(Map<String, Integer> mapCode) {
		List<String> list = new LinkedList<String>();
		Set<String> keyset = mapCode.keySet();
		for (String string : keyset) {
			if (mapCode.get(string) > 10) {
				String value = "0000000000" + mapCode.get(string);
				list.add(value.substring(value.length() - 8) + " " + string);
			}
		}
		Collections.sort(list);
		return list;
	}
}
