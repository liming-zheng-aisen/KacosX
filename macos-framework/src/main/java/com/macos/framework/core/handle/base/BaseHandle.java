package com.macos.framework.core.handle.base;
import com.macos.framework.context.base.ApplicationContextApi;
import com.macos.framework.context.impl.ApplicationContextImpl;
import com.macos.framework.core.handle.ConfigurationHandle;

import java.util.HashSet;
import java.util.Set;

/**
 * @Desc 处理器
 * @Author Zheng.LiMing
 * @Date 2020/1/1
 */
public abstract class BaseHandle {

    /**前置处理集合*/
    protected static Set<BaseHandle> beforeHandleMap = new HashSet<>();

    /**后置处理集合*/
    protected static Set<BaseHandle>afterHandleMap = new HashSet<>();

    /**需要处理的注解*/
    public static Class[] annotationclass;

    /**
     * 应用程序上下文
     */
    protected static ApplicationContextApi applicationContextApi = ApplicationContextImpl.Builder.getApplicationContext();

    /**
     * 执行处理
     * @param target
     * @param args
     * @throws Exception
     */
    public abstract boolean doHandle(Class target,String[] args) throws Exception;

    /**
     * 执行前置处理
     * @param target
     * @throws Exception
     */
    protected void doBefore(Class target,String[] args) throws Exception {
        if (beforeHandleMap.size()==0){
            return;
        }
        execute(target,args, beforeHandleMap);
    }

    /**
     * 执行后置处理
     * @param target
     * @throws Exception
     */
    protected void doAfter(Class target,String[] args) throws Exception {
        if (afterHandleMap.size()==0){
            return;
        }
        execute(target, args,afterHandleMap);
    }

    /**
     * 执行
     * @param c
     * @param handleSet
     * @throws Exception
     */
    protected void execute(Class c , String[] args , Set<BaseHandle> handleSet) throws Exception {
        for (BaseHandle handle: handleSet){
            if (handle != null) {
              boolean result = handle.doHandle(c,args);
              if (!result){
                  return;
              }
            }
        }
    }

    /***
     * 注册前置处理器
     * @param value
     */
    public static void registerBeforeHandle(BaseHandle value) {
        ConfigurationHandle.beforeHandleMap.add(value);
    }

    /***
     * 注册后置处理器
     * @param value
     */
    public static void registerAfterHandle(BaseHandle value) {
        ConfigurationHandle.afterHandleMap.add(value);
    }

    public static Set<BaseHandle> getBeforeHandleMap() {
        return beforeHandleMap;
    }

    public static Set<BaseHandle> getAfterHandleMap() {
        return afterHandleMap;
    }
}
