package com.tangqiang.jpcap.example;

import jpcap.JpcapCaptor;
import jpcap.JpcapSender;
import jpcap.NetworkInterface;
import jpcap.packet.EthernetPacket;
import jpcap.packet.ICMPPacket;
import jpcap.packet.IPPacket;

import java.net.InetAddress;

public class SendICMP {

    public static void main(String[] args) throws java.io.IOException {

        NetworkInterface device = JpcapDeviceUtil.get();
        JpcapSender sender = JpcapSender.openDevice(device);

        ICMPPacket p = new ICMPPacket();
        p.type = ICMPPacket.ICMP_IREQ;
        p.seq = 1000;
        p.id = 999;
        p.orig_timestamp = 123;
        p.trans_timestamp = 456;
        p.recv_timestamp = 789;
        p.setIPv4Parameter(0, false, false, false, 0, false, false, false, 0, 1010101, 100, IPPacket.IPPROTO_ICMP,
                InetAddress.getByName("10.1.42.23"), InetAddress.getByName("10.1.42.1"));
        p.data = "data".getBytes();

        EthernetPacket ether = new EthernetPacket();
        ether.frametype = EthernetPacket.ETHERTYPE_IP;
        ether.src_mac = new byte[]{(byte) 0, (byte) 1, (byte) 2, (byte) 3, (byte) 4, (byte) 5};
        ether.dst_mac = new byte[]{(byte) 0, (byte) 6, (byte) 7, (byte) 8, (byte) 9, (byte) 10};
        p.datalink = ether;

        //for(int i=0;i<10;i++)
        System.out.println("SendICMP.main :"+ sender +"  P:" + p);
        sender.sendPacket(p);
    }
}