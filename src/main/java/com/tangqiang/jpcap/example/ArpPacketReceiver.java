package com.tangqiang.jpcap.example;

import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
import jpcap.PacketReceiver;
import jpcap.packet.ARPPacket;
import jpcap.packet.Packet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Arp接收
 *
 * @author Tom
 * @version 1.0 2018-01-26 0026 Tom create
 * @date 2018-01-26 0026
 * @copyright Copyright © 2018 Grgbanking All rights reserved.
 */
public class ArpPacketReceiver implements PacketReceiver {
    private Logger logger = LoggerFactory.getLogger(getClass());
    private Map<String, Map<String, AtomicInteger>> result = new HashMap<>();
    private AtomicLong count = new AtomicLong();

    public static void main(String[] args) throws Exception {
        NetworkInterface device = JpcapDeviceUtil.get();
        JpcapCaptor jpcap = JpcapCaptor.openDevice(device, 2000, false, 20);
        jpcap.loopPacket(-1, new ArpPacketReceiver());
    }

    @Override
    public void receivePacket(Packet packet) {
        if (packet instanceof ARPPacket) {
            ARPPacket arpPacket = (ARPPacket) packet;
            //logger.info("Sender:" + arpPacket.getSenderProtocolAddress() + "  Target:" + arpPacket.getTargetProtocolAddress() +"  Packet:" + packet);
            String sender = String.valueOf(arpPacket.getSenderProtocolAddress());
            if (!result.containsKey(sender)) {
                result.put(sender, new HashMap<>());
            }
            String target = String.valueOf(arpPacket.getTargetProtocolAddress());
            Map<String, AtomicInteger> map = result.get(sender);
            if (!map.containsKey(target)) {
                map.put(target, new AtomicInteger());
            }
            map.get(target).incrementAndGet();
        }
        long tmp = count.incrementAndGet();
        if (tmp % 100 == 0) {
            //logger.info("result:" + result);
            result.entrySet().stream().forEach(System.out::println);
        }
    }
}
