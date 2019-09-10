package com.duanya.spring;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;

/**
 * @Desc DyConsolePrint
 * @Author Zheng.LiMing
 * @Date 2019/9/4
 */
public class DyConsolePrint {

    public static void printLogo(Class c){
        try {
            File file = new File(c.getResource("/dy.txt").getPath());
            if (file.exists()) {
                FileReader in = new FileReader(file);
                BufferedReader buff = new BufferedReader(in);
                while (buff.ready()){
                    System.out.println(buff.readLine());
                }
                buff.close();
                in.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
