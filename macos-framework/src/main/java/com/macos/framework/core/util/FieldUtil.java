package com.macos.framework.core.util;

import com.macos.common.util.StringUtils;
import com.macos.framework.annotation.Value;
import lombok.extern.slf4j.Slf4j;
import java.lang.reflect.Field;

/**
 * @Desc bean属性处理
 * @Author Zheng.LiMing
 * @Date 2019/9/4
 */
@Slf4j
@SuppressWarnings("all")
public class FieldUtil {

    /**
     * 为字段赋值（字段上必须有@Value的注解）
     *
     * @param bean
     * @return
     * @throws Exception
     */
    public static Object doFields(Object bean) throws Exception {
        Field[] fields = bean.getClass().getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(Value.class)) {
                Value dyValue = field.getAnnotation(Value.class);
                String value = dyValue.value();
                Object fValue = getValue(value);
                setField(field,bean,fValue);
            }
        }
        return bean;
    }

    /**
     * 根据Value获取属性值
     * @param value
     * @return
     * @throws Exception
     */
    public static String getValue(String value) throws Exception {
        String[] values = splitValue(value);
        if (values == null){
            return null;
        }

        if (values.length==1){
            return values[0];
        }

        //String endValue = PropertiesFileLoader.getEvn().getProperty(values[0]);

        String endValue = null;
        if (StringUtils.isEmpty(endValue)&& values.length>1){
            endValue = values[1];
        }

        return endValue;
    }

    /**
     * 根据":"进行字符串拆分
     * @param value
     * @return
     * @throws Exception
     */
    public static String[] splitValue(String value) throws Exception{

        if (StringUtils.isEmpty(value)) {
            return null;
        }

        if (value.indexOf("${")<0 || value.lastIndexOf("}")<0){
            return new String[]{value};
        }

        if (!isLegitimate(value)){
            throw  new Exception(value+"不合法，请检查参数！");
        }

        value = value.substring(2,value.length()-1);

        int index = value.indexOf(":");

        if (index>0){
            return new String[]{value.substring(0,index),value.substring(index+1)};
        }

        return new String[]{value};
    }

    /**
     * 是否满足${k:v}
     * @param value
     * @return
     */
    public static boolean isLegitimate(String value){
        //${k:v}
        if (value.indexOf("${")==0 && value.lastIndexOf("}") == (value.length()-1)){
            return true;
        }
        return false;
    }


    public static void setField(Field field, Object bean,Object value) throws IllegalAccessException {
        String fType=field.getType().toString();
        field.setAccessible(true);
        if (fType.endsWith("String")){
            field.set(bean,(String)value);
        }else if (fType.endsWith("int") || fType.endsWith("Integer")){
            field.set(bean,Integer.parseInt(value.toString()));
        }else if (fType.endsWith("Double") || fType.endsWith("double")){
            field.set(bean,Double.valueOf(value.toString()));
        }else if (fType.endsWith("Boolean") || fType.endsWith("boolean")){
            field.set(bean,Boolean.valueOf(value.toString()));
        }else if (fType.endsWith("Short") || fType.endsWith("short")){
            field.set(bean,Short.valueOf(value.toString()));
        }else if (fType.endsWith("Float") || fType.endsWith("float")){
            field.set(bean,Float.valueOf(value.toString()));
        }else if (fType.endsWith("Character") || fType.endsWith("char")){
            field.set(bean,(char)value);
        }
    }

}
