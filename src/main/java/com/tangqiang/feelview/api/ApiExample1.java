package com.tangqiang.feelview.api;

import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import javax.imageio.stream.FileImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * feelview接口访问示例
 */
public class ApiExample1 {
    private static String baseURL = "http://10.1.42.113:8080/feelview";
    private Logger logger = LoggerFactory.getLogger(getClass());

    public static void main(String[] args) {
        try {
            ApiExample1 exa = new ApiExample1();
            //Map<String, Object> login = exa.login();
            String cookie = "JSESSIONID=67A2ACE7165798D04440D4F3E6CEA58A; path=/feelview; httponly, rememberMe=deleteMe; max-age=0; path=/feelview";//(String) login.get("cookie");
            Map<String, Object> userInfo = exa.getData(baseURL + "/loginData/getUser", cookie, new HashMap<String, Object>());
            Map<String, Object> userData = exa.getData(baseURL + "/loginData/getUserData", cookie, new HashMap<String, Object>());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 登录
     */
    private Map<String, Object> login() throws Exception {
        OkHttpClient client = new OkHttpClient.Builder().cookieJar(new ExampleCookieJar()).followRedirects(false).build();

        String user = "admin";
        String password = DigestUtils.sha256Hex(user + "admin123");
        String validateCode = getValidateCode(client);

        Map<String, Object> mapResult = new HashMap<String, Object>();
        logger.info("Login username: {}  password: {}  validateCode: {}", user,password,validateCode);
        FormBody formBody = new FormBody.Builder().add("username", user).add("password", password).add("validateCode", validateCode).build();
        Request request = new Request.Builder().url(baseURL + "/loginDo").post(formBody).build();

        Response response = client.newCall(request).execute();
        String responseResult = response.body().string().trim();
        logger.info("Login Response: {}", response);
        logger.info("Login Response body: {}", responseResult);

        Map<String, Object> result = (Map<String, Object>) JSONObject.parse(responseResult);
        if (result.get("resultMsg").equals("Normal")) {
            List<Cookie> cookies = client.cookieJar().loadForRequest(request.url());
            logger.error("Login Success ! Cookie:{}", cookies);
            result.put("cookie", cookies.toString());
        } else {
            logger.error("Login Failed !!!");
        }
        return result;
    }

    private String getValidateCode(OkHttpClient client) throws Exception {
        Request request = new Request.Builder().url(baseURL + "/validateCode").get().build();
        Response response = client.newCall(request).execute();

        InputStream inputStream = response.body().byteStream();//得到图片的流
        BufferedImage image = ImageIO.read(inputStream);
        FileImageOutputStream imageOutput = new FileImageOutputStream(new File("./tmp.png"));
        ImageIO.write(image,"png",imageOutput);

        return "";
    }

    /**
     * 访问接口
     */
    private Map<String, Object> getData(String service, String cookie, Map<String, Object> params) throws Exception {
        String paramsStr = JSONObject.toJSONString(params);
        logger.info("Data Request[{}] Param {}    cookie:{}", service, paramsStr,cookie);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), paramsStr);
        Request request = new Request.Builder().header("Cookie", cookie).url(service).post(requestBody).build();
        Response response = new OkHttpClient().newCall(request).execute();
        String responseResult = response.body().string().trim();
        logger.info("Data Response: {}", response);
        logger.info("Data Response body: {}", responseResult);
        Map<String, Object> result = (Map<String, Object>) JSONObject.parse(responseResult);
        return result;
    }

}
