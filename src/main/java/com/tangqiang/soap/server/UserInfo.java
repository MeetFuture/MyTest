package com.tangqiang.soap.server;

import javax.xml.ws.WebFault;

/**
 * 用户实体
 *
 * @author tqiang
 * @email tqiang@grgbanking.com
 * @file com.tangqiang.soap.server.entity.UserInfo.java 
 * @date 2014-6-23 上午10:47:20
 *
 * @version 1.0 2014-6-23 tqiang create
 * 
 * @copyright Copyright © 2011-2014 广电运通 All rights reserved.
 */
@WebFault
public class UserInfo {
	private String sUserName ;
	private Integer iAge;
	
	
	public String getsUserName() {
		return sUserName;
	}
	public void setsUserName(String sUserName) {
		this.sUserName = sUserName;
	}
	public Integer getiAge() {
		return iAge;
	}
	public void setiAge(Integer iAge) {
		this.iAge = iAge;
	}
	
	
}
