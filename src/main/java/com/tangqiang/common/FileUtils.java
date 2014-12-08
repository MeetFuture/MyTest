package com.tangqiang.common;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.channels.FileChannel;

/**
 * 文件操作类
 * 
 * @author tqiang
 * @email tqiang@grgbanking.com
 * @date 2014-7-14 上午8:39:08
 * 
 * @version 1.0 2014-7-14 tqiang create
 * 
 * @copyright Copyright © 2011-2014 广电运通 All rights reserved.
 */
public class FileUtils {
	/**
	 * 使用文件通道的方式复制文件
	 * 
	 * @param s
	 *            源文件
	 * @param t
	 *            复制到的新文件
	 */
	public static boolean fileCopy(File s, File t) {
		FileInputStream fi = null;
		FileOutputStream fo = null;
		FileChannel in = null;
		FileChannel out = null;
		try {
			fi = new FileInputStream(s);
			fo = new FileOutputStream(t);
			in = fi.getChannel();// 得到对应的文件通道
			out = fo.getChannel();// 得到对应的文件通道
			in.transferTo(0, in.size(), out);// 连接两个通道，并且从in通道读取，然后写入out通道
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		} finally {
			try {
				fi.close();
				in.close();
				fo.close();
				out.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				fi = null;
				fo = null;
				in = null;
				out = null;
			}
		}
	}
}
