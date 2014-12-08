package com.tangqiang.module.observe;
import java.util.Observable;
import java.util.Observer;

import org.apache.log4j.Logger;
 
/**
 * 观察者模式 观察者
 *
 * @author tqiang
 * @email tom617288330@gmail.com
 * @date 2014-7-25 下午4:23:15
 * 
 * @version 1.0 2014-7-25 tqiang  create
 *
 */
public class Buyer implements Observer{
	private Logger logger = Logger.getLogger(Buyer.class);
     
    private String name;
     
    public Buyer(String name) {
        this.name = name;
    }
 
    @Override
    public void update(Observable observable, Object object) {
    	logger.info("共有观察者:"+observable.countObservers());
        if (object instanceof Float) {
        	logger.info(this.name + "观察到价格变化：" + ((Float)object).floatValue());
        }
    }
}