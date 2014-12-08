package com.tangqiang.soap.server;

import java.util.HashMap;

/**
 * soap任务执行接口
 *
 * @author tqiang
 * @email tqiang@grgbanking.com
 * @file com.tangqiang.soap.IDoSomething.java 
 * @date 2014-6-23 上午10:12:24
 *
 * @version 1.0 2014-6-23 tqiang create
 * 
 * @copyright Copyright © 2011-2014 广电运通 All rights reserved.
 */
public interface IDoSomething {

	/**
	 * 日志记录
	 */
	String doLog(String sUser,String sOper);
	
	void getData(HashMap<String,String> mapData);
	
	void saveUser(UserInfo user);
}
