package com.tangqiang.mytest;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.apache.log4j.Logger;

/**
 * java8 新特性测试
 * 
 * @author tqiang
 * @email tom617288330@gmail.com
 * @date 2014-7-29 下午12:02:47
 * 
 * @version 1.0 2014-7-29 tqiang create
 * 
 */
public class Java8 {
	private Logger logger = Logger.getLogger(Java8.class);

	public static void main(String[] args) {
		Java8 j = new Java8();
//		j.testList();
	}

//	private void testList() {
//		Comparator<String> c = (a, b) -> Integer.compare(a.length(),b.length());
//		List<String> names = Arrays.asList("peter", "anna", "mike", "xenia");
//
//		Collections.sort(names, new Comparator<String>() {
//		    @Override
//		    public int compare(String a, String b) {
//		    	logger.info( a + "  ->  "+b);
//		        return b.compareTo(a);
//		    }
//		});
//		
//		names.forEach(new Consumer<String>(){
//			@Override
//			public void accept(String t) {
//				logger.info(t);
//			}
//		});
//		
//		
//		Consumer<String> greeter = ts -> System.out.println(ts);
//		greeter.accept("Luke");
//	}
//
//	private void testOptional() {
//		Optional<String> optional = Optional.of("bam");
//
//		optional.isPresent();           // true
//		optional.get();                 // "bam"
//		optional.orElse("fallback");    // "bam"
//
//		optional.ifPresent((s) -> System.out.println(s.charAt(0)));     // "b"
//	}
}
