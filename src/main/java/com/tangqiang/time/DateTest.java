package com.tangqiang.time;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateTest {
	public static void main(String[] args) {
		DateTest dt = new DateTest();
		dt.dateAfter();
	}
	
	private void timeBegin(){
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(sdf.format(new Date(0l)));
	}

	private void dateAfter() {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			long l = sdf.parse("20140624").getTime();
			for (int i = 0; i < 100; i++) {
				l = l + 24 * 3600000;
				System.out.println( sdf.format(new Date(l)) +"  "+ (l / 3600000) % 100);
			}
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}

}
