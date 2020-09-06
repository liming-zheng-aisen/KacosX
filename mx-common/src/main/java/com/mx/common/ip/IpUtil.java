package com.mx.common.ip;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 * @Desc IpUtil IP工具类，用于获取本机IP地址
 * @Author Zheng.LiMing
 * @Date 2019/9/13
 */
public class IpUtil {

    /**
     * 获取本地内网IP
     * 内网IP定义为以：10.|172.16.|192.168.开头的IP段
     * @return
     */
    public static String currentIP(boolean privateIp) {
        try {
            Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
            while (networkInterfaces.hasMoreElements()) {
                NetworkInterface networkInterface = networkInterfaces.nextElement();
                Enumeration<InetAddress> inetAddresses = networkInterface.getInetAddresses();
                while (inetAddresses.hasMoreElements()) {
                    InetAddress inetAddress = inetAddresses.nextElement();
                    if (inetAddress != null && inetAddress instanceof Inet4Address) {
                        String ip = inetAddress.getHostAddress();
                        // 内网IP
                        if (ip.startsWith("10.") || ip.startsWith("172.16.") || ip.startsWith("192.168.")) {
                            if (privateIp) {
                                return ip;
                            }
                            // 本地lo IP
                        }else if ("127.0.0.1".equals(ip)) {

                            // 外网IP
                        }else {
                            return ip;
                        }
                    }
                }
            }
        }catch (Exception e) {}
        return null;
    }

    public static void main(String[] args){
        System.out.println(currentIP(false));
    }
}
