package com.tangqiang.leetcode;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 Add to List 535. Encode and Decode TinyURL
 *
 * @author acer-pc
 * @date 2017年6月1日 上午9:33:11
 * 
 * @version 1.0 2017年6月1日 acer-pc create
 *
 */
public class Solution535 {
	public static Map<String, String> map = new HashMap<String, String>();

	public static void main(String[] args) {
		String url = "https://leetcode.com/problems/design-tinyurl";
		Solution535 codec = new Solution535();
		String encode = codec.encode(url);
		System.out.println(encode);
		System.out.println(codec.decode(encode));

	}

	/** 535. Encode and Decode TinyURL */
	// Encodes a URL to a shortened URL.
	public String encode(String longUrl) {
		long poi = 0;
		char[] chars = longUrl.toCharArray();
		for (char c : chars) {
			poi = (poi << 5) + c;
		}
		poi = Math.abs(poi);
		String result = getEncodeStr(poi, 6);
		map.put(result, longUrl);
		return "http://tinyurl.com/" + result;
	}

	private String getEncodeStr(long poi, int len) {
		StringBuilder res = new StringBuilder(len);
		char[] array = { 'q', 'w', 'e', 'r', 't', 'y', 'u', 'i', 'o', 'p', 'a', 's', 'd', 'f', 'g', 'h', 'j', 'k', 'l', 'z', 'x', 'c', 'v', 'b', 'n', 'm', '0', '1', '2', '3', '4', '5', '6', '7', '8',
				'9', 'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P', 'A', 'S', 'D', 'F', 'G', 'H', 'J', 'K', 'L', 'Z', 'X', 'C', 'V', 'B', 'N', 'M' };
		int i = 0;
		while (i++ < len) {
			int curr = (int) (poi % array.length);
			res.append(array[curr]);
			poi = poi / 62;
		}
		String result = res.reverse().toString();
		return result;
	}

	// Decodes a shortened URL to its original URL.
	public String decode(String shortUrl) {
		String str = shortUrl.substring(shortUrl.lastIndexOf("/") + 1);
		return map.get(str);
	}

}
