package com.macos.common.ip;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @Desc IpUtil
 * @Author Zheng.LiMing
 * @Date 2019/9/13
 */
public class IpUtil {

    public static String currentIP(String defaultValue){
        try {
            defaultValue = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return defaultValue;
    }
}
