package com.tangqiang.jpcap.example;

import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
import jpcap.PacketReceiver;
import jpcap.packet.ARPPacket;
import jpcap.packet.ICMPPacket;
import jpcap.packet.Packet;

/**
 * TCP 包显示
 *
 * @author Tom
 * @version 1.0 2018-01-26 0026 Tom create
 * @date 2018-01-26 0026
 * @copyright Copyright © 2017 Grgbanking All rights reserved.
 */
public class Tcpdump implements PacketReceiver {

    public static void main(String[] args) throws Exception {
        NetworkInterface device = JpcapDeviceUtil.get();
        JpcapCaptor jpcap = JpcapCaptor.openDevice(device, 2000, false, 20);
        jpcap.loopPacket(-1, new Tcpdump());
    }


    @Override
    public void receivePacket(Packet packet) {
        if (packet instanceof ARPPacket) {
            System.out.println(packet);
        }
    }


}
