package com.tangqiang.soap.server;

import java.util.HashMap;

import org.apache.log4j.Logger;


/**
 * 任务执行
 * 
 * @author tqiang
 * @email tqiang@grgbanking.com
 * @file com.tangqiang.soap.DoSomething.java
 * @date 2014-6-23 上午10:07:50
 * 
 * @version 1.0 2014-6-23 tqiang create
 * 
 * @copyright Copyright © 2011-2014 广电运通 All rights reserved.
 */
public class DoSomethingImpl implements IDoSomething {
	private Logger logger = Logger.getLogger(DoSomethingImpl.class);

	public String doLog(String sUser, String sOper) {
		logger.info("DoLog  用户:" + sUser + " 执行操作:" + sOper);
		return "SUCCESS";
	}

	@Override
	public void getData(HashMap<String, String> mapData) {
		logger.info("getData " + mapData);
	}

	@Override
	public void saveUser(UserInfo user) {
		logger.info("saveUser  UserName:" + user.getsUserName() + " Age:"+user.getiAge());
	}
}
