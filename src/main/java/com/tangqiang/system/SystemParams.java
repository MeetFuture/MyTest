package com.tangqiang.system;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Properties;
import java.util.Set;

/**
 * 各种JAVA系统参数
 *
 * @author tqiang
 * @version 1.0  2014-7-2  tqiang  create
 * @email tqiang@grgbanking.com
 * @file com.tangqiang.system.SystemParams.java
 * @date 2014-7-2 下午4:58:55
 * @copyright Copyright © 2011-2014 广电运通 All rights reserved.
 */
public class SystemParams {
    private Logger logger = LoggerFactory.getLogger(SystemParams.class);

    public static void main(String[] args) {

//		java.version                       Java 运行时环境版本
//		java.vendor                        Java 运行时环境供应商
//		java.vendor.url                    Java 供应商的 URL
//		java.home                          Java 安装目录
//		java.vm.specification.version      Java 虚拟机规范版本
//		java.vm.specification.vendor       Java 虚拟机规范供应商
//		java.vm.specification.name         Java 虚拟机规范名称
//		java.vm.version                    Java 虚拟机实现版本
//		java.vm.vendor                     Java 虚拟机实现供应商
//		java.vm.name                       Java 虚拟机实现名称
//		java.specification.version         Java 运行时环境规范版本
//		java.specification.vendor          Java 运行时环境规范供应商
//		java.specification.name            Java 运行时环境规范名称
//		java.class.version                 Java 类格式版本号
//		java.class.path                    Java 类路径
//		java.library.path                  加载库时搜索的路径列表
//		java.io.tmpdir                     默认的临时文件路径
//		java.compiler                      要使用的 JIT 编译器的名称
//		java.ext.dirs                      一个或多个扩展目录的路径
//		os.name                            操作系统的名称
//		os.arch                            操作系统的架构
//		os.version                         操作系统的版本
//		file.separator                     文件分隔符（在 UNIX 系统中是“/”）
//		path.separator                     路径分隔符（在 UNIX 系统中是“:”）
//		line.separator                     行分隔符（在 UNIX 系统中是“/n”）
//		user.name                          用户的账户名称
//		user.home                          用户的主目录
//		user.dir                           用户的当前工作目录 


        SystemParams sp = new SystemParams();
        sp.doSys();
    }


    private void doSys() {
//        String[] sArrParamKeys = new String[]{"java.version", "java.vendor", "java.vendor.url", "java.home", "java.vm.specification.version", "java.vm.specification.vendor", "java.vm.specification.name", "java.vm.version", "java.vm.vendor", "java.vm.name", "java.specification.version", "java.specification.vendor", "java.specification.name", "java.class.version", "java.class.path", "java.library.path", "java.io.tmpdir", "java.compiler", "java.ext.dirs", "os.name", "os.arch", "os.version", "file.separator", "path.separator", "line.separator", "user.name", "user.home", "user.dir"};
//        for (int i = 0; i < sArrParamKeys.length; i++) {
//            logger.info(sArrParamKeys[i] + "					" + System.getProperty(sArrParamKeys[i]));
//        }

        Properties properties = System.getProperties();
        Set<Object> keys = properties.keySet();
        for (Object key : keys) {
            Object value = properties.get(key);
            logger.info(key + "					" + value);
        }
    }
}
