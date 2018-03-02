package com.tangqiang.feelview.api;


import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**cookie 管理*/
public class ExampleCookieJar implements CookieJar {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private Map<String, List<Cookie>> map = new HashMap<String, List<Cookie>>();

    @Override
    public void saveFromResponse(HttpUrl httpUrl, List<Cookie> list) {
        List<Cookie> listOld = map.get(httpUrl.host()) != null ? map.get(httpUrl.host()) : new ArrayList<Cookie>();
        listOld.addAll(list);
        logger.info("CookieJar save[{}] : {}   host[{}]", httpUrl.url() ,  listOld ,httpUrl.host() );
        map.put(httpUrl.host(), listOld);
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl httpUrl) {
        List<Cookie> list = map.get(httpUrl.host()) != null ? map.get(httpUrl.host()) : new ArrayList<Cookie>();
        logger.info("CookieJar load [{}] :{}   host[{}]", httpUrl.url() ,  list ,httpUrl.host() );
        return list;
    }
}