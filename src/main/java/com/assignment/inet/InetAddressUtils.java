package com.assignment.inet;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class InetAddressUtils {

    /**
     * Returns whether the given host name represents the local host
     *
     * @param otherHostName
     * @return
     */
    public static boolean isLocalHost(String otherHostName) {
        boolean isLocalHost = false;

        try {
            String otherHostAddress = getHostAddressByName(otherHostName);
            isLocalHost = InetAddress.getLocalHost().getHostAddress().equals(otherHostAddress);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return isLocalHost;
    }

    /**
     * Returns the host address for the given host name
     *
     * @param hostName
     * @return
     */
    public static String getHostAddressByName(String hostName) {
        String hostAddress = null;
        try {
            InetAddress byName = InetAddress.getByName(hostName);
            hostAddress = byName.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return hostAddress;
    }

}
