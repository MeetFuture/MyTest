package com.tangqiang.jpcap.arp;

import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;

import jpcap.JpcapCaptor;
import jpcap.JpcapSender;
import jpcap.packet.Packet;

/**
 * 包 发送类
 * 
 * @author tqiang
 * @email tom617288330@gmail.com
 * @date 2014-8-11 上午9:54:33
 * 
 * @version 1.0 2014-8-11 tqiang create
 * 
 */
public class PacketSendThread extends Thread {
	private Logger logger = Logger.getLogger(PacketSendThread.class);
	private Packet packet;
	private long time;

	public PacketSendThread(Packet packet, long time) {
		this.packet = packet;
		this.time = time;
	}

	public void run() {
		try {
			int iCountSend = 0;
			JpcapCaptor jc = JpcapCaptor.openDevice(JpcapCaptor.getDeviceList()[0], 2000, false, 3000);
			JpcapSender sender = jc.getJpcapSenderInstance();
			while (true) {
				try {
					logger.info(packet.toString());
					sender.sendPacket(packet);
					TimeUnit.MILLISECONDS.sleep(time);
					iCountSend++;
					if (iCountSend % 10 == 0) {
						logger.info("已发送次数:" + iCountSend);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
