package com.macos;

import java.io.BufferedReader;
import java.io.InputStreamReader;

/**
 * @Desc DyConsolePrint
 * @Author Zheng.LiMing
 * @Date 2019/9/4
 */
public class ConsolePrint {

    public static void printLogo(Class c){
            try {
                BufferedReader br=new BufferedReader(new InputStreamReader(c.getResourceAsStream("/MacosX.txt"),"utf-8"));
                while(br.ready()) {
                    System.out.println(br.readLine());
                }
                br.close();
            } catch (Exception var4) {
                var4.printStackTrace();
            }
    }

}
