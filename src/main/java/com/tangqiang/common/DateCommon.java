package com.tangqiang.common;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 日期处理
 * 
 * @author tqiang
 * @email tom617288330@gmail.com
 * @date 2014-7-25 下午4:54:26
 * 
 * @version 1.0 2014-7-25 tqiang create
 * 
 */
public class DateCommon {
	
	public static void main(String[] args) {
		getPartDate();
	}

	/**
	 * 获取某年某周的起始时间和结束时间
	 * 
	 * @param year
	 * @param weekindex
	 * @return
	 */
	public static String[] getDayOfWeek(int year, int weekindex) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		Calendar c = Calendar.getInstance();
		//c.setWeekDate(year, weekindex, 1);

		int dayOfWeek = c.get(Calendar.DAY_OF_WEEK) - 2;
		c.add(Calendar.DATE, -dayOfWeek); // 得到本周的第一天
		String begin = sdf.format(c.getTime());
		c.add(Calendar.DATE, 6); // 得到本周的最后一天
		String end = sdf.format(c.getTime());
		String[] range = new String[2];
		range[0] = begin;
		range[1] = end;
		return range;
	}
	
	
	public static void getPartDate(){
		Date d = new Date(10000l);
		System.out.println(d.getYear());
		System.out.println(d.getMonth());
		Calendar now = Calendar.getInstance();
		Calendar ca = Calendar.getInstance();
		ca.setTime(d);
		
		
		System.out.println(now.get(Calendar.YEAR));
		System.out.println(ca.get(Calendar.YEAR));
		System.out.println(now.get(Calendar.MONTH));
		
	}
}
