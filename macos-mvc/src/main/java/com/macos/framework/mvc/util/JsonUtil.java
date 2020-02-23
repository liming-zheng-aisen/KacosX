package com.macos.framework.mvc.util;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.List;

/**
 * @author zheng.liming
 * @date 2019/8/6
 * @description
 */
@SuppressWarnings("all")
public class JsonUtil{

    // 定义jackson对象
    private static final ObjectMapper MAPPER = new ObjectMapper();


    /**
     * Description：将对象转换成json字符串
     *
     * @param data json数据
     * @return
     */
    public static String objectToJson(Object data) throws JsonProcessingException {
            String string = MAPPER.writeValueAsString(data);
            return string;
    }

    /**
     * Description：将json结果集转化为对象
     *
     * @param jsonData json数据
     * @param clazz    对象中的object类型
     * @return
     */
    public static <T> T jsonToBean(String jsonData, Class<T> beanType) throws IOException {
            T t = MAPPER.readValue(jsonData, beanType);
            return t;
    }


    /**
     * Description：将json数据转换成pojo对象list
     *
     * @param jsonData
     * @param beanType
     * @return
     */
    public static <T> List<T> jsonToList(String jsonData, Class<T> beanType) throws IOException {

        JavaType javaType = MAPPER.getTypeFactory().constructParametricType(List.class, beanType);
        List<T> list = MAPPER.readValue(jsonData, javaType);
        return list;

    }


}

