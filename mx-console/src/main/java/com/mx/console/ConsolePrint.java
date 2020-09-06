package com.mx.console;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @Desc 系统打印控制平台
 * @Author Zheng.LiMing
 * @Date 2019/9/4
 */
public class ConsolePrint {

    private final static String MX_LOGO = "/mx.txt";

    private final static String MX_CHARSET = "utf-8";

    public static void printLogo(Class c) {
        try {
            BufferedReader br = new BufferedReader(new InputStreamReader(c.getResourceAsStream(MX_LOGO), MX_CHARSET));
            while (br.ready()) {
                System.out.format("\33[%d;1m%s%n", FontColor.BLUE, br.readLine());
            }
            br.close();
        } catch (Exception var4) {
            var4.printStackTrace();
        }
    }

    /**
     * 打印错误信息
     *
     * @param source
     * @param msg
     */
    public static void error(String source, String msg) {
        System.out.format("\33[%d;1m%s - \33[%d;1m【ERROR】\33[%d;1m%s%n", FontColor.WHITE, source, FontColor.VIOLET, FontColor.RED, msg);
    }

    /**
     * 打印基本信息
     *
     * @param source
     * @param msg
     */
    public static void info(String source, String msg) {
        System.out.format("\33[%d;1m%s - \33[%d;1m【INFO】\33[%d;1m%s%n", FontColor.WHITE, source, FontColor.VIOLET, FontColor.GREEN, msg);
    }

    /**
     * 打印警告信息
     *
     * @param source
     * @param msg
     */
    public static void warn(String source, String msg) {
        System.out.format("\33[%d;1m%s - \33[%d;1m【WARN】\33[%d;1m%s%n", FontColor.WHITE, source, FontColor.VIOLET, FontColor.YELLOW, msg);
    }

    /**
     * 打印系统成功打印的信息
     *
     * @param title
     * @param msg
     */
    public static void success(String title, String msg) {
        System.out.format("\33[%d;1m%s - \33[%d;1m【SUCCESS】\33[%d;1m%s%n", FontColor.WHITE, title, FontColor.VIOLET, FontColor.BLUE, msg);
    }

    /**
     * 打印DEBUG信息
     *
     * @param title
     * @param msg
     */
    public static void debug(String title, String msg) {
        System.out.format("\33[%d;1m%s - \33[%d;1m【DEBUG】\33[%d;1m%s%n", FontColor.WHITE, title, FontColor.VIOLET, FontColor.BLUE, msg);
    }


}
