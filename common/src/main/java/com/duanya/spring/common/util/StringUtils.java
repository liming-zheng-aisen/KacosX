package com.duanya.spring.common.util;

import java.net.URL;

/**
 * @author zheng.liming
 * @date 2019/8/3
 * @description
 */
public class StringUtils {

    /**
     * 判断非空(保留空格)，先判断是否为null，再判断是否为“”
     * @param obj
     * @return
     */
    public static boolean isEmpty(String obj){
        return  null==obj || obj.length()==0;
    }

    /**
     * 判断空(去除空格)，先判断是否为null，再判断是否为“”
     * @param obj
     * @return
     */
    public static boolean isEmptyPlus(String obj){
        return isEmpty(trim(obj));
    }

    /**
     * 字符串(保留空格)不为“”或null
     * @param obj
     * @return
     */
    public static boolean isNotEmpty(String obj){
        return isEmpty(obj)==false;
    }

    /**
     * 判断非空(去除空格)，先判断是否为null，再判断是否为“”
     * @param obj
     * @return
     */
    public static boolean isNotEmptyPlus(String obj){
        return isEmptyPlus(obj)==false;
    }

    /**
     *去掉空格，预习判断null
     * @param obj
     * @return
     */
    public static String trim(String obj){
        if (null==obj){
            return  null;
        }
        return obj.trim();
    }

    public static String toLowerCaseFirstName(String str){
        if (isEmptyPlus(str)){
            return null;
        }
        byte[] strByte=str.getBytes();
        strByte[0]+=32;
        return new String(strByte);
    }
    public static String toUpCaseFirstName(String str){
        if (isEmptyPlus(str)){
            return null;
        }
        byte[] strByte=str.getBytes();
        strByte[0]-=32;
        return new String(strByte);
    }

    public static String formatUrl(String url){

        url=url.replace("//","/");

        if (url.equals("/")){
            return url;
        }

        if (url.indexOf("/")!=0){
            url="/"+url;
        }

        if (url.lastIndexOf("/")==url.length()-1){
            url=url.substring(0,url.lastIndexOf("/"));
        }

        return  url;
    }

    public static  String formatDefinedName(String name){

        if (isEmptyPlus(name)){
            return null;
        }

        name=name.toLowerCase();
        String[] newArray = name.split("[-,_]");

        String result="";
        for (int i=0;i<newArray.length;i++){
            if (i==0) {
                result+=newArray[i];
                continue;
            }
            result+=toUpCaseFirstName(newArray[i]);
        }
        return result;
    }
    /**
     * "file:/home/whf/cn/fh" -> "/home/whf/cn/fh"
     * "jar:file:/home/whf/foo.jar!cn/fh" -> "/home/whf/foo.jar"
     */
    public static String getRootPath(URL url) {
        String fileUrl = url.getFile();
        int pos = fileUrl.indexOf('!');

        if (-1 == pos) {
            return fileUrl;
        }

        return fileUrl.substring(5, pos);
    }

    /**
     * "cn.fh.lightning" -> "cn/fh/lightning"
     * @param name
     * @return
     */
    public static String dotToSplash(String name) {
        return name.replaceAll("\\.", "/");
    }

    /**
     * "Apple.class" -> "Apple"
     */
    public static String trimExtension(String name) {
        int pos = name.indexOf('.');
        if (-1 != pos) {
            return name.substring(0, pos);
        }

        return name;
    }

    /**
     * /application/home -> /home
     * @param uri
     * @return
     */
    public static String trimURI(String uri) {
        String trimmed = uri.substring(1);
        int splashIndex = trimmed.indexOf('/');

        return trimmed.substring(splashIndex);
    }
}
