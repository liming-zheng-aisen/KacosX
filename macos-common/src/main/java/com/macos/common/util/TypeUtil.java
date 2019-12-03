package com.macos.common.util;

import java.util.Date;

/**
 * @Desc TypeUtil
 * @Author Zheng.LiMing
 * @Date 2019/9/13
 */
public class TypeUtil {
    /**
     * 判断对象属性是否是基本数据类型,包括是否包括string
     *
     * @param className
     * @param incString 是否包括string判断,如果为true就认为string也是基本数据类型
     * @return
     */
    public static boolean isBaseType(Class className, boolean incString) {
        if (incString && className.equals(String.class)) {
            return true;
        }
        return className.equals(Integer.class) ||
                className.equals(int.class) ||
                className.equals(Byte.class) ||
                className.equals(byte.class) ||
                className.equals(Long.class) ||
                className.equals(long.class) ||
                className.equals(Double.class) ||
                className.equals(double.class) ||
                className.equals(Float.class) ||
                className.equals(float.class) ||
                className.equals(Character.class) ||
                className.equals(char.class) ||
                className.equals(Short.class) ||
                className.equals(short.class) ||
                className.equals(Boolean.class) ||
                className.equals(boolean.class);
    }

    public static Object baseType(String type,String value) throws Exception {
        Object targer=null;
        switch (type){
            case "Integer":
            case "int":
                targer=Integer.parseInt(value);
                break;
            case "String":
                targer=value;
                break;
            case "Double":
            case "double":
                targer=Double.parseDouble(value);
                break;
            case "Float":
            case "float":
                targer=Float.parseFloat(value);
                break;
            case "Boolean":
            case "boolean":
                targer=Boolean.parseBoolean(value);
                break;
            case "Date":
                targer=Date.parse(value);
                break;
            default:
                throw new Exception("不支持此类型的数据！");
        }
        return targer;
    }
}
