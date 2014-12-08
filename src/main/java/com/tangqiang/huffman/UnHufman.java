package com.tangqiang.huffman;

import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * 解压
 *
 * @author tqiang
 * @email tom617288330@gmail.com
 * @date 2014-7-31 上午10:40:43
 * 
 * @version 1.0 2014-7-31 tqiang  create
 *
 */
public class UnHufman {

	public static void main(String args[]) {
		// 第一步：读取文件，得到码表和子节数组
		UnHufman uf = new UnHufman();
		byte[] bs = uf.readFile("E:\\TermIdList.txtRar");

		// for (byte b : bs) {
		// System.out.println(">>" + b);
		// }

		// 将子节数组转成二进制，得到01串
		String str = uf.byte2String(bs);
		System.out.println(str);

		// 根据01串和码表，还原编码对应的子节，该子节即为压缩之前的原始子节
		ArrayList<Byte> list = uf.string2SrcBytes(str);
		for (byte b : list) {
			System.out.println(b);
		}

		// 将原始子节写到文件
		uf.write2File(list, "E:\\UmTermIdList.txt");
	}

	// 存放读取到的码表数据
	HashMap<String, Byte> mapCode = new HashMap<String, Byte>();

	/**
	 * 读取压缩文件，得到码表和子节数据
	 * 
	 * @param path
	 *            压缩的文件地址
	 * @return 返回读取到的子节数据
	 */
	public byte[] readFile(String path) {
		try {
			// 根据文件路径建立文件输出流
			FileInputStream fos = new FileInputStream(path);
			// 包装成对象输出流
			ObjectInputStream oos = new ObjectInputStream(fos);

			// 码表的长度
			int len = oos.readInt();
			for (int i = 0; i < len; i++) {
				// 读取子节
				byte b = oos.readByte();
				String str = (String) oos.readObject();
				// 将子节对应的编码放入HashMap
				mapCode.put(str, b);
			}

			// 读取数据
			int byteArrayLength = oos.readInt();
			// 根据长度定义子节数组
			byte[] bs = new byte[byteArrayLength];
			// 将流中的剩余子节度如数组
			oos.read(bs);
			return bs;
		} catch (Exception ef) {
			ef.printStackTrace();
		}
		return null;
	}

	/**
	 * 将子节数组中的子节按照十进制-->二进制,得到一个01串
	 * 
	 * @param bs
	 *            子节数组
	 * @return 返回生成的01串
	 */
	public String byte2String(byte[] bs) {
		String s = "";
		// 获取最后一个子节，为补0的个数
		int num = bs[bs.length - 1];
		// 由于最后一个子节是保存的倒数第二个子节补0的个数
		// 不需要转成二进制
		for (int j = 0; j < bs.length - 1; j++) {
			byte b = bs[j];
			String str = Integer.toBinaryString(b);
			if (b < 0) {
				str = str.substring(24);
			} else {
				// 当子节为正数的时候，转成的01串长度可能小于8，应该补0补成8位
				int len = 8 - str.length();
				for (int i = 0; i < len; i++) {
					str = "0" + str;
				}
			}
			s += str;
		}

		// 截取掉最后补的0
		s = s.substring(0, s.length() - num);
		return s;
	}

	/**
	 * 根据码表将01串还原成原始子节
	 * 
	 * @param str
	 *            01串
	 * @return 返回装有原始子节的数组
	 */
	public ArrayList<Byte> string2SrcBytes(String str) {
		// 由于不知道根据01串会转成多少个子节，就采用动态数组或者链表
		ArrayList<Byte> list = new ArrayList<Byte>();
		// 要和码表匹配的字符串，是从str上获取来的
		String s = "";
		// 遍历字符串，取得一个字符
		for (int i = 0; i < str.length(); i++) {
			s += str.charAt(i);
			// 如果包含这个key
			if (mapCode.containsKey(s)) {
				// 得到该编码对应的子节
				byte b = mapCode.get(s);
				// 将子节保存起来
				list.add(b);
				s = "";
			}
		}
		return list;
	}

	/**
	 * 将子节数组写到文件，即为解压后的文件
	 * 
	 * @param list
	 *            解压出的原始子节数组
	 * @param path
	 *            解压后的文件
	 */
	public void write2File(ArrayList<Byte> list, String path) {
		try {
			FileOutputStream fos = new FileOutputStream(path);
			BufferedOutputStream bos = new BufferedOutputStream(fos);

			for (byte b : list) {
				bos.write(b);
			}
			bos.flush();
			bos.close();
			fos.close();
		} catch (Exception ef) {
			ef.printStackTrace();
		}
	}
}
