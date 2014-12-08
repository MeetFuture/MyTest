package com.tangqiang.module.observe;

/**
 * 观察者模式测试
 * 
 * @author tqiang
 * @email tom617288330@gmail.com
 * @date 2014-7-25 下午4:24:59
 * 
 * @version 1.0 2014-7-25 tqiang create
 * 
 */
public class TestMain {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		House house = new House(1000f);
		Buyer b1 = new Buyer("A");
		Buyer b2 = new Buyer("B");
		Buyer b3 = new Buyer("C");
		house.addObserver(b1);
		house.addObserver(b2);
		house.addObserver(b3);
		System.out.println(house);
		house.setPrice(6000.0f);
		System.out.println(house);

	}

}