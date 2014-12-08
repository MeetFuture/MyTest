package com.tangqiang.robot;

import java.io.IOException;
import java.net.URLEncoder;

import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class Tuling {
	
	
	/** 调用图灵机器人平台接口
	* 需要导入的包：commons-logging-1.0.4.jar、 httpclient-4.3.1.jar、httpcore-4.3.jar
	*/
	public static void main(String[] args) throws IOException {

	    String INFO = URLEncoder.encode("北京今日天气", "utf-8");
	    String requesturl = "http://www.tuling123.com/openapi/api?key=asdasd&info="+INFO;
	    HttpGet request = new HttpGet(requesturl);
	    HttpResponse response = HttpClients.createDefault().execute(request);

	    //200即正确的返回码
	    if(response.getStatusLine().getStatusCode()==200){
	        String result = EntityUtils.toString(response.getEntity());
	        System.out.println("返回结果："+result);
	    }
	} 

}
