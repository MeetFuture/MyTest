package com.tangqiang.stokeplus;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FilenameFilter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 统计 StrokePlus 各个手势使用的次数
 *
 * @author Tom
 * @version 1.0 2018-01-26 0026 Tom create
 * @date 2018-01-26 0026
 * @copyright Copyright © 2018 Grgbanking All rights reserved.
 */
public class StrokeCount extends Thread {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private String LogPath = "D:\\Program Files\\StrokesPlus\\logs";


    public static void main(String[] args) {
        StrokeCount th = new StrokeCount();
        th.start();
    }

    @Override
    public void run() {
        try {
            Map<String, AtomicInteger> res = new HashMap<String, AtomicInteger>();
            Map<String, Integer> resDay = new HashMap<String, Integer>();
            AtomicInteger count = new AtomicInteger();

            File folder = new File(LogPath);
            File[] logs = folder.listFiles(new FilenameFilter() {
                @Override
                public boolean accept(File dir, String name) {
                    logger.info(dir.getAbsolutePath() + "  name:" + name);
                    boolean acc = new File(dir, name).lastModified() > System.currentTimeMillis() - 20 * 24 * 3600 * 1000;
                    return acc;
                }
            });
            for (File file : logs) {
                logger.info(file.getAbsolutePath());
                List<String> list = FileUtils.readLines(file, "UTF-8");
                resDay.put(file.getName(), list.size());
                for (String cmd : list) {
                    if (!res.containsKey(cmd)) {
                        res.put(cmd, new AtomicInteger());
                    }
                    res.get(cmd).incrementAndGet();
                    count.incrementAndGet();
                }
            }
            logger.info("Count :" + count);

            resDay.entrySet().stream().sorted(new MapComparator2()).forEach(System.out::println);
            res.entrySet().stream().sorted(new MapComparator1()).forEach(System.out::println);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    class MapComparator1 implements Comparator<Map.Entry<String, AtomicInteger>> {

        @Override
        public int compare(Map.Entry<String, AtomicInteger> entry1, Map.Entry<String, AtomicInteger> entry2) {
            return entry2.getValue().get() - entry1.getValue().get();
        }
    }


    class MapComparator2 implements Comparator<Map.Entry<String, Integer>> {

        @Override
        public int compare(Map.Entry<String, Integer> o1, Map.Entry<String, Integer> o2) {
            return o1.getKey().compareTo(o2.getKey());
        }
    }
}
