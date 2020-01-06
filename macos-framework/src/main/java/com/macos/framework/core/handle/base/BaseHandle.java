package com.macos.framework.core.handle.base;
import com.macos.framework.context.base.ApplicationContextApi;
import com.macos.framework.context.impl.ApplicationContextImpl;

import java.lang.annotation.Annotation;
import java.util.HashMap;
import java.util.Map;

/**
 * @Desc 处理器
 * @Author Zheng.LiMing
 * @Date 2020/1/1
 */
public abstract class BaseHandle {

    /**前置处理集合*/
    protected static Map<Class, BaseHandle> beforeHandleMap = new HashMap<>();
    /**后置处理集合*/
    protected static Map<Class, BaseHandle> afterHandleMap = new HashMap<>();
    /**需要处理的注解*/
    public static Class[] annotationclass;

    /**
     * 应用程序上下文
     */
    protected static ApplicationContextApi applicationContextApi = ApplicationContextImpl.Builder.getApplicationContext();

    /**
     * 执行处理
     * @param c
     * @throws Exception
     */
    public abstract void doHandle(Class c) throws Exception;

    /**
     * 执行前置处理
     * @param c
     * @throws Exception
     */
    protected void doBefore(Class c) throws Exception {
        if (beforeHandleMap.size()==0){
            return;
        }
        execute(c, beforeHandleMap);
    }

    /**
     * 执行后置处理
     * @param c
     * @throws Exception
     */
    protected void doAfter(Class c) throws Exception {
        if (afterHandleMap.size()==0){
            return;
        }
        execute(c, afterHandleMap);
    }

    /**
     * 执行
     * @param c
     * @param handleMap
     * @throws Exception
     */
    protected void execute(Class c, Map<Class, BaseHandle> handleMap) throws Exception {
        Annotation[] annotations = c.getAnnotations();
        if (annotations == null || annotations.length == 0) {
            return;
        }
        for (Annotation an : annotations) {
            BaseHandle handle = handleMap.get(an.annotationType());
            if (handle != null) {
                handle.doHandle(c);
            }
        }
    }


}
