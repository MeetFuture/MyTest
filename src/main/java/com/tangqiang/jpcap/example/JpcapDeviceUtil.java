package com.tangqiang.jpcap.example;

import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
import jpcap.NetworkInterfaceAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * TODO
 *
 * @author Tom
 * @version 1.0 2018-01-26 0026 Tom create
 * @date 2018-01-26 0026
 * @copyright Copyright © 2018 Grgbanking All rights reserved.
 */
public class JpcapDeviceUtil {
    private static int position = 0;
    private static Logger logger = LoggerFactory.getLogger(JpcapDeviceUtil.class);

    public static NetworkInterface get() {
        NetworkInterface[] devices = JpcapCaptor.getDeviceList();
        logger.info("NetworkInterface -------------------------");
        for (int i = 0; i < devices.length; i++) {
            logger.info(i + " :" + devices[i].name + "(" + devices[i].description + ")");
            logger.info("    data link:" + devices[i].datalink_name + "(" + devices[i].datalink_description + ")");

            StringBuilder sbMac = new StringBuilder("    MAC address:");
            for (byte b : devices[i].mac_address) {
                sbMac.append(Integer.toHexString(b & 0xff) + ":");
            }
            logger.info(sbMac.toString());
            for (NetworkInterfaceAddress a : devices[i].addresses) {
                logger.info("    address:" + a.address + " " + a.subnet + " " + a.broadcast);
            }
        }
        logger.info("NetworkInterface -------------------------");
        return devices[position];
    }
}
