package com.study.monitor.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class HttpUtil {

    public static String getCurrentHostname() {
        try {
            InetAddress localHost = InetAddress.getLocalHost();
            return localHost.getHostName();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            return null;
        }
    }

    //unit test
    public static void main(String[] args) {
        System.out.println(getCurrentHostname());
    }
}
