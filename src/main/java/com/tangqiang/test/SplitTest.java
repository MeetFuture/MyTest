package com.tangqiang.test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SplitTest {
	public static void main(String[] args) {

		// for (int i = 0; i < 100; i++) {
		// String batTermId = "";
		// batTermId = batTermId.replaceAll(" ", ",").replaceAll("\\n", ",").replaceAll("\\r\\n", ",");
		// String[] termIdArr = batTermId.split(",");
		// System.out.println(termIdArr);
		// }

		String old = "aaaJOU201701aa";

		Pattern p = Pattern.compile("JOU\\d{4}(0[1-9]|1[0-2])");
		Matcher m = p.matcher(old);
		System.out.println(m.matches());

	}
}
