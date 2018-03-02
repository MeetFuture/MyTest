package com.tangqiang.feelview.api;

import java.io.IOException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Authenticator;
import okhttp3.Cookie;
import okhttp3.Credentials;
import okhttp3.FormBody;
import okhttp3.Headers;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.Route;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;

/**
 * feelview 接口访问示例
 *
 * @author Tom
 * @date 2017年8月22日 上午9:18:32
 *
 * @version 1.0 2017年8月22日 Tom create
 * 
 * @copyright Copyright © 2017-???? 广电运通 All rights reserved.
 */
public class WebExample {
	private Logger logger = LoggerFactory.getLogger(getClass());
	private static String user = "253%250%241%245%240%";// admin
	private static String password = "1eb1afa20dc454d6ef3b6dc6abcbd7dca7e519b698fdf073f4625ded09d74807";// admin123

	public static void main(String[] args) {
		try {
			WebExample exa = new WebExample();
			// Map<String, Object> data = exa.login("http://localhost:8080/feelview/j_spring_security_check.html", user, password);
			Map<String, Object> data = new HashMap<String, Object>();
			data.put("cookieString", "JSESSIONID=124B11112E9B97A62DFF683ECBDAEA92");
			exa.getUserData(data, "http://10.1.39.127:8080/feelview/service/loginData/getUserData");
			
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/** 获取用户登录时的数据 */
	private void getUserData(Map<String, Object> data, String url) throws Exception {
		String cookieString = data.get("cookieString") == null ? "" : data.get("cookieString").toString();
		Map<String, Object> user = (Map<String, Object>) data.get("user");
		String loginUserId = user == null || user.get("loginUserId") == null ? "grgadmin" : user.get("loginUserId").toString();

		Map<String, Object> mapData = new HashMap<String, Object>();
		mapData.put("language", "zh_CN");
		mapData.put("loginUserId", loginUserId);
		String requestStr = JSONObject.toJSONString(mapData);
		RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), requestStr);
		logger.info("getUserData RequestStr  :" + requestStr);
		Request request = new Request.Builder().header("Cookie", cookieString).url(url).post(requestBody).build();
		Response response = new OkHttpClient().newCall(request).execute();
		logger.info("getUserData response is :" + response);
		logger.info("getUserData response body:" + response.body().string());
	}

	/** 不需要获取 cookie 直接获取数据 */
	private void getUserDataByAuthorization(String url) throws Exception {
		Map<String, Object> mapData = new HashMap<String, Object>();
		mapData.put("language", "zh_CN");
		String requestStr = JSONObject.toJSONString(mapData);
		String credential = Credentials.basic(user, password);
		RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), requestStr);
		logger.info("RequestStr  :" + requestStr);
		Request request = new Request.Builder().header("Authorization", credential).url(url).post(requestBody).build();
		Response response = new OkHttpClient().newCall(request).execute();
		logger.info("response is :" + response);
		logger.info("response body:" + response.body().string());
	}

	/** 登录 */
	private Map<String, Object> login(String url, String usr, String pwd) throws Exception {
		Map<String, Object> mapResult = new HashMap<String, Object>();
		FormBody formBody = new FormBody.Builder().add("j_username", usr).add("j_password", pwd).build();
		Request request = new Request.Builder().url(url).post(formBody).build();
		OkHttpClient client = new OkHttpClient.Builder().followRedirects(false).build();

		Response response = client.newCall(request).execute();
		String responseResult = response.body().string().trim();
		logger.info("login response body:" + responseResult + "    response:" + response);

		if (response.code() == 302) {
			Headers headers = response.headers();
			String urlRedirect = headers.get("Location");
			if (urlRedirect.contains("LoginSuccess")) {
				Cookie cookie = Cookie.parseAll(request.url(), headers).get(0);
				String cookieString = cookie.name() + "=" + cookie.value();
				logger.info("Login success ,begin redirect ... cookie:" + cookieString);
				mapResult.put("cookie", cookie);
				mapResult.put("cookieString", cookieString);
				Request requestRedirect = new Request.Builder().header("Cookie", cookieString).url(urlRedirect).get().build();

				Response responseRedirect = client.newCall(requestRedirect).execute();
				String loginResult = responseRedirect.body().string().trim();
				logger.info("login redirect response:" + responseRedirect);
				logger.info("login redirect response body:" + loginResult);

				if (responseRedirect.code() == 200) {
					JSONObject jsonResult = JSONObject.parseObject(loginResult);
					int resultCode = jsonResult.getIntValue("resultCode");
					int resultType = jsonResult.getIntValue("resultType");
					if (resultCode == 0 && resultType == 11) {
						mapResult.put("user", jsonResult.get("data"));
					} else {
						logger.error("Login redirect not ok !!!");
					}
				}
			} else {
				logger.error("Login not ok !!!");
			}
		} else {
			logger.error("login response not ok !!!");
		}
		logger.info("login result:" + mapResult);
		return mapResult;
	}

	private Response post(URL url, RequestBody request) {
		OkHttpClient client = new OkHttpClient.Builder().authenticator(new Authenticator() {
			public Request authenticate(Route route, Response response) throws IOException {
				System.out.println("Authenticating for response: " + response);
				System.out.println("Challenges: " + response.challenges());
				String credential = Credentials.basic("user", "password");
				return response.request().newBuilder().header("Authorization", credential).build();
			}
		}).build();
		return null;
	}

}
