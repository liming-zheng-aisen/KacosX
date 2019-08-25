package com.duanya.spring.commont.util;

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


}
