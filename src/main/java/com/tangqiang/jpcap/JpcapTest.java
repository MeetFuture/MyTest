package com.tangqiang.jpcap;

import org.apache.log4j.Logger;

import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
import jpcap.NetworkInterfaceAddress;
import jpcap.packet.Packet;
import jpcap.packet.TCPPacket;

/**
 * Jpcap测试
 * 
 * @author tqiang
 * @email tom617288330@gmail.com
 * @date 2014-7-31 上午11:39:20
 * 
 * @version 1.0 2014-7-31 tqiang create
 * 
 */
public class JpcapTest {
	private Logger logger = Logger.getLogger(JpcapTest.class);

	public static void main(String[] args) {
		try {
			JpcapTest jt = new JpcapTest();
			jt.testJpcap();
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void testJpcap() throws Exception {
		NetworkInterface[] devices = JpcapCaptor.getDeviceList();
		for (int i = 0; i < devices.length; i++) {
			logger.info("网卡:" + i + "  名称:" + devices[i].name + "   网卡描述:" + devices[i].description + "");
			logger.info(" 数据链路层的名称:" + devices[i].datalink_name + " 所属网络:" + devices[i].datalink_description + "  (以太网 Ethernet、无线LAN网 wireless LAN、令牌环网 token ring)");

			String mac = "";
			for (byte b : devices[i].mac_address) {
				mac += Integer.toHexString(b & 0xff) + ":";
			}
			logger.info(" MAC address:" + mac);
			
			for (NetworkInterfaceAddress a : devices[i].addresses) {
				logger.info(" address:" + a.address + " 子网:" + a.subnet + " 广播地址:" + a.broadcast);
			}
		}

		int index = 0;
		JpcapCaptor captor = JpcapCaptor.openDevice(devices[index], 65535, true, 20);
		//captor.setFilter("send host 10.1.33.222", true);
		
		while (true) {
			Packet pt = captor.getPacket();
			if (pt != null) {
				
				if (pt instanceof TCPPacket) {
					TCPPacket tcpp = (TCPPacket) pt;
					logger.info(tcpp);
				}
			}
		}
	}
}
