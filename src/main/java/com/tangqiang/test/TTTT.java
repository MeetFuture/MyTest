package com.tangqiang.test;

import java.util.Calendar;
import java.util.Date;

public class TTTT {

	public static void main(String[] args) {

		Calendar c1 = Calendar.getInstance();
		c1.setTime(new Date(System.currentTimeMillis() + 12 * 60 * 60000));
		int value = c1.get(Calendar.DAY_OF_WEEK);
		System.out.println(value);

		String hash = "asdfghjk";
		int hashCode = hash.hashCode();
		System.out.println(hash + "　hashCode　"+ hashCode);
	}

}
