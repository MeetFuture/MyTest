package com.tangqiang.soap.server;

import org.apache.cxf.frontend.ServerFactoryBean;

/**
 * soap服务器端
 *
 * @author tqiang
 * @email tqiang@grgbanking.com
 * @file com.tangqiang.soap.RegistServer.java 
 * @date 2014-6-23 上午10:04:23
 *
 * @version 1.0 2014-6-23 tqiang create
 * 
 * @copyright Copyright © 2011-2014 广电运通 All rights reserved.
 */
public class SoapServer {
	
	private String soapServerURL = "http://127.0.0.1:8088/SoapServer";
	
	public static void main(String[] args) {
		SoapServer rs = new SoapServer();
		rs.register();
	}
	
	private void register() {
		ServerFactoryBean svrFactory = new ServerFactoryBean();
		
		DoSomethingImpl ds = new DoSomethingImpl();
		svrFactory.setServiceClass(IDoSomething.class);
		svrFactory.setAddress(soapServerURL + "/DoSomething");
		svrFactory.setServiceBean(ds);
		svrFactory.create();
	}
	
	
}
