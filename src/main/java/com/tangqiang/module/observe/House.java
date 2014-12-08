package com.tangqiang.module.observe;

import java.util.Observable;

/**
 * 观察者模式观察对象
 * 
 * @author tqiang
 * @email tom617288330@gmail.com
 * @date 2014-7-25 下午4:20:58
 * 
 * @version 1.0 2014-7-25 tqiang create
 * 
 */
public class House extends Observable {

	private float price;

	public House(float price) {
		this.price = price;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		super.setChanged(); // 设置变化点
		super.notifyObservers(price); // 发出变化通知
		this.price = price;
	}

	@Override
	public String toString() {
		return "房子的价格为:" + price;
	}

}