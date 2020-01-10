package com.macos.framework.core.handle;

import com.macos.common.util.StringUtils;
import com.macos.framework.annotation.Value;
import com.macos.framework.core.bean.definition.BeanDefinition;
import com.macos.framework.core.bean.manage.BeanManager;
import com.macos.framework.core.env.ApplicationENV;
import com.macos.framework.core.handle.base.BaseHandler;
import com.macos.framework.core.util.ReflectionsUtil;

import java.lang.reflect.Field;

/**
 * @Desc ValueHandle
 * @Author Zheng.LiMing
 * @Date 2020/1/6
 */
public class ValueHandler extends BaseHandler {

    static {
        handleAnnotations = new Class[]{Value.class};
    }


    @Override
    public boolean doHandle(Class mainClass, Class handleClass, String[] args) throws Exception {
        if (handleClass == null || handleClass.isInterface()){
            return true;
        }
        BeanDefinition beanDefinition = BeanManager.getBeanDefinition(null,handleClass);
        if (beanDefinition==null){
            return true;
        }
        Object bean = beanDefinition.getContextApi().getBean(null,handleClass);
        Field[] fields =handleClass.getDeclaredFields();
        for (Field field : fields) {
            field.setAccessible(true);
            if (field.isAnnotationPresent(Value.class)) {
                Value valueAnnotation = field.getAnnotation(Value.class);
                String value = valueAnnotation.value();
                Object fValue = getValue(value);
                ReflectionsUtil.setFieldValue(bean,field,fValue);
            }
        }

        return false;
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

        String endValue = null;
        BeanDefinition beanDefinition = BeanManager.getBeanDefinition(null,ApplicationENV.class);
        if (beanDefinition!=null){
            ApplicationENV env = (ApplicationENV)beanDefinition.getContextApi().getBean(null,ApplicationENV.class);
            endValue = (String) env.getElementValue(values[0]);
        }

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


}
