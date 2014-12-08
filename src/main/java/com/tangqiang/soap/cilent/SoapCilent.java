package com.tangqiang.soap.cilent;

import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.util.HashMap;

import org.apache.cxf.endpoint.Client;
import org.apache.cxf.jaxws.endpoint.dynamic.JaxWsDynamicClientFactory;
import org.apache.cxf.ws.security.kerberos.KerberosClient;
import org.apache.log4j.Logger;

import com.tangqiang.soap.server.UserInfo;

/**
 * 执行soap请求
 * 
 * @author tqiang
 * @email tqiang@grgbanking.com
 * @file com.tangqiang.soap.cilent.CallCilent.java
 * @date 2014-6-23 上午10:15:52
 * 
 * @version 1.0 2014-6-23 tqiang create
 * 
 * @copyright Copyright © 2011-2014 广电运通 All rights reserved.
 */
public class SoapCilent {
	private Logger logger = Logger.getLogger(SoapCilent.class);

	public static void main(String[] args) {
		SoapCilent sc = new SoapCilent();
		sc.call();
	}

	private void call() {
		try {
			String urlAdress = "http://127.0.0.1:8088/SoapServer/DoSomething?wsdl";
			Client client = null;
			JaxWsDynamicClientFactory dcf = JaxWsDynamicClientFactory.newInstance();
			client = dcf.createClient(urlAdress);
			logger.info("开始调用 doLog !");
			Object[] oArr = client.invoke("doLog", "Tom", "run!");
			for (Object object : oArr) {
				logger.info(object);
			}
			
//			logger.info("开始调用 getData !");
//			HashMap<String,String> map = new HashMap<String, String>();
//			map.put("name", "Joe Schmoe");
//			map.put("age", "25");
//			client.invoke("getData", map);

			logger.info("开始调用 saveUser !");
			

			UserInfo user2 = new UserInfo();
			user2.setsUserName("Tom");
			user2.setiAge(25);
			client.invoke("saveUser", user2);
		} catch (Exception e) {
			logger.error(e);
		}
	}
}
