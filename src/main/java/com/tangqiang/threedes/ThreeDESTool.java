package com.tangqiang.threedes;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * 3des(ECB)加解密 md5加密
 *
 * @author tqiang
 * @email tqiang@grgbanking.com
 * @file com.tangqiang.threedes.ThreeDESTool.java 
 * @date 2014-6-25 下午4:34:16
 *
 * @version 1.0 2014-6-25 tqiang create
 * 
 * @copyright Copyright © 2011-2014 广电运通 All rights reserved.
 */
public class ThreeDESTool {

	private Logger log = LoggerFactory.getLogger(ThreeDESTool.class); // 日志记录器
	private String AlgorithmECB = "DESede/ECB/NoPadding";// 加密方法／运算模式／填充模式

	private String sMasterKey = ""; // 加密主密钥
	private byte[] sMessage = null; // 加密字符串

	/**
	 * 设备加密主密钥
	 * 
	 * @param sTmpMasterKey
	 *            加密主密钥
	 * @return false 主密钥格式错误 true 主密钥设置成功
	 */
	public boolean setMasterKey(String sTmpMasterKey) {
		if (sTmpMasterKey.length() != 8) {
			log.info("主密钥长度错误，请输入 8 位的主密钥");
			return false;
		}
		sMasterKey = sTmpMasterKey + "0000000000000000";
		return true;
	}

	/**
	 * 设置并格式化加解密数据,如果加解密数据不足8的倍数，则数据后补0x00 直到够8的倍数
	 * 
	 * @param sTmpMessage
	 *            加解密数据
	 * @param iType
	 *            数据类型 0-10进制 1-16进制
	 * @return true 初始化向量设置成功
	 */
	public boolean setMessage(String sTmpMessage, int iType) {
		if (iType == 0) {
			if (sTmpMessage.length() % 8 != 0) {
				int iMessLen = sTmpMessage.length();
				String sTmpString = "";
				for (int iIndex = 0; iIndex < 8 - iMessLen % 8; iIndex++) {
					sTmpString += "00";
				}
				sTmpMessage += new String(this.hexToBytes(sTmpString));
			}
			sMessage = sTmpMessage.getBytes();
		} else {
			if (sTmpMessage.length() % 16 != 0) {
				int iMessLen = sTmpMessage.length();
				for (int iIndex = 0; iIndex < 16 - iMessLen % 16; iIndex++) {
					sTmpMessage += "00";
				}
			}
			sMessage = this.hexToBytes(sTmpMessage);
		}
		return true;
	}

	/**
	 * 3DES加密
	 * 
	 * @return 加密后密文
	 * @throws Exception
	 */
	public String encryptByECB() throws Exception {
		DESedeKeySpec dks = new DESedeKeySpec(sMasterKey.getBytes("UTF-8"));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
		SecretKey securekey = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance(AlgorithmECB);
		cipher.init(Cipher.ENCRYPT_MODE, securekey);
		byte[] res = cipher.doFinal(sMessage);
		return byte2Hex(res);
	}

	/**
	 * 3DES解密
	 * 
	 * @return 解密后密码
	 * @throws Exception
	 */
	public String decryptByECB() throws Exception {
		sMasterKey = sMasterKey + sMasterKey.substring(0, 8);
		DESedeKeySpec dks = new DESedeKeySpec(sMasterKey.getBytes("UTF-8"));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DESede");
		SecretKey securekey = keyFactory.generateSecret(dks);
		Cipher cipher = Cipher.getInstance(AlgorithmECB);
		cipher.init(Cipher.DECRYPT_MODE, securekey);
		byte[] res = cipher.doFinal(sMessage);
		return byte2Hex(res);

	}

	/**
	 * 将16进制转换为 10 进制
	 * 
	 * @param sSourceString
	 * @return
	 */
	public String ConvertString(String sSourceString) {
		if (sSourceString.length() % 2 != 0)
			return null;
		int iCount = 0;
		char sArrSource[] = sSourceString.toCharArray();
		char sArrDest[] = new char[sSourceString.length() / 2];
		while (iCount < sSourceString.length() / 2) {
			sArrDest[iCount] = (char) (ConvertChar(sArrSource[iCount * 2]) << 4);
			sArrDest[iCount] |= ConvertChar(sArrSource[iCount * 2 + 1]);
			iCount++;
		}
		return new String(sArrDest);
	}

	/**
	 * 字符串压缩,将两个字节的数据高位截掉，压缩成一个字节
	 * 
	 * @param str
	 *            原字符串
	 * @return 压缩后的字符串
	 */
	private byte[] hexToBytes(String str) {
		if (str == null) {
			return null;
		} else if (str.length() < 2) {
			return null;
		} else {
			int len = str.length() / 2;
			byte[] buffer = new byte[len];
			for (int i = 0; i < len; i++) {
				buffer[i] = (byte) Integer.parseInt(str.substring(i * 2, i * 2 + 2), 16);
			}
			return buffer;
		}
	}

	/**
	 * 转换成十六进制字符串
	 */
	private String byte2Hex(byte[] b) {
		String hs = "";
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1) {
				hs = hs + "0" + stmp;
			} else {
				hs = hs + stmp;
			}
			if (n < b.length - 1)
				hs = hs + "";
		}
		return hs.toUpperCase();
	}

	private char ConvertChar(char sSourceString) {
		if (sSourceString >= '0' && sSourceString <= '9')
			return (char) (sSourceString - 48);
		else if (sSourceString >= 'A' && sSourceString <= 'F')
			return (char) (sSourceString - 55);
		else if (sSourceString >= 'a' && sSourceString <= 'f')
			return (char) (sSourceString - 87);
		else
			return 0;
	}

	/**
	 * 转为16进制大写
	 * 
	 * @param bytes
	 * @return
	 */
	private String byte2hex(byte bytes[]) {
		String str1 = "";
		String str2 = "";
		for (int i = 0; i < bytes.length; i++) {
			str2 = Integer.toHexString(bytes[i] & 255);
			if (str2.length() == 1)
				str1 = str1 + "0" + str2;
			else
				str1 = str1 + str2;
		}
		return str1.toUpperCase();
	}

	public static void main(String[] args) {
		ThreeDESTool tool = new ThreeDESTool();

		try {
			String sMastKey = "09ABCDEF";
			String sMsg = "This is Message !";

			System.out.println("主密钥:[" + sMastKey + "] 数据:[" + sMsg + "]");
			tool.setMasterKey(sMastKey);
			tool.setMessage(sMsg, 0);
			String sEncryptString = tool.encryptByECB();
			System.out.println("加密结果为:[" + sEncryptString + "]");

			tool.setMessage( sEncryptString , 1);
			String sDecryptString = tool.decryptByECB();
			System.out.println("解密结果为: 16-[" + sDecryptString + "] 10-[" + tool.ConvertString(sDecryptString).trim() + "]");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
