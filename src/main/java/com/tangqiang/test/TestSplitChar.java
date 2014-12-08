package com.tangqiang.test;

public class TestSplitChar {
	public static final String US = Character.toString((char) 0x1F);

	public static void main(String[] args) {
//		String s =  "SMS03" + US + "00000001" + US + "EUR:CNY=100:797.69" + US + "20140626113011";
//		System.out.println(s.length() + US + s);
//		System.out.println(s.length());
//
////		String b = "ASDQIONXS".getBytes() ^ 5;
//		// System.out.println(s^b);
//		System.out.println(5 ^ 6);
		replaceChar();
	}

	public String xorString(String str1, String str2) {
		byte[] data = str1.getBytes();
		byte[] keyData = str2.getBytes();
		int keyIndex = 0;
		for (int x = 0; x < str1.length(); x++) {
			data[x] = (byte) (data[x] ^ keyData[keyIndex]);
			if (++keyIndex == keyData.length) {
				keyIndex = 0;
			}
		}
		return new String(data);
	}
	
	
	private static void replaceChar(){
		String s = "ASDF$SK*L54.57#";
		String sN = s.replaceAll("[^a-zA-Z0-9]","_");
		System.out.println(sN);
		
	}
}
