package com.tangqiang.chinese;

public class PrintChinese {

	public static void main(String[] args) throws Exception {

		byte[] byt = new byte[3];
		StringBuilder sb = new StringBuilder();
		for (int i = Byte.MIN_VALUE; i < Byte.MAX_VALUE; i++) {
			for (int j = Byte.MIN_VALUE; j < Byte.MAX_VALUE; j++) {
				for (int k = Byte.MIN_VALUE; k < Byte.MAX_VALUE; k++) {
					byt[0] = (byte) i;
					byt[1] = (byte) j;
					byt[2] = (byte) k;
					String s = new String(byt, "UTF-8");
					if (s.length() == 1 && !"�".equals(s) && !"".equals(s) && !"?".equals(s)) {
						// sb.append(s + "(" + i + "," + j + "," + k + ")");
						sb.append(s);
						// System.out.println(s + "	" + i + "	" + j + "	" + k);
					}
				}
				if (sb.length() > 0) {
					System.out.print(i + "	" + j + "	");
					System.out.println(sb);
					sb = new StringBuilder();
					Thread.sleep(1);
				}
			}
		}

		System.out.println("a(Unicode)    ：" + "a".getBytes("Unicode").length);
		System.out.println("a(Unicode)    ：" + "aa".getBytes("Unicode").length);
		System.out.println("啊(Unicode)   ：" + "啊".getBytes("Unicode").length);
		System.out.println("啊啊(Unicode) ：" + "啊啊".getBytes("Unicode").length);
		System.out.println("");
		System.out.println("a(UTF-8)    ：" + "a".getBytes("UTF-8").length);
		System.out.println("aa(UTF-8)   ：" + "aa".getBytes("UTF-8").length);
		System.out.println("啊(UTF-8)   ：" + "啊".getBytes("UTF-8").length + "啊".getBytes("UTF-8")[0] + "啊".getBytes("UTF-8")[1] + "啊".getBytes("UTF-8")[2]);
		System.out.println("啊啊(UTF-8) ：" + "啊啊".getBytes("UTF-8").length);
		System.out.println("");
		System.out.println("a(UTF-16)    ：" + "a".getBytes("UTF-16").length);
		System.out.println("aa(UTF-16)   ：" + "aa".getBytes("UTF-16").length);
		System.out.println("啊(UTF-16)   ：" + "啊".getBytes("UTF-16").length);
		System.out.println("啊啊(UTF-16) ：" + "啊啊".getBytes("UTF-16").length);
	}
}
