package com.duanya.spring.framework.core.bean.factory;

import com.duanya.spring.common.util.StringUtils;
import com.duanya.spring.framework.annotation.DyValue;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.Properties;

/**
 * @Desc 属性注入工厂
 * @Author Zheng.LiMing
 * @Date 2019/9/4
 */
public class DyValueFactory {

    private static final Logger log = LoggerFactory.getLogger(DyValueFactory.class);


    /**
     * 为字段赋值（字段上必须有@DyValue的注解）
     *
     * @param bean
     * @param evn
     * @return
     * @throws IllegalAccessException
     */
    public static Object doFields(Object bean, Properties evn) throws IllegalAccessException {

        Field[] fields = bean.getClass().getDeclaredFields();

        for (Field field : fields) {

            field.setAccessible(true);

            if (field.isAnnotationPresent(DyValue.class)) {
                DyValue dyValue = field.getAnnotation(DyValue.class);
                String value = dyValue.value();
                Object fValue = null;
                if (StringUtils.isNotEmptyPlus(value)) {
                    String fName = value.substring(0, 2);
                    int fEndIdnex = value.lastIndexOf("}");
                    if ("${".equals(fName) && fEndIdnex > 1) {
                        String key = value.substring(2, fEndIdnex);
                        fValue = evn.get(key);
                        if (null == fValue) {
                            if (fEndIdnex < value.length() - 1) {
                                if (value.substring(fEndIdnex+1, fEndIdnex + 2).equals(":")) {
                                    fValue = value.substring(fEndIdnex + 2);
                                } else {
                                    log.error(value + "不符合规范【@DyValue(\"${key}:默认值\")】");
                                    throw new IllegalAccessException(value + "不符合规范【@DyValue(\"${key}:默认值\")】");
                                }
                            }
                        }
                    } else {
                        fValue = value;
                    }
                    setField(field,bean,fValue);

                }
            }
        }
        return bean;
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
