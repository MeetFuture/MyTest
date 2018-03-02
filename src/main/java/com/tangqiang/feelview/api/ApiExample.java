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
import java.util.Map;

/**
 * Feelview接口访问示例
 */
public class ApiExample {
    private static String baseURL = "http://10.1.231.195:8090/feelview";
    private OkHttpClient client = new OkHttpClient.Builder().cookieJar(new ExampleCookieJar()).followRedirects(false).build();
    private Logger logger = LoggerFactory.getLogger(getClass());

    public static void main(String[] args) {
        try {
            ApiExample exa = new ApiExample();
            Map<String, Object> login = exa.login();
            Map<String, Object> userInfo = exa.getData(baseURL + "/loginData/getUser", new HashMap<String, Object>());
            //Map<String, Object> userData = exa.getData(baseURL + "/loginData/getUserData", new HashMap<String, Object>());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 登录
     */
    private Map<String, Object> login() throws Exception {
        String user = "admin";
        String password = DigestUtils.sha256Hex(user + "admin123");
        String validateCode = getValidateCode();
        String rememberme = "true";
        String language = "zh_CN";

        logger.info("Login username: {}  password: {}  validateCode: {}  rememberMe: {}  language: {}", user, password, validateCode, rememberme, language);
        FormBody formBody = new FormBody.Builder()
                .add("username", user)
                .add("password", password)
                .add("validateCode", validateCode)
                .add("rememberme",rememberme)
                .add("language",language).build();
        Request request = new Request.Builder().url(baseURL + "/loginDo").post(formBody).build();

        Response response = client.newCall(request).execute();
        String responseResult = response.body().string().trim();
        logger.info("Login Response: {}", response);
        logger.info("Login Response body: {}", responseResult);

        Map<String, Object> result = (Map<String, Object>) JSONObject.parse(responseResult);
        return result;
    }

    /**
     * 获取验证码
     */
    private String getValidateCode() throws Exception {
        Request request = new Request.Builder().url(baseURL + "/validateCode").get().build();
        Response response = client.newCall(request).execute();

        InputStream inputStream = response.body().byteStream();//得到图片的流
        BufferedImage image = ImageIO.read(inputStream);
        FileImageOutputStream imageOutput = new FileImageOutputStream(new File("./tmp.png"));
        ImageIO.write(image, "png", imageOutput);

        //断点  根据图片手动输入
        return "";
    }

    /**
     * 访问接口
     */
    private Map<String, Object> getData(String service, Map<String, Object> params) throws Exception {
        String paramsStr = JSONObject.toJSONString(params);
        logger.info("Data Request[{}] Param {}", service, paramsStr);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json; charset=utf-8"), paramsStr);
        Request request = new Request.Builder().url(service).post(requestBody).build();
        Response response = client.newCall(request).execute();
        String responseResult = response.body().string().trim();
        logger.info("Data Response: {}", response);
        logger.info("Data Response body: {}", responseResult);
        Map<String, Object> result = (Map<String, Object>) JSONObject.parse(responseResult);
        return result;
    }
}
